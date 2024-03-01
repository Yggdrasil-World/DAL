package de.yggdrasil.core.dal.data.datasources.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.bytemc.evelon.repository.annotations.Entity;
import net.bytemc.evelon.repository.annotations.PrimaryKey;

import java.util.UUID;

/**
 * The {@code PlayerData} class represents the data of a player.
 */
@Entity(name = "playerdata")
@AllArgsConstructor
@Getter
@Setter
public class PlayerData {

    @PrimaryKey
    UUID playerID;
    String locale;
    int playerLevel;

}
