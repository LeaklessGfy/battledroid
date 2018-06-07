package fr.battledroid.core.map;

import fr.battledroid.core.Settings;
import fr.battledroid.core.adaptee.Asset;
import fr.battledroid.core.adaptee.AssetFactory;
import fr.battledroid.core.map.noise.NoiseGenerator;
import fr.battledroid.core.map.tile.Tile;

public final class MapFactory {
    public static Map createRandom(AssetFactory factory) {
        Settings settings = Settings.instance();
        int s = settings.mapSize;
        Tile[][] tiles = new Tile[s][s];

        NoiseGenerator backgroundG = new NoiseGenerator(settings.octaves, settings.roughness, settings.scale);
        double[][] bNoises = backgroundG.generate(s, s, settings.seed);

        NoiseGenerator overlayG = new NoiseGenerator(settings.octaves * 2, 1,  settings.scale - 0.0001);
        double[][] oNoises = overlayG.generate(s, s, settings.seed);

        for (int x = 0; x < s; x++) {
            for (int y = 0; y < s; y++) {
                Asset background;
                Asset overlay;

                double b = bNoises[x][y];
                double o = oNoises[x][y];

                if (b > Biome.SNOW.value()) {
                    background = factory.getBiome(Biome.GRASS);
                    overlay = factory.getRandomObstacle(Biome.SNOW, o);
                } else if (b > Biome.GRASS.value()) {
                    background = factory.getBiome(Biome.GRASS);
                    overlay = factory.getRandomObstacle(Biome.GRASS, o);
                } else if (b > Biome.DARK_GRASS.value()) {
                    background = factory.getBiome(Biome.DARK_GRASS);
                    overlay = factory.getRandomObstacle(Biome.DARK_GRASS, o);
                } else if (b > Biome.ROCK.value()) {
                    background = factory.getBiome(Biome.ROCK);
                    overlay = factory.getRandomObstacle(Biome.ROCK, o);
                } else if (b > Biome.LIGHT_ROCK.value()) {
                    background = factory.getBiome(Biome.LIGHT_ROCK);
                    overlay = factory.getRandomObstacle(Biome.LIGHT_ROCK, o);
                } else if (b > Biome.SAND.value()) {
                    background = factory.getBiome(Biome.SAND);
                    overlay = factory.getRandomObstacle(Biome.SAND, o);
                } else {
                    background = factory.getBiome(Biome.GRASS);
                    overlay = null;
                }

                tiles[x][y] = new Tile(x, y, background, overlay);
            }
        }

        return new MapImpl(tiles);
    }
}
