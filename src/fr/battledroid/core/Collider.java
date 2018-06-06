package fr.battledroid.core;

import fr.battledroid.core.map.tile.Tile;

public interface Collider {
    boolean hasCollide(Tile src, Tile dst);
}
