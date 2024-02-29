package de.yggdrasil.core.dal.adapter;

import de.yggdrasil.core.dal.utils.GenericTypeUtils;

/**
 * The Adapter interface is responsible for converting data between different formats.
 * Implementations of this interface should provide methods to convert an object to data and create an object from data.
 *
 * @param <T> the type of object that the adapter works with
 */
@DALAdapter
public interface Adapter<T> {

    /**
     * Converts an object to data in the form of a byte array.
     *
     * @param object the object to be converted to data
     * @return a byte array representing the converted data
     */
    byte[] convertToData(T object);

    /**
     * Creates an object of type T from the given byte data.
     *
     * @param data the byte data used to create the object
     * @return an object of type T created from the byte data
     */
    T createFromData(byte[] data);

    /**
     * Returns the type of object that the adapter works with.
     *
     * @param <T> the type of object that the adapter works with
     * @return the class object representing the type of object that the adapter works with
     */
    default Class<T> getAdapterType() {
        return (Class<T>) GenericTypeUtils.getParameterTypeClass(this.getClass());
    }

}
