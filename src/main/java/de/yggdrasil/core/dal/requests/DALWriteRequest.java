package de.yggdrasil.core.dal.requests;

import de.yggdrasil.core.dal.data.DataSource;

import java.lang.reflect.ParameterizedType;

/**
 * This interface represents a write request to a data source.
 *
 * @param <T> the type of the data source
 * @param <V> the type of the data being written
 */
public interface DALWriteRequest<T extends DataSource,V> extends DALRequest{

    String getKey();

    V getData();

    /**
     * Gets the class of the data source.
     *
     * @return the class of the data source
     */
    default Class<T> getDatasourceClass() {
        if (getClass().getGenericInterfaces().length == 0
                || !(getClass().getGenericInterfaces()[0] instanceof ParameterizedType type)) return null;
        if (type.getActualTypeArguments().length == 0) return null;
        return (Class<T>) type.getActualTypeArguments()[0];
    }

}
