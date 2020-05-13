package cj.netos.ec.wybank.cmd;

import cj.netos.ec.wybank.ICuratorPathChecker;
import cj.netos.ec.wybank.bo.PurchaseWenyBO;
import cj.netos.rabbitmq.RabbitMQException;
import cj.netos.rabbitmq.RetryCommandException;
import cj.netos.rabbitmq.consumer.IConsumerCommand;
import cj.studio.ecm.IServiceSite;
import cj.studio.ecm.annotation.CjService;
import cj.studio.ecm.annotation.CjServiceRef;
import cj.studio.ecm.annotation.CjServiceSite;
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

@CjService(name = "/wenybank.ports#purchase")
public class PurchaseWenyCommand implements IConsumerCommand {
    @CjServiceSite
    IServiceSite site;

    @CjServiceRef(refByName = "curator.framework")
    CuratorFramework framework;

    @CjServiceRef
    ICuratorPathChecker curatorPathChecker;

    @Override
    public void command(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws RabbitMQException, RetryCommandException {
        LongString wenyBankIDLS = (LongString) properties.getHeaders().get("wenyBankID");
        String path = String.format("/wenybank/%s/locks", wenyBankIDLS.toString());
        try {
            curatorPathChecker.check(framework, path);
        } catch (Exception e) {
            throw new RabbitMQException("500", e);
        }
        InterProcessReadWriteLock lock = new InterProcessReadWriteLock(framework, path);
        InterProcessMutex mutex = lock.writeLock();
        try {
            mutex.acquire();
            _doCmd(properties, body);
        } catch (RabbitMQException e) {
            throw e;
        } catch (Exception e) {
            throw new RabbitMQException("500", e);
        } finally {
            try {
                mutex.release();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void _doCmd(AMQP.BasicProperties properties, byte[] body) throws RabbitMQException {
        OkHttpClient client = (OkHttpClient) site.getService("@.http");

        String appid = site.getProperty("appid");
        String appKey = site.getProperty("appKey");
        String appSecret = site.getProperty("appSecret");
        String portsurl = site.getProperty("ports.oc.wenybank");
        LongString wenyBankIDLS = (LongString) properties.getHeaders().get("wenyBankID");
        LongString noteLS = (LongString) properties.getHeaders().get("note");
        LongString personLS = (LongString) properties.getHeaders().get("person");
        LongString nickNameLS = (LongString) properties.getHeaders().get("nickName");
        LongString deviceLS = (LongString) properties.getHeaders().get("device");
        long amountLS = (long) properties.getHeaders().get("amount");

        String nonce = Encript.md5(String.format("%s%s", UUID.randomUUID().toString(), System.currentTimeMillis()));
        String sign = Encript.md5(String.format("%s%s%s", appKey, nonce, appSecret));

        PurchaseWenyBO purchaseWenyBO = new PurchaseWenyBO();
        purchaseWenyBO.setAmount(amountLS);
        purchaseWenyBO.setCurrency("WENY");
        purchaseWenyBO.setNote(noteLS.toString());
        purchaseWenyBO.setWenyBankID(wenyBankIDLS.toString());
        purchaseWenyBO.setPurchaser(personLS.toString());
        purchaseWenyBO.setPurchaserName(nickNameLS.toString());
        purchaseWenyBO.setDevice(deviceLS.toString());
        Map mapArgs = new HashMap();
        mapArgs.put("purchaseBill", purchaseWenyBO);
        RequestBody requestBody = RequestBody.create(new Gson().toJson(mapArgs).getBytes());
        final Request request = new Request.Builder()
                .url(portsurl)
                .addHeader("Rest-Command", "purchase")
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
        map = new Gson().fromJson(json, HashMap.class);
    }
}
