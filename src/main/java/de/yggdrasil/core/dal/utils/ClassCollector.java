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

public class ClassCollector implements AdapterCollector, PipelineCollector, DatasourceCollector {

    private static Reflections reflections = new Reflections("de.yggdrasil");
    private static Set<Class<?>> ignored = reflections.getTypesAnnotatedWith(HideFromDefaultCollector.class);

    public static <T> Set<Class<? extends T>> collect(Class<T> type, Class<? extends Annotation> annotation){
        return reflections.getTypesAnnotatedWith(annotation).stream()
                .filter(type::isAssignableFrom)
                .filter(aClass -> {return  !ignored.contains(aClass);})
                .filter(aClass -> Arrays.stream(aClass.getConstructors()).anyMatch(constructor -> constructor.getParameterCount() == 0))
                .map(aClass -> (Class<? extends T>) aClass)
                .collect(Collectors.toUnmodifiableSet());
    }

    @Override
    public Set<Class<? extends Adapter>> collectAdapters() {
        return collect(Adapter.class, DALAdapter.class);
    }

    @Override
    public Set<Class<? extends DataSource>> collectDatasources() {
        return collect(DataSource.class, DALDatasource.class);
    }

    @Override
    public Set<Class<? extends Pipeline>> collectPipelines() {
        return collect(Pipeline.class, DALPipeline.class);
    }
}
