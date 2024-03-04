package de.yggdrasil.core.dal.pipeline.implementations;

import de.yggdrasil.core.dal.data.DatasourceInstanceCollection;
import de.yggdrasil.core.dal.pipeline.ReadPipeline;
import de.yggdrasil.core.dal.pipeline.WritePipeline;
import de.yggdrasil.core.dal.requests.implementations.ConfigReadRequest;
import de.yggdrasil.core.dal.requests.implementations.ConfigWriteRequest;
import de.yggdrasil.core.dal.responses.implementations.ConfigResponse;

public class ConfigPipeline implements
        ReadPipeline<ConfigReadRequest, ConfigResponse>, WritePipeline<ConfigWriteRequest> {

    /**
     * Reads data from a data source based on the provided read request.
     *
     * @param readRequest the read request containing the information necessary to read the data
     * @return a ConfigResponse object containing the data retrieved from the data source
     */
    @Override
    public ConfigResponse readData(ConfigReadRequest readRequest) {
        return new ConfigResponse(DatasourceInstanceCollection.get()
                .getDatasource(readRequest.getDatasourceClass())
                .getData(readRequest.getIdentifier()));
    }

    /**
     * Writes data to a data source based on the provided write request.
     *
     * @param writeRequest the write request containing the information necessary to write the data
     */
    @Override
    public void writeData(ConfigWriteRequest writeRequest) {
        DatasourceInstanceCollection.get().getDatasource(writeRequest.getDatasourceClass())
                .writeData(writeRequest.getKey(), writeRequest.getData());
    }

}
