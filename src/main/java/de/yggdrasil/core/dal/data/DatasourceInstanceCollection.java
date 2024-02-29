package de.yggdrasil.core.dal.data;
import de.yggdrasil.core.dal.strings.logging.DatasourceLibraryLoggerMessages;
import de.yggdrasil.core.dal.utils.ClassCollector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;

/**
 * The DatasourceInstanceCollection class represents a library of data sources.
 * It provides methods for adding and retrieving data sources.
 */
public class DatasourceInstanceCollection {

    private Logger logger = LogManager.getLogger(DatasourceInstanceCollection.class);

    private final HashMap<Class<? extends DataSource>, DataSource> dataSources = new HashMap<>();
    private final static DatasourceInstanceCollection instance = new DatasourceInstanceCollection();

    {
        setup();
    }

    /**
     * Sets up the DatasourceInstanceCollection by adding a collection of data sources.
     *
     * @see #addDatasourceCollection(DatasourceCollector)
     */
    private void setup(){
        this.addDatasourceCollection(new ClassCollector());
    }

    /**
     * Adds a collection of data sources to the DatasourceInstanceCollection.
     * The data sources are collected using the provided DatasourceCollector.
     *
     * @param collector the DatasourceCollector used to collect the data sources
     */
    public void addDatasourceCollection(DatasourceCollector collector){
        int count = 0;
        for (Class<? extends DataSource> datasourceClass:
                collector.collectDatasources()) {
            this.addDatasource(datasourceClass);
            count++;
        }
        logger.info(DatasourceLibraryLoggerMessages.ADD_DATASOURCE_COLLECTION.formatted(count, dataSources.size()));
    }

    /**
     * Adds a data source to the library.
     *
     * @param datasourceClass the class of the data source to add
     */
    private void addDatasource(Class<? extends DataSource> datasourceClass){
        try {
            DataSource datasourceInstance = datasourceClass.newInstance();
            this.dataSources.put(datasourceClass, datasourceInstance);
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves a data source of the specified type from the library.
     *
     * @param datasourceClass the class representing the desired data source
     * @param <T> the type of the data source
     * @return the data source of the specified type
     */
    public <T extends DataSource> T getDatasource(Class<T> datasourceClass){
        return (T) dataSources.get(datasourceClass);
    }

    /**
     * Returns the instance of the DatasourceInstanceCollection class.
     *
     * @return the instance of the DatasourceInstanceCollection class
     */
    public static DatasourceInstanceCollection get() {
        return instance;
    }

}
