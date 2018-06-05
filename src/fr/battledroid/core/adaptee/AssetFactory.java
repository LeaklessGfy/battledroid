package fr.battledroid.core.adaptee;

import fr.battledroid.core.map.Biome;
import fr.battledroid.core.player.Player;

public interface AssetFactory {
    Asset getBiome(Biome biome);
    Asset getRandomObstacle(Biome biome, double d);
    Asset getPlayer(Class<? extends Player> clazz);
}
