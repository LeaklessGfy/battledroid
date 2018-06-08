package fr.battledroid.core.adaptee;

import fr.battledroid.core.artifact.Artifact;
import fr.battledroid.core.map.Biome;
import fr.battledroid.core.particle.Particle;
import fr.battledroid.core.player.Player;
import fr.battledroid.core.utils.Utils;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class AssetFactory {
    private final Random rand = new Random();
    private final HashMap<Biome, Path> backgrounds = new HashMap<>();
    private final HashMap<Biome, ArrayList<Path>> overlays = new HashMap<>();
    private final HashMap<Class<? extends Player>, Path> players = new HashMap<>();
    private final HashMap<Class<? extends Artifact>, Path> artifacts = new HashMap<>();
    private final HashMap<Class<? extends Particle>, Path> particles = new HashMap<>();
    private final SpriteFactory spriteFactory;

    public AssetFactory(SpriteFactory spriteFactory) {
        this.spriteFactory = Utils.requireNonNull(spriteFactory);
    }

    public void registerBiome(Biome biome, Path path) {
        backgrounds.put(Utils.requireNonNull(biome), Utils.requireNonNull(path));
    }

    public void registerObstacle(Biome biome, Path path) {
        Utils.requireNonNull(biome);
        overlays.computeIfAbsent(biome, k -> new ArrayList<>()).add(Utils.requireNonNull(path));
    }

    public void registerPlayer(Class<? extends Player> clazz, Path path) {
        players.put(Utils.requireNonNull(clazz), Utils.requireNonNull(path));
    }

    public void registerArtifact(Class<? extends Artifact> clazz, Path path) {
        artifacts.put(Utils.requireNonNull(clazz), Utils.requireNonNull(path));
    }

    public void registerParticle(Class<? extends Particle> clazz, Path path) {
        particles.put(Utils.requireNonNull(clazz), Utils.requireNonNull(path));
    }

    public Asset getBiome(Biome biome) {
        return findBackground(biome);
    }

    public Asset getRandomObstacle(Biome biome, double d) {
        return findOverlay(biome);
    }

    public Asset getPlayer(Class<? extends Player> clazz) {
        return findPlayer(clazz);
    }

    public Asset getArtifact(Class<? extends Artifact> clazz) {
        return findArtifact(clazz);
    }

    public Asset getParticle(Class<? extends Particle> clazz) {
        return findParticle(clazz);
    }

    private Asset findBackground(Biome biome) {
        Path path = backgrounds.get(biome);
        if (path == null) {
            throw new IllegalStateException("Unknown background for biome " + biome);
        }
        return find(path);
    }

    private Asset findOverlay(Biome biome) {
        ArrayList<Path> paths = overlays.get(biome);
        if (paths == null) {
            throw new IllegalStateException("Unknown overlay for biome " + biome);
        }
        int i = rand.nextInt(paths.size());
        return find(paths.get(i % paths.size()));
    }

    private Asset findPlayer(Class<? extends Player> clazz) {
        Path path = players.get(clazz);
        if (path == null) {
            throw new IllegalStateException("Unknown player " + clazz);
        }
        return find(path);
    }

    private Asset findArtifact(Class<? extends Artifact> clazz) {
        Path path = artifacts.get(clazz);
        if (path == null) {
            throw new IllegalStateException("Unknown artifact " + clazz);
        }
        return find(path);
    }

    private Asset findParticle(Class<? extends Particle> clazz) {
        Path path = particles.get(clazz);
        if (path == null) {
            throw new IllegalStateException("Unknown particle " + clazz);
        }
        return find(path);
    }

    private Asset find(Path path) {
        return spriteFactory.get(path);
    }
}
