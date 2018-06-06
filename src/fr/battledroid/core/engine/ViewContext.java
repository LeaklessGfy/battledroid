package fr.battledroid.core.engine;

import fr.battledroid.core.Position;
import fr.battledroid.core.utils.Utils;
import fr.battledroid.core.adaptee.Canvas;
import fr.battledroid.core.utils.PointF;
import fr.battledroid.core.player.Player;

public final class ViewContext {
    private final Engine engine;
    private final Player player;
    private final PointF offset = new PointF(0, 0);

    public ViewContext(Engine engine, Player player) {
        this.engine = Utils.requireNonNull(engine);
        this.player = Utils.requireNonNull(player);
    }

    public void offset(double dX, double dY) {
        if (dX == 0 || dY == 0) {
            return;
        }
        offset.x -= dX;
        offset.y -= dY;
    }

    public void move(double x, double y) {
        Position dst = engine.findPosition(x, y);
        engine.move(player, dst);
    }

    public void move(Direction direction) {
        engine.move(player, Direction.toPoint(direction));
    }

    public void draw(Canvas canvas) {
        engine.drawMap(canvas, offset);
    }
}
