package de.yggdrasil.core.dal.utils;

/**
 * This class provides utility methods for working with byte arrays.
 */
public class ByteArrayUtils {

    /**
     * Concatenates two byte arrays into a single byte array.
     *
     * @param arrayA the first byte array
     * @param arrayB the second byte array
     * @return a new byte array containing the concatenated elements of arrayA followed by arrayB
     */
    public static byte[] connectByteArrays(byte[] arrayA, byte[] arrayB){
        byte[] newArray = Arrays.copyOf(arrayA, arrayA.length + arrayB.length);
        System.arraycopy(arrayB, 0, newArray, arrayA.length, arrayB.length);
        return newArray;
    }

}
