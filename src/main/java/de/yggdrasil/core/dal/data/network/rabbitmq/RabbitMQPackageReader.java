package de.yggdrasil.core.dal.data.network.rabbitmq;

import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * The RabbitMQPackageReader class is responsible for reading data packages from a byte array and
 * converting them into RabbitMQMessage objects.
 */
public class RabbitMQPackageReader {

    /**
     * Reads a data package and converts it into a RabbitMQMessage object.
     *
     * @param packageData the byte array containing the data package
     * @return the RabbitMQMessage object created from the data package
     */
    public static RabbitMQMessage readPackage(byte[] packageData){
        byte[] header = Arrays.copyOfRange(packageData, 0, 4);
        int keySize = ByteBuffer.wrap(header).getInt();
        byte[] key = Arrays.copyOfRange(packageData, 4, 4 + keySize);
        return new RabbitMQMessage(new String(key),
                Arrays.copyOfRange(packageData, keySize + 4, packageData.length));
    }

}
