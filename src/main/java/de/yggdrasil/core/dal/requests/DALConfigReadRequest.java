package de.yggdrasil.core.dal.requests;

import de.yggdrasil.core.dal.data.datasources.ConfigDB;

public class DALConfigReadRequest<String> implements DALReadRequest<ConfigDB>{

    private final String configKey;

    public DALConfigReadRequest(String configKey){
        this.configKey = configKey;
    }

    @Override
    public java.lang.String getIdentifier() {
        return (java.lang.String) this.configKey;
    }
}
