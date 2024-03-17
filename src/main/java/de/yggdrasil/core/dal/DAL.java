package de.yggdrasil.core.dal;

import de.yggdrasil.core.dal.exceptions.MissingPipelineException;
import de.yggdrasil.core.dal.pipeline.DALPipelineProzessor;
import de.yggdrasil.core.dal.requests.DALReadRequest;
import de.yggdrasil.core.dal.requests.DALWriteRequest;
import de.yggdrasil.core.dal.responses.DALResponse;
import de.yggdrasil.core.dal.strings.logging.DALLogger;
import de.yggdrasil.core.dal.strings.logging.DatabaseConnectionLoggerMessages;
import io.github.cdimascio.dotenv.Dotenv;
import net.bytemc.evelon.DatabaseProtocol;
import net.bytemc.evelon.Evelon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The DAL (Data Access Layer) class provides methods for saving and reading data using a request system.
 */
public class DAL {

    private Logger logger = LoggerFactory.getLogger(DAL.class);

    private final static DAL instance = new DAL();
    private final DALPipelineProzessor pipelineProzessor = new DALPipelineProzessor();

    {
        setupEvelonDBCredentials();
    }

    /**
     * Saves data using a write request.
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
     * Sets up the credentials for accessing the database used by the Evelon ORM module.
     * This method reads the necessary information from the environment variables and configures the Evelon library with the provided credentials.
     */
    private void setupEvelonDBCredentials() {
        Dotenv dotenv = Dotenv.configure().load();
        final String host = dotenv.get("ORM_HOST");
        final String port = dotenv.get("ORM_PORT");
        final String databaseName = dotenv.get("ORM_DB");
        final String username = dotenv.get("ORM_USER");
        final String password = dotenv.get("ORM_PW");
        Evelon.setCradinates(
                DatabaseProtocol.POSTGRESQL, //type of database
                host, //hostname
                password, //password
                username, //username
                databaseName, //database
                Integer.parseInt(port) //port
        );
        logger.info(DatabaseConnectionLoggerMessages.DATABASE_CONNECTION_DATA.formatted(host, port, databaseName, username));
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
