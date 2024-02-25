package de.yggdrasil.core.dal.requests.implementations;

import de.yggdrasil.core.dal.data.datasources.ConfigDB;
import de.yggdrasil.core.dal.requests.DALReadRequest;

/**
 * The {@code ConfigReadRequest} class represents a read request to the {@code ConfigDB} data source.
 * It implements the {@code DALReadRequest} interface with {@code ConfigDB} as the data source type.
 * It provides the configKey as the identifier for the read request.
 */
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
