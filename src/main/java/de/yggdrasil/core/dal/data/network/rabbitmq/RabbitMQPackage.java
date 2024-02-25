package de.yggdrasil.core.dal.data.network.rabbitmq;

import de.yggdrasil.core.dal.data.network.Package;
import de.yggdrasil.core.dal.utils.ByteUtils;

import java.nio.ByteBuffer;

/**
 * Represents a RabbitMQ package that implements the Package interface.
 * A RabbitMQPackage consists of a header and data.
 */
public class RabbitMQPackage implements Package {

    private final byte[] header;
    private final byte[] data;

    /**
     * Represents a RabbitMQ package that implements the Package interface.
     * A RabbitMQPackage consists of a header and data.
     */
    public RabbitMQPackage(RabbitMQMessage message){
        header = ByteBuffer.allocate(4).putInt(message.key().length()).array();
        data = ByteUtils.connectByteArrays(message.key().getBytes(), message.content());
    }

    /**
     * Converts the RabbitMQPackage object to a byte array.
     *
     * @return a byte array representation of the RabbitMQPackage object
     */
    @Override
    public byte[] toBytes() {
        return ByteUtils.connectByteArrays(header, data);
    }


}
