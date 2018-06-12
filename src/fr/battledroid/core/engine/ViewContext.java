package fr.battledroid.core.engine;

import fr.battledroid.core.Direction;
import fr.battledroid.core.Settings;
import fr.battledroid.core.Colors;
import fr.battledroid.core.function.Consumer;
import fr.battledroid.core.map.tile.Tile;
import fr.battledroid.core.utils.Points;
import fr.battledroid.core.utils.Utils;
import fr.battledroid.core.adaptee.Canvas;
import fr.battledroid.core.utils.PointF;
import fr.battledroid.core.player.Player;

public final class ViewContext {
    private final Engine engine;
    private final Player player;
    private final PointF offset = new PointF();
    private final PointF center = new PointF();

    private int centerI;
    private int centerSpeed;

    public ViewContext(Engine engine, Player player) {
        this.engine = Utils.requireNonNull(engine);
        this.player = Utils.requireNonNull(player);
    }

    public void tick() {
        engine.tick();
        if (centerI == centerSpeed)  {
            return;
        }
        offset.x += center.x;
        offset.y += center.y;
        centerI++;
    }

    public void offset(double dX, double dY) {
        if (dX == 0 || dY == 0) {
            return;
        }
        offset.x -= dX;
        offset.y -= dY;
    }

    public void move(double x, double y) {
        Tile tile = engine.find(x, y);
        engine.move(player, tile, new Consumer<Tile>() {
            @Override
            public void accept(Tile val) {
                smoothCenterOn(val);
            }
        });
    }

    public void move(Direction direction) {
        engine.move(player, Direction.toPoint(direction), new Consumer<Tile>() {
            @Override
            public void accept(Tile val) {
                smoothCenterOn(val);
            }
        });
    }

    public void shoot(Direction direction) {
        engine.shoot(player, direction);
    }

    public void draw(Canvas canvas) {
        if (player.isDead()) {
            drawDead(canvas);
        } else {
            engine.draw(canvas, offset);
            drawUI(canvas);
        }
    }

    public void center() {
        this.offset.set(Points.center(player.current()));
    }

    private void drawDead(Canvas canvas) {
        Settings s = Settings.instance();
        Colors c = Colors.instance();

        canvas.drawColor(c.getBackground());
        canvas.drawText("You lose", s.screenWidth / 2, s.screenHeight / 2, c.getHealth());
    }

    private void drawUI(Canvas canvas) {
        Settings s = Settings.instance();
        Colors c = Colors.instance();

        canvas.drawRect(10, s.screenHeight - 35, player.maxDefense(), 10, c.getBackground());
        canvas.drawRect(10, s.screenHeight - 35, player.defense(), 10, c.getDefense());

        canvas.drawRect(10, s.screenHeight - 20, (float) player.maxHealth(), 10, c.getBackground());
        canvas.drawRect(10, s.screenHeight - 20, (float) player.health(), 10, c.getHealth());
    }

    private void smoothCenterOn(Tile tile) {
        PointF c = Points.center(tile);
        //center.offset(-diff.x, -diff.y);

        int dist = Points.dist(offset, c);
        centerI = 0;
        centerSpeed = dist / 15;
        center.set((c.x - offset.x) / centerSpeed, (c.y - offset.y) / centerSpeed);
    }
}
