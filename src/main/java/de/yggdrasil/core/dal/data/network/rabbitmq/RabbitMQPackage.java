package de.yggdrasil.core.dal.data.network.rabbitmq;

import de.yggdrasil.core.dal.data.network.Package;
import de.yggdrasil.core.dal.utils.ByteArrayUtils;

import java.nio.ByteBuffer;

/**
 * Implementation of the Package interface for RabbitMQ.
 */
public class RabbitMQPackage implements Package {

    private final byte[] header;
    private final byte[] data;

    public RabbitMQPackage(RabbitMQMessage message){
        header = ByteBuffer.allocate(4).putInt(message.key().length()).array();
        data = ByteArrayUtils.connectByteArrays(message.key().getBytes(), message.content());
    }

    /**
     * Converts the Package object to a byte array.
     *
     * @return a byte array representation of the RabbitMQPackage object
     */
    @Override
    public byte[] toBytes() {
        return ByteArrayUtils.connectByteArrays(header, data);
    }


}
