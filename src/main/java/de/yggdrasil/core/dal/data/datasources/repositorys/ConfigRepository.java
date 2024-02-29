package de.yggdrasil.core.dal.data.datasources.repositorys;

import de.yggdrasil.core.dal.data.datasources.models.ConfigEntry;
import net.bytemc.evelon.repository.Repository;
import net.bytemc.evelon.repository.annotations.Entity;
import net.bytemc.evelon.repository.filters.MatchFilter;

/**
 * The ConfigRepository class provides methods for accessing and manipulating configuration data stored in a Repository.
 */
public class ConfigRepository {

    Repository<ConfigEntry> configEntryRepository = Repository.create(ConfigEntry.class);

    /**
     * Retrieves the value associated with the given key from the config repository.
     *
     * @param key the key used to retrieve the value
     * @return the value associated with the key
     */
    public String getValue(String key){
        return this.configEntryRepository.query().filter(new MatchFilter("key",key))
                .database().findAll().get(0).getValue();
    }

    /**
     * Saves the value associated with the given key in the config repository.
     *
     * @param key the key used to save the value
     * @param value the value to be saved
     */
    public void saveValue(String key, String value){
        this.configEntryRepository.query().create(new ConfigEntry(key, value));
    }

}
