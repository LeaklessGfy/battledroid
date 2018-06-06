package fr.battledroid.core.map;

import fr.battledroid.core.Settings;
import fr.battledroid.core.adaptee.Asset;
import fr.battledroid.core.adaptee.AssetFactory;
import fr.battledroid.core.Tile;
import fr.battledroid.core.map.noise.NoiseGenerator;

public final class MapFactory {
    public static Map createRandom(AssetFactory factory) {
        Settings settings = Settings.instance();
        int s = settings.mapSize;

        NoiseGenerator backgroundG = new NoiseGenerator(settings.octaves, settings.roughness, settings.scale);
        double[][] bNoises = backgroundG.generate(s, s, settings.seed);
        Tile[][] backgrounds = new Tile[s][s];

        NoiseGenerator overlayG = new NoiseGenerator(settings.octaves * 2, 1,  settings.scale - 0.0001);
        double[][] oNoises = overlayG.generate(s, s, settings.seed);
        Tile[][] overlays = new Tile[s][s];

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

                backgrounds[x][y] = new Tile(x, y, background);
                overlays[x][y] = new Tile(x, y, overlay);
            }
        }

        return new MapImpl(backgrounds, overlays, settings);
    }

    /*
    private static void snow(AssetFactory factory, Tile t, double n, Random rand) {
        t.setBackground(factory.getBiome(Biome.GRASS));

        if (n > Biome.SNOW.value() + 0.75) {
            if (rand.nextInt(100) < 50) {
                t.setOverlay(factory.getObstacle());
            }
        }
    }

    private static void grass(AssetFactory factory, Tile t, double n, Random rand) {
        t.setBackground(factory.getBiome(Biome.GRASS));

        if (rand.nextInt(100) < 5) {
            t.setOverlay(factory.getObstacle());
        } else if (rand.nextInt(100) < 3) {
            t.setOverlay(factory.getObstacle());
        } else if (rand.nextInt(100) > 97) {
            t.setOverlay(factory.getObstacle());
        }
    }

    private static void darkGrass(AssetFactory factory, Tile t, double n, Random rand) {
        t.setBackground(factory.getBiome(Biome.DARK_GRASS));

        if (rand.nextInt(100) < 10) {
            t.setOverlay(factory.getObstacle());
        }
    }

    private static void rock(AssetFactory factory, Tile t, double n, Random rand) {
        t.setBackground(factory.getBiome(Biome.ROCK));

        if (n > Biome.ROCK.value() && n < Biome.ROCK.value() + 0.12) {
            t.setOverlay(factory.getObstacle());
        } else if (n > Biome.ROCK.value() && n < Biome.ROCK.value() + 0.35) {
            if (rand.nextInt(50) > 0) {
                t.setBackground(factory.getShrink());
            }
        } else if (rand.nextInt(100) < 2) {
            t.setOverlay(factory.getObstacle());
        }
    }

    private static void lightRock(AssetFactory factory, Tile t, double n, Random rand) {
        t.setBackground(factory.getBiome(Biome.LIGHT_ROCK));

        if (rand.nextInt(100) < 10) {
            t.setOverlay(factory.getObstacle());
        }
    }

    private static void sand(AssetFactory factory, Tile t, double n, Random rand) {
        t.setBackground(factory.getBiome(Biome.SAND));

        if (n < Biome.SAND.value() + 0.20) {
            t.setOverlay(factory.getObstacle());
        }
    }
    */
}
