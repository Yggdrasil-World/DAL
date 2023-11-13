package de.yggdrasil.core.dal.requests;

import de.yggdrasil.core.dal.data.DataSource;
import de.yggdrasil.core.dal.data.datasources.ConfigDB;

public class DALConfigReadRequest<String> implements DALReadRequest{

    private final String configKey;

    public DALConfigReadRequest(String configKey){
        this.configKey = configKey;
    }

    @Override
    public Class<? extends DataSource> getDatasource() {
        return ConfigDB.class;
    }

    @Override
    public java.lang.String getIdentifier() {
        return (java.lang.String) this.configKey;
    }


    @Override
    public Class getTargetType() {
        return java.lang.String.class;
    }
}
