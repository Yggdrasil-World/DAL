package de.yggdrasil.core.dal.data;

import java.util.Set;

/**
 * The DataSourceCollector interface is responsible for collecting `Datasource` classes
 */
public interface DatasourceCollector {

    /**
     * Collects Datasource classes.
     *
     * @return a set of classes that are assignable from the `Datasource` class.
     */
    Set<Class<? extends DataSource>> collectDatasources();

}
