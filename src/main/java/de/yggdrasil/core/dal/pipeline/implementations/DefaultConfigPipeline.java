package de.yggdrasil.core.dal.pipeline.implementations;

import de.yggdrasil.core.dal.data.DatasourceLibrary;
import de.yggdrasil.core.dal.pipeline.ReadPipeline;
import de.yggdrasil.core.dal.pipeline.WritePipeline;
import de.yggdrasil.core.dal.requests.implementations.ConfigReadRequest;
import de.yggdrasil.core.dal.requests.implementations.ConfigWriteRequest;
import de.yggdrasil.core.dal.responses.implementations.ConfigResponse;

public class DefaultConfigPipeline implements
        ReadPipeline<ConfigReadRequest, ConfigResponse>, WritePipeline<ConfigWriteRequest> {

    @Override
    public ConfigResponse readBytes(ConfigReadRequest readRequest) {
        return new ConfigResponse(DatasourceLibrary.get()
                .getDatasource(readRequest.getDatasourceClass())
                .getData(readRequest.getIdentifier()));
    }

    @Override
    public void writeBytes(ConfigWriteRequest writeRequest) {
        DatasourceLibrary.get().getDatasource(writeRequest.getDatasourceClass())
                .writeData(writeRequest.getKey(), writeRequest.getData());
    }

}
