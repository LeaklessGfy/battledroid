package fr.battledroid.core;

import fr.battledroid.core.utils.RandomGenerator;
import fr.battledroid.core.utils.Utils;

public final class Settings {
    private static Settings settings;

    /* MAP */
    private static final int DEFAULT_TILE_WIDTH = 256; //512
    private static final int DEFAULT_TILE_HEIGHT = 438; //876
    private static final int DEFAULT_TILE_ALPHA_WIDTH = 0;
    private static final int DEFAULT_TILE_ALPHA_HEIGHT = 142; //284
    private static final int DEFAULT_MAP_SIZE = 4;

    /* FOLDER */
    private static final String DEFAULT_RESOURCES = "resources";
    private static final String DEFAULT_THEME = "pixel";

    /* NOISE */
    private static final int DEFAULT_OCTAVES = 4;
    private static final double DEFAULT_ROUGHNESS = 0.8;
    private static final double DEFAULT_SCALE = 0.0098;

    /* SHRINK */
    private static final int DEFAULT_SHRINK_TIMEOUT = 20000;
    private static final int DEFAULT_SHRINK_RADIUS = DEFAULT_MAP_SIZE / 2;

    /* TIMEOUT */
    private static final int DEFAULT_BEHAVIOUR_TIMEOUT = 2000;

    public final int tileWidth;
    public final int tileHeight;
    public final int tileAlphaWidth;
    public final int tileAlphaHeight;
    public final int mapSize;

    public final String resources;
    public final String theme;

    public final int octaves;
    public final double roughness;
    public final double scale;

    public final int shrinkTimeout;
    public final int shrinkRadius;

    public final int behaviourTimeout;
    public final int seed;

    private Settings(Builder b) {
        this.tileWidth = Utils.requireMin(b.tileWidth, 64);
        this.tileHeight = Utils.requireMin(b.tileHeight, 64);
        this.tileAlphaWidth = Utils.requireMinMax(b.tileAlphaWidth, -1, tileWidth);
        this.tileAlphaHeight = Utils.requireMinMax(b.tileAlphaHeight, -1, tileHeight);
        this.mapSize = Utils.requireMin(b.mapSize, 4);

        this.resources = Utils.requireNonNull(b.resources);
        this.theme = Utils.requireNonNull(b.theme);

        this.octaves = b.octaves;
        this.roughness = b.roughness;
        this.scale = b.scale;

        this.shrinkTimeout = b.shrinkTimeout;
        this.shrinkRadius = b.shrinkRadius;

        this.behaviourTimeout = b.behaviourTimeout;
        this.seed = b.seed;
    }

    private Settings() {
        this(new Builder());
    }

    public static Settings instance() {
        if (settings == null) {
            settings = new Settings();
        }
        return settings;
    }

    public static class Builder {
        private int tileWidth;
        private int tileHeight;
        private int tileAlphaWidth;
        private int tileAlphaHeight;
        private int mapSize;

        private String resources;
        private String theme;

        private int octaves;
        private double roughness;
        private double scale;

        private int shrinkTimeout;
        private int shrinkRadius;

        private int behaviourTimeout;
        private int seed;

        public Builder() {
            tileWidth = DEFAULT_TILE_WIDTH;
            tileHeight = DEFAULT_TILE_HEIGHT;
            tileAlphaWidth = DEFAULT_TILE_ALPHA_WIDTH;
            tileAlphaHeight = DEFAULT_TILE_ALPHA_HEIGHT;
            mapSize = DEFAULT_MAP_SIZE;

            resources = DEFAULT_RESOURCES;
            theme = DEFAULT_THEME;

            octaves = DEFAULT_OCTAVES;
            roughness = DEFAULT_ROUGHNESS;
            scale = DEFAULT_SCALE;

            shrinkTimeout = DEFAULT_SHRINK_TIMEOUT;
            shrinkRadius = DEFAULT_SHRINK_RADIUS;

            behaviourTimeout = DEFAULT_BEHAVIOUR_TIMEOUT;
            seed = RandomGenerator.randomInt(0, Integer.MAX_VALUE - 1);
        }
    }
}
