package fr.battledroid.core.map.noise;

public final class NoiseGenerator {
    private final int octaves;
    private final double roughness;
    private final double scale;

    public NoiseGenerator(int octaves, double roughness, double scale) {
        this.octaves = octaves; // Number of Layers combined together to get a natural looking surface
        this.roughness = roughness; // Increasing the of the range between -1 and 1, causing higher values eg more rough terrain
        this.scale = scale; // Overall scaling of the terrain
    }

    public double[][] generate(int width, int height, int seed) {
        double[][] totalNoise = new double[width][height];
        double layerFrequency = scale;
        double layerWeight = 1;

        SimplexNoise.setSeed(seed);

        for (int octave = 0; octave < octaves; octave++) {
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    totalNoise[x][y] += SimplexNoise.noise(x * layerFrequency, y * layerFrequency) * layerWeight;
                }
            }

            layerFrequency *= 2;
            layerWeight *= roughness;
        }

        return totalNoise;
    }
}
