package de.yggdrasil.core.dal.requests.implementations;

import de.yggdrasil.core.dal.data.datasources.PlayerDataDataSource;
import de.yggdrasil.core.dal.requests.DALReadRequest;

import java.util.UUID;


/**
 * Represents a read request for player data from the {@PlayerDataDataSource}. It uses the Player UUID to request the desired dataset.
 */
public class PlayerDataReadRequest implements DALReadRequest<PlayerDataDataSource> {

    private final UUID uuid;

    public PlayerDataReadRequest(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public String getIdentifier() {
        return this.uuid.toString();
    }

}
