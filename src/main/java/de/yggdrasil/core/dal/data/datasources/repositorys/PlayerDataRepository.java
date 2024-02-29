package de.yggdrasil.core.dal.data.datasources.repositorys;

import de.yggdrasil.core.dal.data.datasources.models.PlayerData;
import net.bytemc.evelon.repository.Repository;
import net.bytemc.evelon.repository.filters.MatchFilter;

import java.util.UUID;

/**
 * The {@code PlayerDataRepository} class represents a repository for managing player data.
 */
public class PlayerDataRepository {

    private final Repository<PlayerData> playerDataRepository = Repository.create(PlayerData.class);

    /**
     * Retrieves the {@link PlayerData} of a player based on their {@link UUID}.
     *
     * @param player the UUID of the player
     * @return the player data
     */
    public PlayerData getPlayerData(UUID player){
        return playerDataRepository.query().filter(new MatchFilter("UUID",player)).database().findAll().get(0);
    }

    /**
     * Writes player data to the repository.
     *
     * @param playerData the player data to be written
     */
    public void writePlayerData(PlayerData playerData){
        this.playerDataRepository.query().create(playerData);
    }

}
