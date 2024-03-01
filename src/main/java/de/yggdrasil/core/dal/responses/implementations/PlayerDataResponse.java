package de.yggdrasil.core.dal.responses.implementations;

import de.yggdrasil.core.dal.data.datasources.models.PlayerData;
import de.yggdrasil.core.dal.responses.DALResponse;


/**
 * The PlayerDataResponse class contains player related data from the {@PlayerDataDataSource}.
 */
public class PlayerDataResponse implements DALResponse<PlayerData> {

    private PlayerData data;

    public PlayerDataResponse(PlayerData playerData){
        this.data = playerData;
    }

    @Override
    public PlayerData data() {
        return this.data;
    }

}
