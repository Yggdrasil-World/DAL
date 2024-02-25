package de.yggdrasil.core.dal.utils;

/**
 * This class provides utility methods for working with byte arrays.
 */
public class ByteUtils {

    /**
     * Concatenates two byte arrays into a single byte array.
     *
     * @param arrayA the first byte array
     * @param arrayB the second byte array
     * @return a new byte array containing the concatenated elements of arrayA followed by arrayB
     */
    public static byte[] connectByteArrays(byte[] arrayA, byte[] arrayB){
        byte[] result = new byte[arrayA.length+arrayB.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = i < arrayA.length ? arrayA[i] : arrayB[i-arrayA.length];
        }
        return result;
    }

}
