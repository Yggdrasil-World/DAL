package de.yggdrasil.core.dal.adapter;

import de.yggdrasil.core.dal.exceptions.DuplicateAdapterForClassException;
import de.yggdrasil.core.dal.strings.logging.AdapterLibraryLoggerMessages;
import de.yggdrasil.core.dal.utils.ClassCollector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.util.HashMap;

/**
 * The AdapterLibrary class is responsible for managing a collection of adapters.
 * It allows for adding and retrieving adapters based on their corresponding classes.
 */
public class AdapterLibrary {

    private Logger logger = LogManager.getLogger(AdapterLibrary.class);

    private final HashMap<Class, Adapter> adapters = new HashMap<>();

    {
        setup();
    }

    /**
     * Sets up the AdapterLibrary by adding a collection of adapters.
     * Calls the addAdapterCollection method with a new instance of ClassCollector.
     */
    private void setup(){
        this.addAdapterCollection(new ClassCollector());
    }

    /**
     * Adds a collection of adapters to the AdapterLibrary.
     *
     * @param collector the AdapterCollector instance containing the adapters to be added
     */
    public void addAdapterCollection(AdapterCollector collector){
        int count = 0;
        for (Class<? extends Adapter> adapterClass:
             collector.collectAdapters()) {
            this.addAdapter(adapterClass);
            count++;
        }
        this.logger.info(AdapterLibraryLoggerMessages.ADD_ADAPTER_COLLECTION.formatted(count, adapters.size()));
    }

    /**
     * Adds an adapter to the AdapterLibrary.
     *
     * @param adapterClass the class of the adapter to be added
     * @throws DuplicateAdapterForClassException if an adapter for the given class already exists in the AdapterLibrary
     */
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

    /**
     * Retrieves the adapter for the given class from the AdapterLibrary.
     *
     * @param c the class for which to retrieve the adapter
     * @return the adapter corresponding to the given class, or null if no adapter is found
     */
    public Adapter getAdapterForClass(Class c){
        return this.adapters.get(c);
    }

}
