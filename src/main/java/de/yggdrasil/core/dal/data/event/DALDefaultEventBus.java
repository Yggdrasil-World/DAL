package de.yggdrasil.core.dal.data.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * The DALDefaultEventBus class is an implementation of the DALEventbus interface.
 * It provides methods for triggering events and registering event listeners.
 */
public class DALDefaultEventBus implements DALEventbus{

    private final HashMap<Class<? extends DataSourceDataEvent>, List<DataSourceDataListener>> eventListeners = new HashMap<>();

    /**
     * Triggers an event on the event bus, notifying all registered event listeners.
     *
     * @param event the data source data event to trigger
     * @return true if the event was triggered successfully, false otherwise
     */
    @Override
    public boolean triggerEvent(DataSourceDataEvent event) {
        for (DataSourceDataListener listener:
                this.eventListeners.get(event.getClass())) {
                listener.handleEvent(event);
        }
        return true;
    }

    /**
     * Registers a listener for data events triggered by a data source.
     *
     * @param listener the listener to register
     */
    @Override
    public void registerListener(DataSourceDataListener listener) {
        if (!this.eventListeners.containsKey(listener.getEventClass())){
            List<DataSourceDataListener> listeners = new ArrayList<>();
            listeners.add(listener);
            this.eventListeners.put(listener.getEventClass(), listeners);
        } else {
            this.eventListeners.get(listener.getEventClass()).add(listener);
        }
    }
}
