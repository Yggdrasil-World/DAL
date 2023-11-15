package de.yggdrasil.core.dal.requests.implementations;

import de.yggdrasil.core.dal.data.datasources.ConfigDB;
import de.yggdrasil.core.dal.requests.DALReadRequest;

public class ConfigReadRequest implements DALReadRequest<ConfigDB> {

    private final String configKey;

    public ConfigReadRequest(String configKey){
        this.configKey = configKey;
    }

    @Override
    public String getIdentifier() {
        return this.configKey;
    }
}
