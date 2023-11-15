package de.yggdrasil.core.dal.adapter;

import java.lang.reflect.ParameterizedType;

@DALAdapter
public interface Adapter<T> {

    byte[] convertToData(T object);

    T createFromData(byte[] data);

    default Class<T> getAdapterType() {
        return (Class<T>) ((ParameterizedType) getClass().getGenericInterfaces()[0])
                .getActualTypeArguments()[0];
    }

}
