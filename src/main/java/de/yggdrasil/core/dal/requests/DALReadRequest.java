package de.yggdrasil.core.dal.requests;

import de.yggdrasil.core.dal.data.DataSource;
import de.yggdrasil.core.dal.utils.GenericTypeUtils;

/**
 * This interface represents a read request to a data source.
 *
 * @param <T> the type of the data source
 */
public interface DALReadRequest<T extends DataSource> extends DALRequest{

    String getIdentifier();

    /**
     * Retrieves the class of the data source.
     *
     * @param <T> the type of the data source
     * @return the class of the data source
     */
    default Class<T> getDatasourceClass() {
        return (Class<T>) GenericTypeUtils.getParameterTypeClass(this.getClass());
    }

}
