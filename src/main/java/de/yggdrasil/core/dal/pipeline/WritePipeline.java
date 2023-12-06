package de.yggdrasil.core.dal.pipeline;

import de.yggdrasil.core.dal.requests.DALWriteRequest;

public interface WritePipeline<T extends DALWriteRequest> extends Pipeline{

    void writeData(T writeRequest);

}
