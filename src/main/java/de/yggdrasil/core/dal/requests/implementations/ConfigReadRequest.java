package de.yggdrasil.core.dal.requests.implementations;

import de.yggdrasil.core.dal.data.datasources.ConfigDataSource;
import de.yggdrasil.core.dal.requests.DALReadRequest;

/**
 * The {@code ConfigReadRequest} class is used to read data from the {@code ConfigDataSource}.
 * It provides the configKey as the identifier for the read request.
 */
public class ConfigReadRequest implements DALReadRequest<ConfigDataSource> {

    private final String configKey;

    public ConfigReadRequest(String configKey){
        this.configKey = configKey;
    }

    @Override
    public String getIdentifier() {
        return this.configKey;
    }
}
