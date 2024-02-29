package de.yggdrasil.core.dal.pipeline;

import de.yggdrasil.core.dal.requests.DALWriteRequest;

/**
 * The WritePipeline interface represents a pipeline for writing data to a data source.
 *
 * @param <T> the type of the write request
 */
public interface WritePipeline<T extends DALWriteRequest> extends Pipeline{

    void writeData(T writeRequest);

}
