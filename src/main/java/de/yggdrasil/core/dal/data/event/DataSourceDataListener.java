package de.yggdrasil.core.dal.data.event;

import de.yggdrasil.core.dal.utils.GenericTypeUtils;

/**
 * The DataSourceDataListener interface represents a listener for data events triggered by a data source.
 *
 * @param <T> the type of data event
 */
public interface DataSourceDataListener<T extends DataSourceDataEvent> {
    void handleEvent(T event);

    /**
     * Returns the class of the event associated with this listener.
     *
     * @param <T> the type of the event
     * @return the class of the event
     */
    default Class<T> getEventClass() {
        return (Class<T>) GenericTypeUtils.getParameterTypeClass(this.getClass());
    }

}
