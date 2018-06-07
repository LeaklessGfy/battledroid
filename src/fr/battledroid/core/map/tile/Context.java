package fr.battledroid.core.map.tile;

import fr.battledroid.core.utils.PointF;

public final class Context {
    public final Tile dst;
    public final PointF dir;
    public final int max;
    public int i;

    public Context(Tile dst, PointF dir, int max) {
        this.dst = dst;
        this.dir = dir;
        this.max = max;
        this.i = 0;
    }
}
