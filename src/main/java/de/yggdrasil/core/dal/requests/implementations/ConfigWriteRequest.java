package de.yggdrasil.core.dal.requests.implementations;

import de.yggdrasil.core.dal.data.datasources.ConfigDB;
import de.yggdrasil.core.dal.requests.DALWriteRequest;

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
