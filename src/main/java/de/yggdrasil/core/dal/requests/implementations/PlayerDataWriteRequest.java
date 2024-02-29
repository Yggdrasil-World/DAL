package de.yggdrasil.core.dal.requests.implementations;

import de.yggdrasil.core.dal.data.datasources.PlayerDataDataSource;
import de.yggdrasil.core.dal.data.datasources.models.PlayerData;
import de.yggdrasil.core.dal.requests.DALWriteRequest;


/**
 * Represents a write request for PlayerData objects to be written to the PlayerDataDataSource (overwrites existing playerdata for the player contained in the data object).
 */
public class PlayerDataWriteRequest implements DALWriteRequest<PlayerDataDataSource, PlayerData> {

    private final PlayerData playerData;

    public PlayerDataWriteRequest(PlayerData playerData){
        this.playerData = playerData;
    }

    // the return value of this method is not used in the pipeline
    @Override
    public String getKey() {
        return null;
    }

    @Override
    public PlayerData getData() {
        return this.playerData;
    }

}
