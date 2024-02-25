package de.yggdrasil.core.dal.responses.implementations;

import de.yggdrasil.core.dal.responses.DALResponse;

/**
 * The ConfigResponse class represents a response from the DAL (Data Access Layer) that contains configuration data of type String.
 * It implements the DALResponse interface with String as the type parameter.
 */
public record ConfigResponse(String data) implements DALResponse<String> { }
