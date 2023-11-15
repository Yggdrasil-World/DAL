package de.yggdrasil.core.dal.data.datasources;

import de.yggdrasil.core.dal.data.DataSource;
import de.yggdrasil.core.dal.data.datasources.repositorys.ConfigRepository;

public class ConfigDB implements DataSource<String> {


    private final ConfigRepository configRepository = new ConfigRepository();

    @Override
    public String getData(String identifier) {
        return configRepository.getValue(identifier);
    }

    @Override
    public void writeData(String key, String value) {
        configRepository.saveValue(key, value);
    }


}
