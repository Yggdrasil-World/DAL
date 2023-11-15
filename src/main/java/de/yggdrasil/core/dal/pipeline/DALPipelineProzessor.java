package de.yggdrasil.core.dal.pipeline;

import de.yggdrasil.core.dal.adapter.AdapterLibrary;
import de.yggdrasil.core.dal.exceptions.MissingPipelineException;
import de.yggdrasil.core.dal.requests.DALReadRequest;
import de.yggdrasil.core.dal.requests.DALWriteRequest;
import de.yggdrasil.core.dal.responses.DALResponse;
import de.yggdrasil.core.dal.strings.logging.DALPipelineProzessorLogger;
import de.yggdrasil.core.dal.utils.ClassCollector;
import de.yggdrasil.core.dal.utils.ReflectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class DALPipelineProzessor {

    private final AdapterLibrary library = new AdapterLibrary();
    private Logger logger = LogManager.getLogger(DALPipelineProzessor.class);
    private HashMap<Class<? extends DALWriteRequest>, Class<? extends WritePipeline>> writePipelineMap = new HashMap<>();
    private HashMap<Class<? extends DALReadRequest>, Class<? extends ReadPipeline<?, ?>>> readPipelineMap = new HashMap<>();

    {
        setup();
    }

    private void setup() {
        this.addPipelineCollection(new ClassCollector());
    }

    public <T extends DALReadRequest, V extends DALResponse> V readData(T readRequest) throws MissingPipelineException {
        try {
            Class<? extends ReadPipeline<T, V>> pipelineClass = (Class<? extends ReadPipeline<T, V>>) this.readPipelineMap.get(readRequest.getClass());
            ReadPipeline<T, V> pipeline = pipelineClass.getDeclaredConstructor().newInstance();
            return pipeline.readBytes(readRequest);
        } catch (InstantiationException | InvocationTargetException | NoSuchMethodException |
                 IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public void writeData(DALWriteRequest writeRequest) throws MissingPipelineException {
        try {
            this.writePipelineMap.get(writeRequest.getClass()).getDeclaredConstructor().newInstance().writeBytes(writeRequest);
        } catch (InstantiationException | NoSuchMethodException | InvocationTargetException |
                 IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public void addPipelineCollection(PipelineCollector collector) {
        int count = 0;
        for (Class<? extends Pipeline> pipelineClass :
                collector.collectPipelines()) {
            this.addPipeline(pipelineClass);
            count++;
        }
        logger.info(DALPipelineProzessorLogger.ADD_PIPELINE_COLLECTION.formatted(count, readPipelineMap.size()));
        logger.info(DALPipelineProzessorLogger.ADD_PIPELINE_COLLECTION.formatted(count, writePipelineMap.size()));
    }

    public void addPipeline(Class<? extends Pipeline> pipelineClass) {
        if (ReadPipeline.class.isAssignableFrom(pipelineClass)) {
            Class<? extends DALReadRequest> readRequestClass =
                    ReflectionUtils.getMatchingGenericTypeArgument(pipelineClass, ReadPipeline.class);
            Class<ReadPipeline<?,?>> readPipeline = (Class<ReadPipeline<?, ?>>) pipelineClass;
            readPipelineMap.put(readRequestClass, readPipeline);
        }
        if (WritePipeline.class.isAssignableFrom(pipelineClass)) {
            Class<? extends DALWriteRequest> writeRequestClass =
                    ReflectionUtils.getMatchingGenericTypeArgument(pipelineClass, WritePipeline.class);
            writePipelineMap.put(writeRequestClass, pipelineClass.asSubclass(WritePipeline.class));
        }
    }

}