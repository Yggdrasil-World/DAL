package de.yggdrasil.core.dal.data;

@DALDatasource
public interface DataSource<T> {

    T getData(String identifier);

    void writeData(String key, T value);

}
