package de.yggdrasil.core.dal.data.datasources;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import de.yggdrasil.core.dal.data.EventDataSource;
import de.yggdrasil.core.dal.data.HideFromDefaultClassCollector;
import de.yggdrasil.core.dal.data.event.DALDefaultEventBus;
import de.yggdrasil.core.dal.data.event.DALEventBus;
import de.yggdrasil.core.dal.data.event.DataSourceDataListener;
import de.yggdrasil.core.dal.data.event.events.RabbitMQDataReceivedEvent;
import de.yggdrasil.core.dal.data.network.rabbitmq.RabbitMQMessage;
import de.yggdrasil.core.dal.data.network.rabbitmq.RabbitMQPackageReader;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * The RabbitMQ class represents a RabbitMQ data source that implements the EventDataSource interface.
 * It provides methods for retrieving and writing data to RabbitMQ, as well as registering event listeners.
 */
@HideFromDefaultClassCollector
public class RabbitMQ implements EventDataSource<RabbitMQMessage, DALDefaultEventBus> {

    private final String queueName;
    private byte[] lastMessage;
    private Channel channel;
    private final DALEventBus eventbus = new DALDefaultEventBus();

    /**
     * used to handle incoming messages from RabbitMQ.
     */
    private final DeliverCallback callback = (consumerTag, delivery) -> {
        RabbitMQMessage message = RabbitMQPackageReader.readPackage(delivery.getBody());
        this.eventbus.fireEvent(new RabbitMQDataReceivedEvent(this, message));
    };

    /**
     * Creates a RabbitMQ object with the specified queue name.
     * It sets up the RabbitMQ connection, channel, and consumer to receive messages from the specified queue.
     *
     * @param queueName the name of the queue to listen for messages
     */
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

    /**
     * Retrieves the data from RabbitMQ for the given identifier.
     *
     * @param identifier the identifier of the data to retrieve
     * @return the RabbitMQ message matching the identifier, or null if no message found
     */
    @Override
    public RabbitMQMessage getData(String identifier) {
        return null;
    }

    /**
     * Writes the provided RabbitMQ message to the data source with the specified key.
     *
     * @param key the key to associate with the message
     * @param value the RabbitMQ message to write
     */
    @Override
    public void writeData(String key, RabbitMQMessage value) {

    }


    /**
     * Registers a listener for data events triggered by a data source.
     *
     * @param listener the {@link DataSourceDataListener} to register
     */
    @Override
    public void registerEventListener(DataSourceDataListener listener) {
        this.eventbus.registerListener(listener);
    }

}
