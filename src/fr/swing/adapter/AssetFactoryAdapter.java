package fr.swing.adapter;

import fr.battledroid.core.Settings;
import fr.battledroid.core.adaptee.Asset;
import fr.battledroid.core.adaptee.AssetFactory;
import fr.battledroid.core.map.Biome;
import fr.battledroid.core.player.Droid;
import fr.battledroid.core.player.Player;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Objects;

public final class AssetFactoryAdapter implements AssetFactory {
    private final HashMap<Biome, Path> backgrounds;
    private final HashMap<Biome, Path> overlays;
    private final HashMap<Class<? extends Player>, Path> players;
    private final SpriteFactory spriteFactory;

    private AssetFactoryAdapter(
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

    public static AssetFactoryAdapter create(SpriteFactory spriteFactory) {
        String r = Settings.instance().resources;
        HashMap<Biome, Path> backgrounds = new HashMap<>();
        backgrounds.put(Biome.SNOW, Paths.get(r + "/tiles/snow.png"));
        backgrounds.put(Biome.GRASS, Paths.get(r + "/tiles/grass.png"));
        backgrounds.put(Biome.DARK_GRASS, Paths.get(r + "/tiles/dark_grass.png"));
        backgrounds.put(Biome.ROCK, Paths.get(r + "/tiles/rock.png"));
        backgrounds.put(Biome.LIGHT_ROCK, Paths.get(r + "/tiles/light_rock.png"));
        backgrounds.put(Biome.SAND, Paths.get(r + "/tiles/sand.png"));

        HashMap<Biome, Path> overlays = new HashMap<>();

        HashMap<Class<? extends Player>, Path> players = new HashMap<>();
        players.put(Droid.class, Paths.get(r + "/players/droid.png"));

        return new AssetFactoryAdapter(backgrounds, overlays, players, spriteFactory);
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
