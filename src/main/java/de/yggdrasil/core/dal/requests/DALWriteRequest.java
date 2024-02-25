package de.yggdrasil.core.dal.requests;

import de.yggdrasil.core.dal.data.DataSource;

import java.lang.reflect.ParameterizedType;

/**
 * This interface represents a write request to a data source.
 * It extends the DALRequest interface.
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
        return (Class<T>) ((ParameterizedType) getClass().getGenericInterfaces()[0])
                .getActualTypeArguments()[0];
    }

}
