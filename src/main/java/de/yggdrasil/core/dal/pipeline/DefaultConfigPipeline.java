package de.yggdrasil.core.dal.pipeline;

import de.yggdrasil.core.dal.requests.DALConfigReadRequest;
import de.yggdrasil.core.dal.requests.DALConfigWriteRequest;
import de.yggdrasil.core.dal.responses.DALConfigResponse;

public class DefaultConfigPipeline implements
        ReadPipeline<DALConfigReadRequest,DALConfigResponse>,WritePipeline<DALConfigWriteRequest> {

    @Override
    public DALConfigResponse readBytes(DALConfigReadRequest readRequest) {
        return new DALConfigResponse("mymockdata");
    }

    @Override
    public void writeBytes(DALConfigWriteRequest writeRequest) {
        System.out.println("It works");
    }
}
