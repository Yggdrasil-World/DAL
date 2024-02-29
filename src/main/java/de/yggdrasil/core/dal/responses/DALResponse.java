package de.yggdrasil.core.dal.responses;

/**
 * The DALResponse interface represents a response from the DAL containing data of type T.
 *
 * @param <T> the type of the data in the response
 */
public interface DALResponse<T> {

    T data();

}
