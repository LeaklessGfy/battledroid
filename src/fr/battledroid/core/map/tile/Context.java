package fr.battledroid.core.map.tile;

import fr.battledroid.core.function.Consumer;
import fr.battledroid.core.utils.PointF;

public final class Context {
    public final Tile dst;
    public final PointF dir;
    public final int max;
    public final Consumer<Tile> onChange;
    public final Consumer<Tile> onArrive;
    public int i;

    public Context(Tile dst, PointF dir, int max, Consumer<Tile> onChange, Consumer<Tile> onArrive) {
        this.dst = dst;
        this.dir = dir;
        this.max = max;
        this.onChange = onChange;
        this.onArrive = onArrive;
        this.i = 0;
    }
}
