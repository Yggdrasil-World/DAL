package de.yggdrasil.core.dal.adapter;

import de.yggdrasil.core.dal.exceptions.DuplicateAdapterForClassException;
import de.yggdrasil.core.dal.strings.logging.AdapterLibraryLogger;
import de.yggdrasil.core.dal.utils.ClassCollector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.util.HashMap;

public class AdapterLibrary {

    private Logger logger = LogManager.getLogger(AdapterLibrary.class);

    private final HashMap<Class, Adapter> adapters = new HashMap<>();

    {
        setup();
    }

    private void setup(){
        this.addAdapterCollection(new ClassCollector());
    }

    public void addAdapterCollection(AdapterCollector collector){
        int count = 0;
        for (Class<? extends Adapter> adapterClass:
             collector.collectAdapters()) {
            this.addAdapter(adapterClass);
            count++;
        }
        this.logger.info(AdapterLibraryLogger.ADD_ADAPTER_COLLECTION.formatted(count, adapters.size()));
    }

    private void addAdapter(Class<? extends Adapter> adapterClass){
        try {
            Adapter adapterInstance = adapterClass.newInstance();
            Class adapterType = adapterInstance.getAdapterType();
            if (this.adapters.containsKey(adapterType)) throw new DuplicateAdapterForClassException();
            this.adapters.put(adapterType, adapterInstance);
        } catch (InstantiationException | IllegalAccessException | DuplicateAdapterForClassException e) {
            e.printStackTrace();
        }
    }

    public Adapter getAdapterForClass(Class c){
        return this.adapters.get(c);
    }

}
