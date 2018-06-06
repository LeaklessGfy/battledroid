package fr.battledroid.core.map.tile;

import fr.battledroid.core.function.Consumer;
import fr.battledroid.core.utils.PointF;

public final class Context {
    final Tile dst;
    final PointF dir;
    final int max;
    final Consumer<Tile> onChange;
    final Consumer<Tile> onArrive;
    int i;

    public Context(Tile dst, PointF dir, int max, Consumer<Tile> onChange, Consumer<Tile> onArrive) {
        this.dst = dst;
        this.dir = dir;
        this.max = max;
        this.onChange = onChange;
        this.onArrive = onArrive;
        this.i = 0;
    }
}
