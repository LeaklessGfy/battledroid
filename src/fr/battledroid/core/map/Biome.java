package fr.battledroid.core.map;

public enum Biome {
    SNOW(1.0), GRASS(0.3), WATER(0.4), DARK_GRASS(0.2), ROCK(-0.4), LIGHT_ROCK(-0.6), SAND(-1.9);

    private final double value;

    Biome(double value) {
        this.value = value;
    }

    public double value() {
        return value;
    }
}
