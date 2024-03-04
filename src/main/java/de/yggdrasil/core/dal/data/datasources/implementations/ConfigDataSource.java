package de.yggdrasil.core.dal.data.datasources.implementations;

import com.google.gson.Gson;
import de.yggdrasil.core.dal.data.DataSource;
import de.yggdrasil.core.dal.data.datasources.models.ConfigJSON;
import de.yggdrasil.core.dal.data.datasources.repositorys.ConfigRepository;

/**
 * The {@code ConfigDataSource} class implements the {@code DataSource} interface with a type parameter of {@code String}.
 * It provides methods to retrieve and write configuration data from or to the {@code ConfigRepository}.
 *
 * @param <String> the type of data stored in the data source
 */
public class ConfigDataSource implements DataSource<ConfigJSON> {


    private final ConfigRepository configRepository = new ConfigRepository();

    /**
     * Retrieves the data associated with the given identifier from the ConfigRepository.
     *
     * @param identifier the identifier used to retrieve the data
     * @return the data associated with the identifier
     */
    @Override
    public ConfigJSON getData(String identifier) {
        return new Gson().fromJson(configRepository.getValue(identifier), ConfigJSON.class);
    }

    /**
     * Writes data to the ConfigRepository based on the provided key and value.
     *
     * @param key   the key used to write the data
     * @param value the value to be written
     */
    @Override
    public void writeData(String key, ConfigJSON value) {
        configRepository.saveValue(key, value.toJSON());
    }


}
