package fr.battledroid.core.adapter;

import fr.battledroid.core.adaptee.Asset;
import fr.battledroid.core.adaptee.AssetFactory;
import fr.battledroid.core.adaptee.SpriteFactory;
import fr.battledroid.core.map.Biome;
import fr.battledroid.core.player.Player;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Objects;

public class DefaultAssetFactory implements AssetFactory {
    private final HashMap<Biome, Path> backgrounds;
    private final HashMap<Biome, Path> overlays;
    private final HashMap<Class<? extends Player>, Path> players;
    private final SpriteFactory spriteFactory;


    private DefaultAssetFactory(
            HashMap<Biome, Path> backgrounds,
            HashMap<Biome, Path> overlays,
            HashMap<Class<? extends Player>, Path> players,
            SpriteFactory spriteFactory
    ) {
        this.backgrounds = Objects.requireNonNull(backgrounds);
        this.overlays = Objects.requireNonNull(overlays);
        this.players = Objects.requireNonNull(players);
        this.spriteFactory = Objects.requireNonNull(spriteFactory);
    }

    public static AssetFactory create(SpriteFactory spriteFactory) {
        HashMap<Biome, Path> backgrounds = new HashMap<>();
        HashMap<Biome, Path> overlays = new HashMap<>();
        HashMap<Class<? extends Player>, Path> players = new HashMap<>();

        return new DefaultAssetFactory(backgrounds, overlays, players, spriteFactory);
    }

    @Override
    public void registerBiome(Biome biome, Path path) {
        backgrounds.put(Objects.requireNonNull(biome), Objects.requireNonNull(path));
    }

    @Override
    public void registerObstacle(Biome biome, Path path) {
        overlays.put(Objects.requireNonNull(biome), Objects.requireNonNull(path));
    }

    @Override
    public void registerPlayer(Class<? extends Player> clazz, Path path) {
        players.put(Objects.requireNonNull(clazz), Objects.requireNonNull(path));
    }

    @Override
    public Asset getBiome(Biome biome) {
        return findBackground(biome);
    }

    @Override
    public Asset getRandomObstacle(Biome biome, double d) {
        return null;
    }

    @Override
    public Asset getPlayer(Class<? extends Player> clazz) {
        return findPlayer(clazz);
    }

    private Asset findBackground(Biome biome) {
        Path path = backgrounds.get(biome);
        if (path == null) {
            throw new IllegalStateException("Unknown background for biome " + biome);
        }
        return find(path);
    }

    private Asset findOverlay(Biome biome) {
        Path path = overlays.get(biome);
        if (path == null) {
            throw new IllegalStateException("Unknown overlay for biome " + biome);
        }
        return find(path);
    }

    private Asset findPlayer(Class<? extends Player> clazz) {
        Path path = players.get(clazz);
        if (path == null) {
            throw new IllegalStateException("Unknown player " + clazz);
        }
        return find(path);
    }

    private Asset find(Path path) {
        return spriteFactory.get(path);
    }
}
