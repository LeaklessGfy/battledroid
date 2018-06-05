package fr.battledroid.core.engine;

import fr.battledroid.core.Utils;
import fr.battledroid.core.asset.Canvas;
import fr.battledroid.core.asset.PointF;
import fr.battledroid.core.player.Player;

import java.util.logging.Logger;

public final class ViewContext {
    private static Logger logger = Logger.getLogger(ViewContext.class.getName());

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

    public void move(int x, int y) {
        player.postMove(x, y);
    }

    public void right() {
        logger.info("Post move on : " + (player.iso().x + 1) + ", " + (player.iso().x));
        player.postMove(player.iso().x + 1, player.iso().y);
    }

    public void draw(Canvas canvas) {
        engine.draw(canvas, offset);
        player.draw(canvas, offset);
    }
}
