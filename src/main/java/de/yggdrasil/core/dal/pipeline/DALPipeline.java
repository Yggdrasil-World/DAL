package de.yggdrasil.core.dal.pipeline;

import java.lang.annotation.*;

/**
 * The DALPipeline annotation is used to mark classes that represent a pipeline for reading
 * or writing data from/to a data source.
 *
 * The annotation is meant to be used in combination with the Pipeline interface.
 *
 * The PipelineCollector interface can be used to collect all classes annotated with DALPipeline
 * that are assignable from Pipeline.
 *
 * Example Usage:
 *
 * // Interface definition
 * @DALPipeline
 * public interface Pipeline { }
 *
 * // Class implementing Pipeline interface
 * @DALPipeline
 * public class MyPipeline implements Pipeline { }
 *
 * // Class implementing Pipeline interface but not annotated
 * public class NonAnnotatedPipeline implements Pipeline { }
 *
 * // Class implementing Pipeline interface but annotated with HideFromDefaultCollector
 * @DALPipeline
 * @HideFromDefaultCollector
 * public class HiddenPipeline implements Pipeline { }
 *
 * // Class using the PipelineCollector to collect pipelines
 * public class PipelineCollectorExample implements PipelineCollector {
 *
 *     @Override
 *     public Set<Class<? extends Pipeline>> collectPipelines() {
 *         // Collecting all classes annotated with DALPipeline and
 *         // assignable from Pipeline
 *         return ClassCollector.collect(Pipeline.class, DALPipeline.class);
 *     }
 * }
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface DALPipeline { }
