package de.yggdrasil.core.dal.data.event.events;

import de.yggdrasil.core.dal.data.DataSource;
import de.yggdrasil.core.dal.data.network.rabbitmq.RabbitMQMessage;

/**
 * This event contains the data source from which the data is received and the RabbitMQ message that contains the received data.
 */
public class RabbitMQDataReceivedEvent implements DataReceivedEvent{

    private final DataSource dataSource;
    private final RabbitMQMessage message;

    public RabbitMQDataReceivedEvent(DataSource dataSource, RabbitMQMessage message){
        this.dataSource = dataSource;
        this.message = message;
    }

    /**
     * Retrieves the data source of the Event.
     *
     * @return the data source from which the data is received
     */
    @Override
    public DataSource getSource() {
        return this.dataSource;
    }

    /**
     * Retrieves the RabbitMQ message associated with the Event.
     *
     * @return the RabbitMQ message
     */
    public RabbitMQMessage getMessage() {
        return message;
    }

}
