package de.yggdrasil.core.dal.strings.logging;

/**
 * The DALPipelineProzessorLoggerMessages interface defines the log messages to be used by the DALPipelineProzessor class.
 */
public interface DALPipelineProzessorLoggerMessages {

    String ADD_PIPELINE_COLLECTION_READ = "Added %d read pipelines to the pipeline processor, total count of served read request types: %d";
    String ADD_PIPELINE_COLLECTION_WRITE = "Added %d write pipelines to the pipeline processor, total count of served write request types: %d";

}
