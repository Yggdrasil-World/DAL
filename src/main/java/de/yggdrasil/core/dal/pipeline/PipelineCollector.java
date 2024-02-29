package de.yggdrasil.core.dal.pipeline;

import java.util.Set;

/**
 * The PipelineCollector interface is responsible for collecting `Pipeline` classes
 */
public interface PipelineCollector {

    /**
     * Collects Pipeline classes.
     *
     * @return a set of classes that are assignable from the `Pipeline` class.
     */
    Set<Class<? extends Pipeline>> collectPipelines();

}
