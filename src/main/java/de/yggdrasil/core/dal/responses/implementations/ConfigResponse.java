package de.yggdrasil.core.dal.responses.implementations;

import de.yggdrasil.core.dal.responses.DALResponse;

/**
 * The ConfigResponse class contains the value of a config Entry in the {@code ConfigDataSource}.
 */
public record ConfigResponse(String data) implements DALResponse<String> { }
