package de.yggdrasil.core.dal.data.datasources;

import de.yggdrasil.core.dal.data.DataSource;
import de.yggdrasil.core.dal.data.datasources.models.PlayerData;
import de.yggdrasil.core.dal.data.datasources.repositorys.PlayerDataRepository;

import java.util.UUID;

/**
 * The {@code PlayerDataDataSource} class implements the {@code DataSource} interface with a type parameter of {@code PlayerData}.
 * It provides methods to retrieve and write player related data from or to the {@code PlayerDataRepository}.
 *
 * @param <PlayerData> the type of data stored in the data source
 */
public class PlayerDataDataSource implements DataSource<PlayerData> {

    private final PlayerDataRepository repository = new PlayerDataRepository();

    @Override
    public PlayerData getData(String identifier) {
        return repository.getPlayerData(UUID.fromString(identifier));
    }

    @Override
    public void writeData(String key, PlayerData value) {
        repository.writePlayerData(value);
    }

}
