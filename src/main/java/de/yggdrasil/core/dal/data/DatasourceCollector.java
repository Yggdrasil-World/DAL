package de.yggdrasil.core.dal.data;

import java.util.Set;

/**
 * The DatasourceCollector interface is responsible for collecting classes that are annotated with the `DALDatasource` annotation and are assignable from the `DataSource` class.
 *
 * This interface extends the AdapterCollector and PipelineCollector interfaces.
 * It provides a method `collectDatasources()` which returns a set of classes that are annotated with `DALDatasource` and are assignable from `DataSource`.
 */
public interface DatasourceCollector {

    /**
     * Collects all classes that are annotated with the `DALDatasource` annotation and are assignable from the `DataSource` class.
     *
     * @return a set of classes that are annotated with `DALDatasource` and are assignable from `DataSource`
     */
    Set<Class<? extends DataSource>> collectDatasources();

}
