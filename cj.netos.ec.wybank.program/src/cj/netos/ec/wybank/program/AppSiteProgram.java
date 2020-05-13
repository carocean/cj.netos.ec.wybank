package cj.netos.ec.wybank.program;

import cj.netos.rabbitmq.IRabbitMQConsumer;
import cj.netos.rabbitmq.RabbitMQException;
import cj.netos.rabbitmq.consumer.DeliveryCommandConsumer;
import cj.studio.ecm.annotation.CjService;
import cj.studio.ecm.net.CircuitException;
import cj.studio.gateway.socket.Destination;
import cj.studio.gateway.socket.app.GatewayAppSiteProgram;
import cj.studio.gateway.socket.app.ProgramAdapterType;

@CjService(name = "$.cj.studio.gateway.app", isExoteric = true)
public class AppSiteProgram extends GatewayAppSiteProgram {

    @Override
    protected void onstart(Destination dest, String assembliesHome, ProgramAdapterType type) throws CircuitException {
        IRabbitMQConsumer rabbitMQ = (IRabbitMQConsumer) site.getService("rabbitMQConsumer");
        try {
            rabbitMQ.open(assembliesHome);
            rabbitMQ.acceptConsumer(new DeliveryCommandConsumer(site));
        } catch (RabbitMQException e) {
            throw new CircuitException(e.getStatus(), e.getMessage());
        }
    }
}
