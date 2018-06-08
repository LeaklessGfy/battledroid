package fr.battledroid.core.map.tile;

public interface TileAware {
    Tile current();
    void current(Tile current);
    void resetCurrent();
}
