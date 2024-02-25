package de.yggdrasil.core.dal.data.datasources;

import de.yggdrasil.core.dal.data.DataSource;
import de.yggdrasil.core.dal.data.datasources.repositorys.ConfigRepository;

/**
 * The {@code ConfigDB} class implements the {@code DataSource} interface with a type parameter of {@code String}.
 * It provides methods to retrieve and write configuration data from a {@code ConfigRepository}.
 *
 * @param <String> the type of data stored in the data source
 */
public class ConfigDB implements DataSource<String> {


    private final ConfigRepository configRepository = new ConfigRepository();

    /**
     * Retrieves the data associated with the given identifier from the ConfigRepository.
     *
     * @param identifier the identifier used to retrieve the data
     * @return the data associated with the identifier
     */
    @Override
    public String getData(String identifier) {
        return configRepository.getValue(identifier);
    }

    /**
     * Writes data to the ConfigRepository based on the provided key and value.
     *
     * @param key   the key used to write the data
     * @param value the value to be written
     */
    @Override
    public void writeData(String key, String value) {
        configRepository.saveValue(key, value);
    }


}
