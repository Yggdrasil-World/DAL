package de.yggdrasil.core.dal.pipeline;

import de.yggdrasil.core.dal.requests.DALReadRequest;
import de.yggdrasil.core.dal.responses.DALResponse;

public interface ReadPipeline<T extends DALReadRequest,V extends DALResponse> extends Pipeline{

    V readBytes(T readRequest);

}
