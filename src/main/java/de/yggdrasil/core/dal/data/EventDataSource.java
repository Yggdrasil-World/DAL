package de.yggdrasil.core.dal.data;

import de.yggdrasil.core.dal.data.event.DALEventbus;
import de.yggdrasil.core.dal.data.event.DataSourceDataListener;

import java.lang.reflect.ParameterizedType;

public interface EventDataSource<T, E extends DALEventbus> extends DataSource<T>{

    void registerEventListener(DataSourceDataListener listener);

    default Class<E> getEventBusClass() {
        return (Class<E>) ((ParameterizedType) getClass().getGenericInterfaces()[1])
                .getActualTypeArguments()[0];
    }

}
