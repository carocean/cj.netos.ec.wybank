package cj.netos.ec.wybank;

import cj.netos.rabbitmq.RabbitMQConsumerConfig;
import cj.studio.ecm.net.CircuitException;
import cj.studio.openport.IOpenportService;
import cj.studio.openport.ISecuritySession;
import cj.studio.openport.annotations.CjOpenport;
import cj.studio.openport.annotations.CjOpenports;

@CjOpenports(usage = "交易中心开放api。注：必须把交易节点的routingKey添加到交易网关的routingKeys列表才能将消息发送到交易节点。对于交易节点，一个交易节点向rabbitmq注册一个routingKey")
public interface IECPorts extends IOpenportService {

    @CjOpenport(usage = "是否已打开交易中心")
    boolean isOpened(ISecuritySession securitySession) throws CircuitException;

    @CjOpenport(usage = "获取交易中心的配置信息")
    RabbitMQConsumerConfig config(ISecuritySession securitySession) throws CircuitException;

    @CjOpenport(usage = "重新打开交易中心，如果已打开则什么也不做")
    RabbitMQConsumerConfig reopen(ISecuritySession securitySession) throws CircuitException;

    @CjOpenport(usage = "关闭交易中心")
    void close(ISecuritySession securitySession) throws CircuitException;


}
