package de.yggdrasil.core.dal.data;
import de.yggdrasil.core.dal.strings.logging.DatasourceLibraryLogger;
import de.yggdrasil.core.dal.utils.ClassCollector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;

public class DatasourceLibrary {

    private Logger logger = LogManager.getLogger(DatasourceLibrary.class);

    private final HashMap<Class<? extends DataSource>, DataSource> dataSources = new HashMap<>();
    private final static DatasourceLibrary instance = new DatasourceLibrary();

    {
        setup();
    }

    private void setup(){
        this.addDatasourceCollection(new ClassCollector());
    }

    public void addDatasourceCollection(DatasourceCollector collector){
        int count = 0;
        for (Class<? extends DataSource> datasourceClass:
                collector.collectDatasources()) {
            this.addDatasource(datasourceClass);
            count++;
        }
        logger.info(DatasourceLibraryLogger.ADD_DATASOURCE_COLLECTION.formatted(count, dataSources.size()));
    }

    private void addDatasource(Class<? extends DataSource> datasourceClass){
        try {
            DataSource datasourceInstance = datasourceClass.newInstance();
            this.dataSources.put(datasourceClass, datasourceInstance);
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public <T extends DataSource> T getDatasource(Class<T> datasourceClass){
        return (T) dataSources.get(datasourceClass);
    }

    public static DatasourceLibrary get() {
        return instance;
    }

}
