package de.yggdrasil.core.dal.requests;

import de.yggdrasil.core.dal.data.DataSource;
import java.lang.reflect.ParameterizedType;

public interface DALReadRequest<T extends DataSource> extends DALRequest{

    String getIdentifier();

    default Class<T> getDatasourceClass() {
        return (Class<T>) ((ParameterizedType) getClass().getGenericInterfaces()[0])
                .getActualTypeArguments()[0];
    }

}
