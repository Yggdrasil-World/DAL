package de.yggdrasil.core.dal.data.datasources.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.bytemc.evelon.repository.annotations.Entity;
import net.bytemc.evelon.repository.annotations.PrimaryKey;

/**
 * The ConfigEntry class represents a configuration entry stored in a repository.
 */
@Entity(name = "config")
@AllArgsConstructor
@Getter
public class ConfigEntry {

    @PrimaryKey
    String key;
    String value;

}
