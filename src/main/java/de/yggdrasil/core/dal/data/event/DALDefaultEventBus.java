package de.yggdrasil.core.dal.data.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * The DALDefaultEventBus class is an implementation of the DALEventBus interface.
 * It provides methods for triggering events and registering event listeners.
 */
public class DALDefaultEventBus implements DALEventBus {

    private final HashMap<Class<? extends DataSourceDataEvent>, List<DataSourceDataListener>> eventListeners = new HashMap<>();

    @Override
    public boolean fireEvent(DataSourceDataEvent event) {
        for (DataSourceDataListener listener:
                this.eventListeners.get(event.getClass())) {
                listener.handleEvent(event);
        }
        return true;
    }

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
