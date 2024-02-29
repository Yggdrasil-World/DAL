package de.yggdrasil.core.dal.strings.logging;

/**
 * The DALLogger interface provides constants for logging messages related to the DAL operations.
 */
public interface DALLogger {

    /**
     * Example: DAL.get().save(new ConfigWriteRequest("ExampleKey", "Value")) -> "DAL Save Request: ConfigWriteRequest"
     */
    String DAL_SAVE = "DAL Save Request: %s";

    /**
     * Example: DAL.get().read(new ConfigReadRequest("ExampleKey")) -> "DAL Read Request: ConfigReadRequest"
     */
    String DAL_READ = "DAL Read Request: %s";

    /**
     * Example
     * If there is not a pipeline set up for a "ConfigReadRequest",
     * The output of this string would be: "No Pipeline for Request ConfigReadRequest configured."
     */
    String DAL_EXCEPTION_PIPELINE = "No Pipeline for Request %s configured.";

}
