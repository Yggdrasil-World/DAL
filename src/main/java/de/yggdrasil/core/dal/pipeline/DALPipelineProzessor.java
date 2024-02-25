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

/**
 * The DALPipelineProzessor class represents a processor for data access layer (DAL) pipelines.
 * It provides methods for reading and writing data using the defined pipelines.
 */
public class DALPipelineProzessor {

    private final AdapterLibrary library = new AdapterLibrary();
    private Logger logger = LogManager.getLogger(DALPipelineProzessor.class);
    private HashMap<Class<? extends DALWriteRequest>, Class<? extends WritePipeline>> writePipelineMap = new HashMap<>();
    private HashMap<Class<? extends DALReadRequest>, Class<? extends ReadPipeline<?, ?>>> readPipelineMap = new HashMap<>();

    {
        setup();
    }

    /**
     * Initializes the pipeline collection by adding a ClassCollector instance.
     * This method is used internally and is called during object initialization.
     * It adds the ClassCollector instance to the pipeline collection.
     */
    private void setup() {
        this.addPipelineCollection(new ClassCollector());
    }

    /**
     * Reads data from the data source using the specified read request.
     *
     * @param readRequest the read request
     * @param <T> the type of the read request
     * @param <V> the type of the response
     * @return the response containing the data read from the data source
     * @throws MissingPipelineException if there is no pipeline available for the read request
     */
    public <T extends DALReadRequest, V extends DALResponse> V readData(T readRequest) throws MissingPipelineException {
        try {
            Class<? extends ReadPipeline<T, V>> pipelineClass = (Class<? extends ReadPipeline<T, V>>) this.readPipelineMap.get(readRequest.getClass());
            ReadPipeline<T, V> pipeline = pipelineClass.getDeclaredConstructor().newInstance();
            return pipeline.readData(readRequest);
        } catch (InstantiationException | InvocationTargetException | NoSuchMethodException |
                 IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Writes data to a data source using a write request.
     *
     * @param writeRequest the write request to be used for writing the data
     * @throws MissingPipelineException if there is no pipeline available for the write request
     */
    public void writeData(DALWriteRequest writeRequest) throws MissingPipelineException {
        try {
            this.writePipelineMap.get(writeRequest.getClass()).getDeclaredConstructor().newInstance().writeData(writeRequest);
        } catch (InstantiationException | NoSuchMethodException | InvocationTargetException |
                 IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Adds a collection of pipelines to the pipeline processor.
     *
     * @param collector the PipelineCollector instance responsible for collecting pipeline classes
     */
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

    /**
     * Adds a pipeline class to the pipeline processor.
     * The pipeline class can be a subclass of ReadPipeline or WritePipeline.
     *
     * @param pipelineClass the class of the pipeline to be added
     */
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