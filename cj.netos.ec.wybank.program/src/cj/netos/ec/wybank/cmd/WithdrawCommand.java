package cj.netos.ec.wybank.cmd;

import cj.netos.ec.wybank.ICuratorPathChecker;
import cj.netos.ec.wybank.ITradeEventNotify;
import cj.netos.ec.wybank.bo.WithdrawBO;
import cj.netos.ec.wybank.bo.WithdrawResponse;
import cj.netos.ec.wybank.model.WithdrawRecord;
import cj.netos.rabbitmq.CjConsumer;
import cj.netos.rabbitmq.IRabbitMQProducer;
import cj.netos.rabbitmq.RabbitMQException;
import cj.netos.rabbitmq.RetryCommandException;
import cj.netos.rabbitmq.consumer.IConsumerCommand;
import cj.studio.ecm.IServiceSite;
import cj.studio.ecm.annotation.CjService;
import cj.studio.ecm.annotation.CjServiceRef;
import cj.studio.ecm.annotation.CjServiceSite;
import cj.studio.ecm.net.CircuitException;
import cj.studio.openport.util.Encript;
import cj.ultimate.gson2.com.google.gson.Gson;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.LongString;
import okhttp3.*;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.framework.recipes.locks.InterProcessReadWriteLock;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@CjConsumer(name = "trade")
@CjService(name = "/wybank.ports#withdraw")
public class WithdrawCommand implements IConsumerCommand {
    @CjServiceSite
    IServiceSite site;

    @CjServiceRef(refByName = "curator.framework")
    CuratorFramework framework;

    @CjServiceRef
    ICuratorPathChecker curatorPathChecker;

    @CjServiceRef(refByName = "@.rabbitmq.producer.ack")
    IRabbitMQProducer rabbitMQProducer;

    @CjServiceRef
    ITradeEventNotify tradeEventNotify;

    @Override
    public void command(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws RabbitMQException, RetryCommandException {
        LongString wenyBankIDLS = (LongString) properties.getHeaders().get("wenyBankID");
        String path = String.format("/wenybank/%s/locks", wenyBankIDLS.toString());
        try {
            curatorPathChecker.check(framework, path);
        } catch (Exception e) {
            throw new RabbitMQException("500", e);
        }
        LongString record_snLS = (LongString) properties.getHeaders().get("record_sn");
        LongString withdrawerLS = (LongString) properties.getHeaders().get("withdrawer");
        InterProcessReadWriteLock lock = new InterProcessReadWriteLock(framework, path);
        InterProcessMutex mutex = lock.writeLock();
        try {
            mutex.acquire();
            WithdrawResponse response = _doCmd(properties, body);
            response.setMessage("ok");
            response.setStatus("200");
            response.setRecordSN(record_snLS.toString());
            //发送消息到交易网关，标记申购状态为：已申购，如果出错，标记为错误状态，交记录错误信息
            _sendAck(response);
            tradeEventNotify.onsuccess("withdraw", response, withdrawerLS == null ? "" : withdrawerLS.toString());
        } catch (RabbitMQException e) {
            WithdrawResponse response = new WithdrawResponse();
            response.setStatus("500");
            response.setMessage(e.getMessage());
            response.setRecordSN(record_snLS.toString());
            try {
                _sendAck(response);
            } catch (CircuitException ex) {
                throw new RabbitMQException(ex.getStatus(), ex.getMessage());
            }
            tradeEventNotify.onerror("withdraw", response, withdrawerLS == null ? "" : withdrawerLS.toString());
            throw e;
        } catch (Exception e) {
            WithdrawResponse response = new WithdrawResponse();
            response.setStatus("500");
            response.setMessage(e.getMessage());
            response.setRecordSN(record_snLS.toString());
            try {
                _sendAck(response);
            } catch (CircuitException ex) {
                throw new RabbitMQException(ex.getStatus(), ex.getMessage());
            }
            tradeEventNotify.onerror("withdraw", response, withdrawerLS == null ? "" : withdrawerLS.toString());
            throw new RabbitMQException("500", e);
        } finally {
            try {
                mutex.release();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private void _sendAck(WithdrawResponse response) throws CircuitException {
        AMQP.BasicProperties properties = new AMQP.BasicProperties().builder()
                .type("/wybank.ports")
                .headers(new HashMap() {
                    {
                        put("state", response.getStatus());
                        put("message", response.getMessage());
                        put("command", "ackWithdraw");
                        put("withdrawer", response.getWithdrawer());
                        put("withdrawerName", response.getWithdrawerName());
                        put("wenyBankID", response.getWenyBankID());
                        put("record_sn", response.getRecordSN());
                    }
                }).build();
        byte[] body = null;
        if (!"200".equals(response.getStatus())) {
            body = new byte[0];
        } else {
            body = new Gson().toJson(response.getRecord()).getBytes();
        }
        rabbitMQProducer.publish(properties, body);
    }

    private WithdrawResponse _doCmd(AMQP.BasicProperties properties, byte[] body) throws RabbitMQException {
        OkHttpClient client = (OkHttpClient) site.getService("@.http");

        String appid = site.getProperty("appid");
        String appKey = site.getProperty("appKey");
        String appSecret = site.getProperty("appSecret");
        String portsurl = site.getProperty("ports.oc.shunter.trade");
        LongString wenyBankIDLS = (LongString) properties.getHeaders().get("wenyBankID");
        LongString withdrawerLS = (LongString) properties.getHeaders().get("withdrawer");
        LongString withdrawerNameLS = (LongString) properties.getHeaders().get("withdrawerName");
//        LongString record_snLS = (LongString) properties.getHeaders().get("record_sn");

        String nonce = Encript.md5(String.format("%s%s", UUID.randomUUID().toString(), System.currentTimeMillis()));
        String sign = Encript.md5(String.format("%s%s%s", appKey, nonce, appSecret));

        WithdrawBO withdrawBO = new WithdrawBO();
        withdrawBO.setWenyBankID(wenyBankIDLS.toString());
        withdrawBO.setWithdrawer(withdrawerLS.toString());
        withdrawBO.setWithdrawerName(withdrawerNameLS.toString());
        withdrawBO.setRecord(new Gson().fromJson(new String(body), WithdrawRecord.class));

        Map mapArgs = new HashMap();
        mapArgs.put("withdrawBill", withdrawBO);
        RequestBody requestBody = RequestBody.create(new Gson().toJson(mapArgs).getBytes());
        final Request request = new Request.Builder()
                .url(portsurl)
                .addHeader("Rest-Command", "withdraw")
                .addHeader("app-id", appid)
                .addHeader("app-key", appKey)
                .addHeader("app-nonce", nonce)
                .addHeader("app-sign", sign)
                .post(requestBody)
                .build();
        final Call call = client.newCall(request);
        Response response = null;
        try {
            response = call.execute();
        } catch (IOException e) {
            throw new RabbitMQException("1002", e);
        }
        if (response.code() >= 400) {
            throw new RabbitMQException("1002", String.format("远程访问失败:%s", response.message()));
        }
        String json = null;
        try {
            json = response.body().string();
        } catch (IOException e) {
            throw new RabbitMQException("1002", e);
        }
        Map<String, Object> map = new Gson().fromJson(json, HashMap.class);
        if (Double.parseDouble(map.get("status") + "") >= 400) {
            throw new RabbitMQException(map.get("status") + "", map.get("message") + "");
        }
        json = (String) map.get("dataText");
        return new Gson().fromJson(json, WithdrawResponse.class);
    }
}
