package de.yggdrasil.core.dal.data.event;

/**
 * The DALEventbus interface represents an event bus for triggering and handling data events.
 * It provides methods for triggering events and registering event listeners.
 */
public interface DALEventbus {

    /**
     * Triggers an event on the event bus, notifying all registered event listeners.
     *
     * @param event the data source data event to trigger
     * @return true if the event was triggered successfully, false otherwise
     */
    boolean triggerEvent(DataSourceDataEvent event);

    /**
     * Registers a listener for data events triggered by a data source.
     *
     * @param listener the listener to register
     */
    void registerListener(DataSourceDataListener listener);

}
