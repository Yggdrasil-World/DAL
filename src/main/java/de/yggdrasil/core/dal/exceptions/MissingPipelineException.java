package de.yggdrasil.core.dal.exceptions;

import de.yggdrasil.core.dal.requests.DALRequest;

/**
 * This class represents an exception that is thrown when there is no pipeline available for a request.
 */
public class MissingPipelineException extends Exception{

    public MissingPipelineException(DALRequest request){
        super(request.getClass().getName());
    }

}
