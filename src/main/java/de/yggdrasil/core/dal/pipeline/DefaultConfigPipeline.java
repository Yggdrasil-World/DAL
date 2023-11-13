package de.yggdrasil.core.dal.pipeline;

import de.yggdrasil.core.dal.data.DatasourceLibrary;
import de.yggdrasil.core.dal.requests.DALConfigReadRequest;
import de.yggdrasil.core.dal.requests.DALConfigWriteRequest;
import de.yggdrasil.core.dal.responses.DALConfigResponse;

public class DefaultConfigPipeline implements
        ReadPipeline<DALConfigReadRequest,DALConfigResponse>,WritePipeline<DALConfigWriteRequest> {

    @Override
    public DALConfigResponse readBytes(DALConfigReadRequest readRequest) {
        return new DALConfigResponse(new String(
                DatasourceLibrary.get().getDatasource(readRequest.getDatasourceClass())
                        .getBytes(readRequest.getIdentifier())
        ));
    }

    @Override
    public void writeBytes(DALConfigWriteRequest writeRequest) {
        DatasourceLibrary.get().getDatasource(writeRequest.getDatasourceClass())
                .writeBytes(writeRequest.getKey(), writeRequest.getData().getBytes());
    }
}
