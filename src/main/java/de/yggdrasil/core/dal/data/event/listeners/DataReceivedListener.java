package de.yggdrasil.core.dal.data.event.listeners;

import de.yggdrasil.core.dal.data.event.DataSourceDataEvent;
import de.yggdrasil.core.dal.data.event.DataSourceDataListener;

/**
 * The DataReceivedListener interface represents a listener for data received events triggered by a data source.
 * It extends the DataSourceDataListener interface and is a generic interface that takes a type parameter T.
 * T must be a subclass of DataSourceDataEvent.
 *
 * @param <T> the type of data event
 */
public interface DataReceivedListener<T extends DataSourceDataEvent> extends DataSourceDataListener<T> {



}
