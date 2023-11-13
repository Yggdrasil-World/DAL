package de.yggdrasil.core.dal.requests;

import de.yggdrasil.core.dal.data.DALWriteScope;
import de.yggdrasil.core.dal.data.DataSource;

import java.lang.reflect.ParameterizedType;

public interface DALWriteRequest<T extends DataSource,V> extends DALRequest{

    DALWriteScope getScope();

    String getKey();

    V getData();

    default Class<T> getDatasourceClass() {
        return (Class<T>) ((ParameterizedType) getClass().getGenericInterfaces()[0])
                .getActualTypeArguments()[0];
    }

}
