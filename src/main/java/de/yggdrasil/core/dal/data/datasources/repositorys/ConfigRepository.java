package de.yggdrasil.core.dal.data.datasources.repositorys;

import de.yggdrasil.core.dal.data.datasources.models.ConfigEntry;
import net.bytemc.evelon.repository.Repository;
import net.bytemc.evelon.repository.annotations.Entity;
import net.bytemc.evelon.repository.filters.MatchFilter;

@Entity(name = "config")
public class ConfigRepository {

    Repository<ConfigEntry> configEntryRepository = Repository.create(ConfigEntry.class);

    public String getValue(String key){
        return this.configEntryRepository.query().filter(new MatchFilter("key",key))
                .database().findAll().get(0).getValue();
    }

    public void saveValue(String key, String value){
        this.configEntryRepository.query().create(new ConfigEntry(key, value));
    }

}
