package de.yggdrasil.core.dal.exceptions;

import de.yggdrasil.core.dal.requests.DALRequest;

public class MissingPipelineException extends Exception{

    public MissingPipelineException(DALRequest request){
        super(request.getClass().getName());
    }

}
