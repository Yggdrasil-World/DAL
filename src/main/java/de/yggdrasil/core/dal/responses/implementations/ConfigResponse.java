package de.yggdrasil.core.dal.responses.implementations;

import de.yggdrasil.core.dal.data.datasources.models.ConfigJSON;
import de.yggdrasil.core.dal.responses.DALResponse;

/**
 * The ConfigResponse class contains the value of a config Entry in the {@code ConfigDataSource}.
 */
public record ConfigResponse(ConfigJSON data) implements DALResponse<ConfigJSON> { }
