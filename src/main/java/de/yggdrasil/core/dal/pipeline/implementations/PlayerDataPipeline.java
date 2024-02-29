package de.yggdrasil.core.dal.pipeline.implementations;

import de.yggdrasil.core.dal.data.DatasourceInstanceCollection;
import de.yggdrasil.core.dal.data.datasources.PlayerDataDataSource;
import de.yggdrasil.core.dal.pipeline.ReadPipeline;
import de.yggdrasil.core.dal.pipeline.WritePipeline;
import de.yggdrasil.core.dal.requests.implementations.PlayerDataReadRequest;
import de.yggdrasil.core.dal.requests.implementations.PlayerDataWriteRequest;
import de.yggdrasil.core.dal.responses.implementations.PlayerDataResponse;

public class PlayerDataPipeline implements ReadPipeline<PlayerDataReadRequest, PlayerDataResponse>, WritePipeline<PlayerDataWriteRequest> {

    /**
     * Reads player data from the PlayerDataDataSource based on the provided PlayerDataReadRequest.
     *
     * @param readRequest the read request for player data
     * @return a PlayerDataResponse object containing the player data
     */
    @Override
    public PlayerDataResponse readData(PlayerDataReadRequest readRequest) {
        return new PlayerDataResponse(DatasourceInstanceCollection.get().getDatasource(PlayerDataDataSource.class).getData(readRequest.getIdentifier()));
    }

    /**
     * Writes the provided data to the PlayerDataDataSource.
     *
     * @param writeRequest the write request containing the data to be written
     */
    @Override
    public void writeData(PlayerDataWriteRequest writeRequest) {
        DatasourceInstanceCollection.get().getDatasource(PlayerDataDataSource.class).writeData(null, writeRequest.getData());
    }

}
