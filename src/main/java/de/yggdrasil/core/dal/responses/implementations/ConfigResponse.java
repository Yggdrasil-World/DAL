package de.yggdrasil.core.dal.responses.implementations;

import de.yggdrasil.core.dal.responses.DALResponse;

public record ConfigResponse(String data) implements DALResponse<String> { }
