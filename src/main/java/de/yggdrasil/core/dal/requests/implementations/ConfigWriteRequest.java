package de.yggdrasil.core.dal.requests.implementations;

import de.yggdrasil.core.dal.data.datasources.ConfigDB;
import de.yggdrasil.core.dal.requests.DALWriteRequest;

/**
 * The `ConfigWriteRequest` class represents a write request to the `ConfigDB` data source.
 * It implements the `DALWriteRequest` interface with `ConfigDB` as the data source type and `String` as the data type being written.
 *
 * @param key The key for the configuration data being written.
 * @param value The value of the configuration data being written.
 */
public class ConfigWriteRequest implements DALWriteRequest<ConfigDB,String> {

    private final String key;
    private final String value;

    public ConfigWriteRequest(String key, String value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public String getData() {
        return this.value;
    }
}
