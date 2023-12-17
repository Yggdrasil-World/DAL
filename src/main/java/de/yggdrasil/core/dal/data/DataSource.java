package de.yggdrasil.core.dal.data;

import de.yggdrasil.core.dal.requests.DALReadRequest;
import de.yggdrasil.core.dal.requests.DALWriteRequest;

@DALDatasource
public interface DataSource<T> {

    T getData(String identifier);

    default T getData(String identifier, DALReadRequest readRequest){
        return getData(identifier);
    }

    void writeData(String key, T value);

    default void writeData(String identifier, T value, DALWriteRequest writeRequest){
        writeData(identifier, value);
    }

}
