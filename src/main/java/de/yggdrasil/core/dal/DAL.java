package de.yggdrasil.core.dal;

import de.yggdrasil.core.dal.exceptions.MissingPipelineException;
import de.yggdrasil.core.dal.pipeline.DALPipelineProzessor;
import de.yggdrasil.core.dal.requests.DALReadRequest;
import de.yggdrasil.core.dal.requests.DALWriteRequest;
import de.yggdrasil.core.dal.responses.DALResponse;
import de.yggdrasil.core.dal.strings.logging.DALLogger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The DAL (Data Access Layer) class provides methods for saving and reading data using a request system.
 */
public class DAL {

    private Logger logger = LogManager.getLogger(DAL.class);

    private final static DAL instance = new DAL();
    private final DALPipelineProzessor pipelineProzessor = new DALPipelineProzessor();

    /**
     * Saves the data using a write request.
     *
     * @param saveRequest the write request containing the data to be saved
     */
    public void save(DALWriteRequest saveRequest) {
        try {
            logger.info(DALLogger.DAL_SAVE.formatted(saveRequest.getClass().getName()));
            this.pipelineProzessor.writeData(saveRequest);
        } catch (MissingPipelineException e) {
            logger.error(DALLogger.DAL_EXCEPTION_PIPELINE.formatted(saveRequest.getClass().getName()));
            e.printStackTrace();
        }
    }

    /**
     * Reads data from the data source using the specified read request.
     *
     * @param <T>           the type of the read request
     * @param <V>           the type of the response
     * @param readRequest   the read request
     * @return the response containing the data read from the data source
     */
    public <T extends DALReadRequest, V extends DALResponse> V read(T readRequest) {
        try {
            logger.info(DALLogger.DAL_READ.formatted(readRequest.getClass().getName()));
            return this.pipelineProzessor.readData(readRequest);
        } catch (MissingPipelineException e) {
            logger.error(DALLogger.DAL_EXCEPTION_PIPELINE.formatted(readRequest.getClass().getName()));
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Retrieves the instance of the DAL (Data Access Layer) class.
     *
     * @return the instance of the DAL class
     */
    public static DAL get(){
        return instance;
    }

}
