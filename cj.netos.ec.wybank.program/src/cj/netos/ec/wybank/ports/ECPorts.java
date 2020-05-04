package cj.netos.ec.wybank.ports;

import cj.netos.ec.wybank.IECPorts;
import cj.netos.ec.wybank.services.DefaultConsumer;
import cj.netos.rabbitmq.IRabbitMQConsumer;
import cj.netos.rabbitmq.RabbitMQConsumerConfig;
import cj.studio.ecm.annotation.CjService;
import cj.studio.ecm.annotation.CjServiceRef;
import cj.studio.ecm.net.CircuitException;
import cj.studio.openport.ISecuritySession;

@CjService(name = "/ec.ports")
public class ECPorts implements IECPorts {
    @CjServiceRef
    IRabbitMQConsumer rabbitMQConsumer;

    private void checkRights(ISecuritySession securitySession) throws CircuitException {
        if (!securitySession.roleIn("platform:administrators") && !securitySession.roleIn("tenant:administrators") && !securitySession.roleIn("app:administrators")) {
            throw new CircuitException("801", "无权访问");
        }
    }

    @Override
    public boolean isOpened(ISecuritySession securitySession) throws CircuitException {
        return rabbitMQConsumer.isOpened();
    }

    @Override
    public RabbitMQConsumerConfig config(ISecuritySession securitySession) throws CircuitException {
        checkRights(securitySession);
        return rabbitMQConsumer.config();
    }

    @Override
    public RabbitMQConsumerConfig reopen(ISecuritySession securitySession) throws CircuitException {
        checkRights(securitySession);
        rabbitMQConsumer.innerOpen();
        rabbitMQConsumer.acceptConsumer(new DefaultConsumer());
        return rabbitMQConsumer.config();
    }

    @Override
    public void close(ISecuritySession securitySession) throws CircuitException {
        checkRights(securitySession);
        rabbitMQConsumer.close();
    }
}
