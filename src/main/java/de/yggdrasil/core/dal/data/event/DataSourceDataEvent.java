package de.yggdrasil.core.dal.data.event;

import de.yggdrasil.core.dal.data.DataSource;

/**
 * The DataSourceDataEvent interface represents an event that is triggered by a data source.
 * It can be used as a base interface for other specific data events.
 */
public interface DataSourceDataEvent {

    DataSource getSource();



}
