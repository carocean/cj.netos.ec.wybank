package cj.netos.ec.wybank.services;

import cj.netos.rabbitmq.IConsumer;
import cj.studio.ecm.CJSystem;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Envelope;

import java.io.IOException;

public class DefaultConsumer implements IConsumer {
    @Override
    public void handleDelivery(Channel channel, String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        CJSystem.logging().info(getClass(), String.format("收到消息:\r\n%s\r\n%s", properties.getHeaders(), new String(body)));
        channel.basicAck(envelope.getDeliveryTag(), false);
    }
}
