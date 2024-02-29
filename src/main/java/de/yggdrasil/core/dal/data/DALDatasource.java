package de.yggdrasil.core.dal.data;

import java.lang.annotation.*;

/**
 * The DALDatasource annotation is used to mark a class as a data source that can be collected by the ClassCollector.
 * Classes marked with this annotation must implement the DataSource interface.
 * The annotation is also inherited by subclasses.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface DALDatasource { }