package fr.battledroid.core.adaptee;

import fr.battledroid.core.artifact.Artifact;
import fr.battledroid.core.map.Biome;
import fr.battledroid.core.particle.Particle;
import fr.battledroid.core.player.Player;
import fr.battledroid.core.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class AssetFactory {
    private final Random rand = new Random();
    private final HashMap<Biome, AssetInfo> backgrounds = new HashMap<>();
    private final HashMap<Biome, ArrayList<AssetInfo>> overlays = new HashMap<>();
    private final HashMap<Class<? extends Player>, AssetInfo> players = new HashMap<>();
    private final HashMap<Class<? extends Artifact>, AssetInfo> artifacts = new HashMap<>();
    private final HashMap<Class<? extends Particle>, AssetInfo> particles = new HashMap<>();
    private final SpriteFactory spriteFactory;

    public AssetFactory(SpriteFactory spriteFactory) {
        this.spriteFactory = Utils.requireNonNull(spriteFactory);
    }

    public void registerBiome(Biome biome, AssetInfo info) {
        backgrounds.put(Utils.requireNonNull(biome), Utils.requireNonNull(info));
    }

    public void registerObstacle(Biome biome, AssetInfo info) {
        Utils.requireNonNull(biome);
        overlays.computeIfAbsent(biome, k -> new ArrayList<>()).add(Utils.requireNonNull(info));
    }

    public void registerPlayer(Class<? extends Player> clazz, AssetInfo info) {
        players.put(Utils.requireNonNull(clazz), Utils.requireNonNull(info));
    }

    public void registerArtifact(Class<? extends Artifact> clazz, AssetInfo info) {
        artifacts.put(Utils.requireNonNull(clazz), Utils.requireNonNull(info));
    }

    public void registerParticle(Class<? extends Particle> clazz, AssetInfo info) {
        particles.put(Utils.requireNonNull(clazz), Utils.requireNonNull(info));
    }

    public Asset getBiome(Biome biome) {
        return findMap(biome, backgrounds);
    }

    public Asset getRandomObstacle(Biome biome) {
        return findOverlay(biome);
    }

    public Asset getPlayer(Class<? extends Player> clazz) {
        return findMap(clazz, players);
    }

    public Asset getArtifact(Class<? extends Artifact> clazz) {
        return findMap(clazz, artifacts);
    }

    public Asset getParticle(Class<? extends Particle> clazz) {
        return findMap(clazz, particles);
    }

    private Asset findOverlay(Biome biome) {
        ArrayList<AssetInfo> infos = overlays.get(biome);
        if (infos == null) {
            throw new IllegalStateException("Unknown overlay for biome " + biome);
        }
        int i = rand.nextInt(infos.size());
        return find(infos.get(i % infos.size()));
    }

    private Asset findMap(Object o, HashMap<?, AssetInfo> map) {
        AssetInfo info = map.get(o);
        if (info == null) {
            throw new IllegalStateException("Unknown asset " + o);
        }
        return find(info);
    }

    private Asset find(AssetInfo info) {
        return spriteFactory.get(info);
    }
}
