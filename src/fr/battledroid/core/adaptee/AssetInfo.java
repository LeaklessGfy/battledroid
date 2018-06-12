package fr.battledroid.core.adaptee;

import fr.battledroid.core.utils.Utils;

import java.nio.file.Path;

public final class AssetInfo {
    private final Path path;
    private final int offsetX;
    private final int offsetY;
    private final int width;
    private final int height;

    public AssetInfo(Path path, int width, int height, int offsetX, int offsetY) {
        this.path = Utils.requireNonNull(path);
        this.width = width;
        this.height = height;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }

    public AssetInfo(Path path, int offsetX, int offsetY) {
        this(path, -1, -1, offsetX, offsetY);
    }

    public AssetInfo(Path path) {
        this(path, -1, -1, 0, 0);
    }

    public Path getPath() {
        return path;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getOffsetX() {
        return offsetX;
    }

    public int getOffsetY() {
        return offsetY;
    }
}
