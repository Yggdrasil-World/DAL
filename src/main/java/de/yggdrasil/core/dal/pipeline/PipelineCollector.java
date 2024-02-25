package de.yggdrasil.core.dal.pipeline;

import java.util.Set;

/**
 * The PipelineCollector interface is responsible for collecting classes that are annotated with the `DALPipeline` annotation and are assignable from the `Pipeline` class.
 */
public interface PipelineCollector {

    /**
     * Collects classes that are annotated with the {@code DALPipeline} annotation and are assignable from the {@code Pipeline} class.
     *
     * @return a set of classes that implement the {@code Pipeline} interface
     */
    Set<Class<? extends Pipeline>> collectPipelines();

}
