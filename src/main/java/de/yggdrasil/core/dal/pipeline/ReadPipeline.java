package de.yggdrasil.core.dal.pipeline;

import de.yggdrasil.core.dal.requests.DALReadRequest;
import de.yggdrasil.core.dal.responses.DALResponse;

/**
 * ReadPipeline interface represents a pipeline for reading data from a data source.
 *
 * @param <T> the type of the read request
 * @param <V> the type of the response
 */
public interface ReadPipeline<T extends DALReadRequest,V extends DALResponse> extends Pipeline{

    V readData(T readRequest);

}
