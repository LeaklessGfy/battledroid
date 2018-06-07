package fr.battledroid.core.player;

import fr.battledroid.core.map.tile.Tile;
import fr.battledroid.core.utils.PointF;

public final class Move {
    public final Tile dst;
    public final PointF dir;
    public final int max;
    public int i;

    public Move(Tile dst, PointF dir, int max) {
        this.dst = dst;
        this.dir = dir;
        this.max = max;
        this.i = 0;
    }
}
