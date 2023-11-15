package de.yggdrasil.core.dal.data.datasources;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import de.yggdrasil.core.dal.data.EventDataSource;
import de.yggdrasil.core.dal.data.HideFromDefaultCollector;
import de.yggdrasil.core.dal.data.event.DALDefaultEventBus;
import de.yggdrasil.core.dal.data.event.DALEventbus;
import de.yggdrasil.core.dal.data.event.DataSourceDataListener;
import de.yggdrasil.core.dal.data.event.events.RabbitMQDataReceivedEvent;
import de.yggdrasil.core.dal.data.network.rabbitmq.RabbitMQMessage;
import de.yggdrasil.core.dal.data.network.rabbitmq.RabbitMQPackageReader;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@HideFromDefaultCollector
public class RabbitMQ implements EventDataSource<RabbitMQMessage, DALDefaultEventBus> {

    private final String queueName;
    private byte[] lastMessage;
    private Channel channel;
    private final DALEventbus eventbus = new DALDefaultEventBus();

    private final DeliverCallback callback = (consumerTag, delivery) -> {
        RabbitMQMessage message = RabbitMQPackageReader.readPackage(delivery.getBody());
        this.eventbus.triggerEvent(new RabbitMQDataReceivedEvent(this, message));
    };

    public RabbitMQ(String queueName){
        this.queueName = queueName;
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(System.getProperty("RABBIT_MQ_HOST"));
        connectionFactory.setPort(Integer.parseInt(System.getProperty("RABBIT_MQ_PORT")));
        try {
            Connection connection = connectionFactory.newConnection();
            channel = connection.createChannel();
            channel.queueDeclare(queueName, false, false, false, null);
            channel.basicConsume(queueName, true, callback, consumerTag -> {});
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }

    @Override
    public RabbitMQMessage getData(String identifier) {
        return null;
    }

    @Override
    public void writeData(String key, RabbitMQMessage value) {

    }


    @Override
    public void registerEventListener(DataSourceDataListener listener) {
        this.eventbus.registerListener(listener);
    }

}
