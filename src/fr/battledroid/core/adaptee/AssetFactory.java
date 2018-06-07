package fr.battledroid.core.adaptee;

import fr.battledroid.core.map.Biome;
import fr.battledroid.core.player.Player;

import java.nio.file.Path;

public interface AssetFactory {
    void registerBiome(Biome biome, Path path);
    void registerObstacle(Biome biome, Path path);
    void registerPlayer(Class<? extends Player> clazz, Path path);

    Asset getBiome(Biome biome);
    Asset getRandomObstacle(Biome biome, double d);
    Asset getPlayer(Class<? extends Player> clazz);
}
