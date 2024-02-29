package de.yggdrasil.core.dal.utils;

import de.yggdrasil.core.dal.adapter.Adapter;
import de.yggdrasil.core.dal.adapter.AdapterCollector;
import de.yggdrasil.core.dal.adapter.DALAdapter;
import de.yggdrasil.core.dal.data.DALDatasource;
import de.yggdrasil.core.dal.data.DataSource;
import de.yggdrasil.core.dal.data.DatasourceCollector;
import de.yggdrasil.core.dal.data.HideFromDefaultCollector;
import de.yggdrasil.core.dal.pipeline.DALPipeline;
import de.yggdrasil.core.dal.pipeline.Pipeline;
import de.yggdrasil.core.dal.pipeline.PipelineCollector;
import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * The ClassCollector class is responsible for collecting classes annotated with specific annotations and assignable from specific class.
 * It implements default implementations for the AdapterCollector, PipelineCollector, and DatasourceCollector interfaces.
 */
public class ClassCollector implements AdapterCollector, PipelineCollector, DatasourceCollector {

    private static Reflections reflections = new Reflections("de.yggdrasil");
    private static Set<Class<?>> ignored = reflections.getTypesAnnotatedWith(HideFromDefaultCollector.class);

    /**
     * Collects classes that are annotated with a specific annotation and are assignable from a given type.
     *
     * @param <T> the type to collect
     * @param type the type to check for assignability
     * @param annotation the annotation class to filter by
     * @return a set of classes that are annotated with the given annotation and are assignable from the given type
     */
    public static <T> Set<Class<? extends T>> collect(Class<T> type, Class<? extends Annotation> annotation){
        return reflections.getTypesAnnotatedWith(annotation).stream()
                .filter(type::isAssignableFrom)
                .filter(aClass -> {return  !ignored.contains(aClass);})
                .filter(aClass -> Arrays.stream(aClass.getConstructors()).anyMatch(constructor -> constructor.getParameterCount() == 0))
                .map(aClass -> (Class<? extends T>) aClass)
                .collect(Collectors.toUnmodifiableSet());
    }

    /**
     * Collects all classes that are annotated with the `DALAdapter` annotation and are assignable from the `Adapter` class.
     *
     * @return a set of classes that are annotated with `DALAdapter` and are assignable from `Adapter`
     */
    @Override
    public Set<Class<? extends Adapter>> collectAdapters() {
        return collect(Adapter.class, DALAdapter.class);
    }

    /**
     * Collects all classes that are annotated with the `DALDatasource` annotation and are assignable from the `DataSource` class.
     *
     * @return a set of classes that are annotated with `DALDatasource` and are assignable from `DataSource`
     */
    @Override
    public Set<Class<? extends DataSource>> collectDatasources() {
        return collect(DataSource.class, DALDatasource.class);
    }

    /**
     * Collects all classes that are annotated with the `DALPipeline` annotation
     * and are assignable from the `Pipeline` class.
     *
     * @return a set of classes that are annotated with `DALPipeline` and
     * are assignable from `Pipeline`
     */
    @Override
    public Set<Class<? extends Pipeline>> collectPipelines() {
        return collect(Pipeline.class, DALPipeline.class);
    }
}
