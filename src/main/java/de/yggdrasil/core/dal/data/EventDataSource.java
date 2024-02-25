package de.yggdrasil.core.dal.data;

import de.yggdrasil.core.dal.data.event.DALEventbus;
import de.yggdrasil.core.dal.data.event.DataSourceDataListener;

import java.lang.reflect.ParameterizedType;

/**
 * The EventDataSource interface extends the DataSource interface and represents a data source that supports event-based communication.
 * It provides methods to register event listeners and retrieve the class of the event bus associated with the data source.
 *
 * @param <T> the type of data stored in the data source
 * @param <E> the type of the event bus associated with the data source
 */
public interface EventDataSource<T, E extends DALEventbus> extends DataSource<T>{

    void registerEventListener(DataSourceDataListener listener);

    /**
     * Returns the class of the event bus associated with the data source.
     *
     * @param <E> the type of the event bus associated with the data source
     * @return the class of the event bus
     */
    default Class<E> getEventBusClass() {
        return (Class<E>) ((ParameterizedType) getClass().getGenericInterfaces()[1])
                .getActualTypeArguments()[0];
    }

}
