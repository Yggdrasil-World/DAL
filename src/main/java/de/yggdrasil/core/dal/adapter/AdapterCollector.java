package de.yggdrasil.core.dal.adapter;

import java.util.Set;

/**
 * The AdapterCollector interface is responsible for collecting classes that are annotated with the `DALAdapter` annotation and are assignable from the `Adapter` class.
 */
public interface AdapterCollector {

    /**
     * Collects all classes that are annotated with the `DALAdapter` annotation, which is used to mark a class as an adapter for converting data between different formats.
     * These classes should be assignable from the `Adapter` class.
     *
     * @return a set of classes that are annotated with the `DALAdapter` annotation and are assignable from the `Adapter` class.
     */
    Set<Class<? extends Adapter>> collectAdapters();

}
