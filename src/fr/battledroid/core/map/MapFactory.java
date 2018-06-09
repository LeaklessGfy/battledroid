package fr.battledroid.core.map;

import fr.battledroid.core.Settings;
import fr.battledroid.core.adaptee.Asset;
import fr.battledroid.core.adaptee.AssetFactory;
import fr.battledroid.core.map.noise.NoiseGenerator;
import fr.battledroid.core.map.tile.Tile;
import fr.battledroid.core.map.tile.math.TileMath;

import java.util.Random;

public final class MapFactory {
    public static Map createSimple(Asset background, TileMath math) {
        Settings settings = Settings.instance();
        int s = settings.mapSize;
        Tile[][] tiles = new Tile[s][s];

        for (int x = 0; x < s; x++) {
            for (int y = 0; y < s; y++) {
                tiles[x][y] = new Tile(x, y, math);
                tiles[x][y].setBackground(background);
            }
        }

        return new MapImpl(tiles);
    }

    public static Map createAdvanced(Asset background, Asset overlay, TileMath math) {
        Settings settings = Settings.instance();
        int s = settings.mapSize;
        Tile[][] tiles = new Tile[s][s];

        for (int x = 0; x < s; x++) {
            for (int y = 0; y < s; y++) {
                tiles[x][y] = new Tile(x, y, math);
                tiles[x][y].setBackground(background);
                tiles[x][y].setOverlay(overlay);
            }
        }

        return new MapImpl(tiles);
    }

    public static Map createRandom(AssetFactory factory, TileMath math) {
        Settings settings = Settings.instance();
        int s = settings.mapSize;
        Tile[][] tiles = new Tile[s][s];

        NoiseGenerator backgroundG = new NoiseGenerator(settings.octaves, settings.roughness, settings.scale);
        double[][] bNoises = backgroundG.generate(s, s, settings.seed);

        NoiseGenerator overlayG = new NoiseGenerator(settings.octaves * 2, 1,  settings.scale - 0.0001);
        double[][] oNoises = overlayG.generate(s, s, settings.seed);

        Random rand = new Random(settings.seed);

        for (int x = 0; x < s; x++) {
            for (int y = 0; y < s; y++) {
                Asset background;
                Asset overlay;

                double b = bNoises[x][y];
                double o = oNoises[x][y];

                if (b > Biome.SNOW.value()) {
                    background = factory.getBiome(Biome.SNOW);
                    overlay = random(rand, factory, Biome.SNOW, o);
                } else if (b > Biome.GRASS.value()) {
                    background = factory.getBiome(Biome.GRASS);
                    overlay = random(rand, factory, Biome.GRASS, o);
                } else if (b > Biome.DARK_GRASS.value()) {
                    background = factory.getBiome(Biome.DARK_GRASS);
                    overlay = random(rand, factory, Biome.DARK_GRASS, o);
                } else if (b > Biome.ROCK.value()) {
                    background = factory.getBiome(Biome.ROCK);
                    overlay = random(rand, factory, Biome.ROCK, o);
                } else if (b > Biome.LIGHT_ROCK.value()) {
                    background = factory.getBiome(Biome.LIGHT_ROCK);
                    overlay = random(rand, factory, Biome.LIGHT_ROCK, o);
                } else if (b > Biome.SAND.value()) {
                    background = factory.getBiome(Biome.SAND);
                    overlay = random(rand, factory, Biome.SAND, o);
                } else {
                    background = factory.getBiome(Biome.GRASS);
                    overlay = null;
                }

                tiles[x][y] = new Tile(x, y, math);
                tiles[x][y].setBackground(background);
                tiles[x][y].setOverlay(overlay);
            }
        }

        return new MapImpl(tiles);
    }

    private static Asset random(Random rand, AssetFactory factory, Biome biome, double v) {
        if (rand.nextInt(100) < 10) {
            return factory.getRandomObstacle(biome);
        }
        return null;
    }
}
