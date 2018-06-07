package fr.battledroid.core;

import fr.battledroid.core.utils.Utils;

import java.util.Random;

public final class Settings {
    private static Settings settings;

    /* MAP */
    private static final int DEFAULT_TILE_WIDTH = 256; //512
    private static final int DEFAULT_TILE_HEIGHT = 438; //876
    private static final int DEFAULT_TILE_ALPHA_WIDTH = 0;
    private static final int DEFAULT_TILE_ALPHA_HEIGHT = 142; //284
    private static final int DEFAULT_MAP_SIZE = 50;

    /* SCREEN */
    private static final int DEFAULT_SCREEN_WIDTH = 1000;
    private static final int DEFAULT_SCREEN_HEIGHT = 700;

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

    public final int screenWidth;
    public final int screenHeight;

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

        this.screenWidth = b.screenWidth;
        this.screenHeight = b.screenHeight;

        this.octaves = b.octaves;
        this.roughness = b.roughness;
        this.scale = b.scale;

        this.shrinkTimeout = b.shrinkTimeout;
        this.shrinkRadius = b.shrinkRadius;

        this.behaviourTimeout = b.behaviourTimeout;
        this.seed = b.seed;
    }

    public static Settings instance() {
        if (settings == null) {
            throw new IllegalStateException("Settings has not been built");
        }
        return settings;
    }

    public static class Builder {
        private int tileWidth;
        private int tileHeight;
        private int tileAlphaWidth;
        private int tileAlphaHeight;
        private int mapSize;

        private int screenWidth;
        private int screenHeight;

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

            screenWidth = DEFAULT_SCREEN_WIDTH;
            screenHeight = DEFAULT_SCREEN_HEIGHT;

            octaves = DEFAULT_OCTAVES;
            roughness = DEFAULT_ROUGHNESS;
            scale = DEFAULT_SCALE;

            shrinkTimeout = DEFAULT_SHRINK_TIMEOUT;
            shrinkRadius = DEFAULT_SHRINK_RADIUS;

            behaviourTimeout = DEFAULT_BEHAVIOUR_TIMEOUT;
            seed = new Random(System.currentTimeMillis()).nextInt(Integer.MAX_VALUE);
        }

        public Settings build() {
            if (settings != null) {
                throw new IllegalStateException("Settings already built");
            }
            settings = new Settings(this);
            return settings;
        }

        public Builder setTileWidth(int tileWidth) {
            this.tileWidth = tileWidth;
            return this;
        }

        public Builder setTileHeight(int tileHeight) {
            this.tileHeight = tileHeight;
            return this;
        }

        public Builder setTileAlphaWidth(int tileAlphaWidth) {
            this.tileAlphaWidth = tileAlphaWidth;
            return this;
        }

        public Builder setTileAlphaHeight(int tileAlphaHeight) {
            this.tileAlphaHeight = tileAlphaHeight;
            return this;
        }

        public Builder setMapSize(int mapSize) {
            this.mapSize = mapSize;
            return this;
        }

        public Builder setScreenWidth(int width) {
            this.screenWidth = width;
            return this;
        }

        public Builder setScreenHeight(int height) {
            this.screenHeight = height;
            return this;
        }

        public Builder setOctaves(int octaves) {
            this.octaves = octaves;
            return this;
        }

        public Builder setRoughness(double roughness) {
            this.roughness = roughness;
            return this;
        }

        public Builder setScale(double scale) {
            this.scale = scale;
            return this;
        }

        public Builder setShrinkTimeout(int shrinkTimeout) {
            this.shrinkTimeout = shrinkTimeout;
            return this;
        }

        public Builder setShrinkRadius(int shrinkRadius) {
            this.shrinkRadius = shrinkRadius;
            return this;
        }

        public Builder setBehaviourTimeout(int behaviourTimeout) {
            this.behaviourTimeout = behaviourTimeout;
            return this;
        }

        public Builder setSeed(int seed) {
            this.seed = seed;
            return this;
        }
    }
}
