package cj.netos.ec.wybank.service;

import cj.netos.ec.wybank.ITradeEventNotify;
import cj.netos.network.NetworkFrame;
import cj.netos.rabbitmq.IRabbitMQProducer;
import cj.studio.ecm.annotation.CjService;
import cj.studio.ecm.annotation.CjServiceRef;
import cj.studio.ecm.net.CircuitException;
import cj.ultimate.gson2.com.google.gson.Gson;
import com.rabbitmq.client.AMQP;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.util.HashMap;

@CjService(name = "tradeEventNotify")
public class TradeEventNotify implements ITradeEventNotify {
    @CjServiceRef(refByName = "@.rabbitmq.producer.notify")
    IRabbitMQProducer rabbitMQProducer;

    @Override
    public void onsuccess(String cmd, Object event,String toPerson) {
        ByteBuf bb = Unpooled.buffer();
        bb.writeBytes(new Gson().toJson(event).getBytes());
        NetworkFrame frame = new NetworkFrame(String.format("onsuccess /wybank/trade/success?event=%s weny/1.0", cmd), bb);
        AMQP.BasicProperties properties = new AMQP.BasicProperties().builder()
                .type("/wybank/trade/success")
                .headers(new HashMap<String, Object>() {{
                    put("event", cmd);
                    put("to-person", toPerson);
//                    put("to-peer", "");
                }}).build();
        try {
            rabbitMQProducer.publish(properties, frame.toBytes());
        } catch (CircuitException e) {
            e.printStackTrace();
        } finally {
            frame.dispose();
        }
    }

    @Override
    public void onerror(String cmd, Object event,String toPerson) {
        ByteBuf bb = Unpooled.buffer();
        bb.writeBytes(new Gson().toJson(event).getBytes());
        NetworkFrame frame = new NetworkFrame(String.format("onerror /wybank/trade/error?%s weny/1.0", cmd), bb);
        AMQP.BasicProperties properties = new AMQP.BasicProperties().builder()
                .type("/wybank/trade/error")
                .headers(new HashMap<String, Object>() {{
                    put("event", cmd);
                    put("to-person", toPerson);
                }}).build();
        try {
            rabbitMQProducer.publish(properties, frame.toBytes());
        } catch (CircuitException e) {
            e.printStackTrace();
        } finally {
            frame.dispose();
        }

    }
}
