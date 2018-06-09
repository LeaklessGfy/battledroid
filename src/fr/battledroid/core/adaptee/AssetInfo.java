package fr.battledroid.core.adaptee;

import fr.battledroid.core.utils.Utils;

import java.nio.file.Path;

public class AssetInfo {
    private final Path path;
    private final int offsetX;
    private final int offsetY;

    public AssetInfo(Path path, int offsetX, int offsetY) {
        this.path = Utils.requireNonNull(path);
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }

    public Path getPath() {
        return path;
    }

    public int getOffsetX() {
        return offsetX;
    }

    public int getOffsetY() {
        return offsetY;
    }
}
