package fr.slick.bridge;

import fr.battledroid.core.adaptee.AssetFactory;
import fr.battledroid.core.adaptee.AssetInfo;
import fr.battledroid.core.artifact.BombMalus;
import fr.battledroid.core.map.Biome;
import fr.battledroid.core.particle.Laser;
import fr.battledroid.core.player.Droid;
import fr.battledroid.core.player.Monster;

import java.nio.file.Path;
import java.nio.file.Paths;

public class AssetFacade {
    private final static String R = "res/";

    public static void initAsset(AssetFactory factory) {
        initBiome(factory);
        initObstacle(factory);
        initPlayer(factory);
        initArtifact(factory);
        initParticle(factory);
    }

    private static void initBiome(AssetFactory factory) {
        AssetInfo info = new AssetInfo(p("tiles/snow.png"), 0, 0);
        factory.registerBiome(Biome.SNOW, info);

        info = new AssetInfo(p("tiles/grass_bug.png"), 56, 74, 0, 0);
        factory.registerBiome(Biome.GRASS, info);

        info = new AssetInfo(p("tiles/dark_grass.png"), 0, 0);
        factory.registerBiome(Biome.DARK_GRASS, info);

        info = new AssetInfo(p("tiles/rock.png"), 0, 0);
        factory.registerBiome(Biome.ROCK, info);

        info = new AssetInfo(p("tiles/light_rock.png"), 0, 0);
        factory.registerBiome(Biome.LIGHT_ROCK, info);

        info = new AssetInfo(p("tiles/sand.png"), 0, 0);
        factory.registerBiome(Biome.SAND, info);
    }

    private static void initObstacle(AssetFactory factory) {
        AssetInfo info = new AssetInfo(p("overlays/snow_mountain.png"), 0, -21);
        factory.registerObstacle(Biome.SNOW, info);

        info = new AssetInfo(p("overlays/building.png"), 10, 0);
        factory.registerObstacle(Biome.GRASS, info);

        info = new AssetInfo(p("overlays/airport.png"), 5, -15);
        factory.registerObstacle(Biome.GRASS, info);

        info = new AssetInfo(p("overlays/tower.png"), 10, -30);
        factory.registerObstacle(Biome.GRASS, info);

        info = new AssetInfo(p("overlays/satellite.png"), 10, -10);
        factory.registerObstacle(Biome.DARK_GRASS, info);

        info = new AssetInfo(p("overlays/rock_mountain.png"), 0, -21);
        factory.registerObstacle(Biome.ROCK, info);

        info = new AssetInfo(p("overlays/rock.png"), 0, -21);
        factory.registerObstacle(Biome.ROCK, info);

        info = new AssetInfo(p("overlays/pyramid.png"), 0, -23);
        factory.registerObstacle(Biome.LIGHT_ROCK, info);

        info = new AssetInfo(p("overlays/sand_mountain.png"), 0, -22);
        factory.registerObstacle(Biome.SAND, info);
    }

    private static void initPlayer(AssetFactory factory) {
        AssetInfo info = new AssetInfo(p("players/droid.png"), 10, -5);
        factory.registerPlayer(Droid.class, info);

        info = new AssetInfo(p("players/monster.png"), 10, 0);
        factory.registerPlayer(Monster.class, info);
    }

    private static void initArtifact(AssetFactory factory) {
        AssetInfo info = new AssetInfo(p("artifacts/bomb_malus.png"), 0, 0);
        factory.registerArtifact(BombMalus.class, info);

        /*
        factory.registerArtifact(SpeedBonus.class, Paths.get(RES + "artifacts/speed_bonus.png"));
        factory.registerArtifact(HealthBonus.class, Paths.get(RES + "artifacts/health_bonus.png"));
        */
    }

    private static void initParticle(AssetFactory factory) {
        AssetInfo info = new AssetInfo(p("particles/laser_lr.png"), 0, 0);
        factory.registerParticle(Laser.class, info);
    }

    private static Path p(String s) {
        return Paths.get(R + s);
    }
}
