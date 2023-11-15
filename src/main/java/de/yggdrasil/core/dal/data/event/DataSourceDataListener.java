package de.yggdrasil.core.dal.data.event;

import java.lang.reflect.ParameterizedType;

public interface DataSourceDataListener<T extends DataSourceDataEvent> {
    void handleEvent(T event);

    default Class<T> getEventClass() {
        return (Class<T>) ((ParameterizedType) getClass().getGenericInterfaces()[0])
                .getActualTypeArguments()[0];
    }

}
