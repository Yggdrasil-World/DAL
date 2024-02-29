package de.yggdrasil.core.dal.data;

import de.yggdrasil.core.dal.requests.DALReadRequest;
import de.yggdrasil.core.dal.requests.DALWriteRequest;

/**
 * The DataSource interface represents a data source that provides methods for retrieving and writing data.
 *
 * @param <T> the type of data stored in the data source
 */
@DALDatasource
public interface DataSource<T> {

    T getData(String identifier);

    /**
     * Retrieves data from a data source using an identifier and a read request.
     *
     * @param identifier the identifier used to retrieve data from the data source
     * @param readRequest the read request to apply when retrieving the data (optional to use it)
     * @return the retrieved data
     */
    default T getData(String identifier, DALReadRequest readRequest){
        return getData(identifier);
    }

    void writeData(String key, T value);

    /**
     * Writes data to a data source using an identifier, a value, and a write request.
     * This method is the default implementation for the writeData method in the DataSource interface.
     *
     * @param identifier the identifier used to write data to the data source
     * @param value the value to be written to the data source
     * @param writeRequest the write request to apply when writing the data (optional to use it)
     */
    default void writeData(String identifier, T value, DALWriteRequest writeRequest){
        writeData(identifier, value);
    }

}
