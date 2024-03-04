package de.yggdrasil.core.dal.requests.implementations;

import de.yggdrasil.core.dal.data.datasources.implementations.ConfigDataSource;
import de.yggdrasil.core.dal.data.datasources.models.ConfigJSON;
import de.yggdrasil.core.dal.requests.DALWriteRequest;

/**
 * The `ConfigWriteRequest` class is used to write data to the {@code ConfigDataSource}.
 *
 * @param key The key for the configuration data being written.
 * @param value The value of the configuration data being written.
 */
public class ConfigWriteRequest implements DALWriteRequest<ConfigDataSource, ConfigJSON> {

    private final String key;
    private final ConfigJSON value;

    public ConfigWriteRequest(String key, ConfigJSON value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public ConfigJSON getData() {
        return this.value;
    }
}
