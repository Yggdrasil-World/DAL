package de.yggdrasil.core.dal.strings.logging;

/**
 * DALLogger is an interface that provides constants for logging messages related to the DAL (Data Access Layer) operations.
 * It contains the following constants:
 * - DAL_SAVE: A log message indicating a DAL save request, with a placeholder for the request class name.
 * - DAL_READ: A log message indicating a DAL load request, with a placeholder for the request class name.
 * - DAL_EXCEPTION_PIPELINE: A log message indicating that no pipeline is configured for a request, with a placeholder for the request class name.
 *
 * Usage Example:
 * // Saving data using a write request
 * DALWriteRequest saveRequest = new DALWriteRequestImpl();
 * DALLogger logger = new DALLoggerImpl();
 * logger.info(DALLogger.DAL_SAVE.formatted(saveRequest.getClass().getName()));
 *
 * // Reading data using a read request
 * DALReadRequest readRequest = new DALReadRequestImpl();
 * logger.info(DALLogger.DAL_READ.formatted(readRequest.getClass().getName()));
 *
 * // Handling a missing pipeline exception
 * logger.error(DALLogger.DAL_EXCEPTION_PIPELINE.formatted(request.getClass().getName()));
 */
public interface DALLogger {

    String DAL_SAVE = "DAL Save Request: %s";

    String DAL_READ = "DAL Load Request: %s";

    String DAL_EXCEPTION_PIPELINE = "No Pipeline for Request %s configured.";

}
