package de.yggdrasil.core.dal.adapter;

import java.util.Set;

/**
 * The AdapterCollector interface is responsible for collecting `Adapter` classes
 */
public interface AdapterCollector {

    /**
     * Collects Adapter classes.
     *
     * @return a set of classes that are assignable from the `Adapter` class.
     */
    Set<Class<? extends Adapter>> collectAdapters();

}
