package fr.battledroid.core.particle;

import fr.battledroid.core.adaptee.Asset;
import fr.battledroid.core.adaptee.Canvas;
import fr.battledroid.core.utils.Point;
import fr.battledroid.core.utils.PointF;
import fr.battledroid.core.utils.Points;

public final class Laser implements Particle {
    private final Point iso;
    private final PointF screen;
    private final Point offset;
    private final Point dst;
    private final PointF dir;

    private int i = 0;
    private int speed = 20;

    public Laser(Point iso, PointF screen, Point offset) {
        this.iso = iso;
        this.screen = screen == null ? Points.isoToScreen(iso) : screen;
        this.offset = offset;
        this.dst = iso.clone().offset(offset);
        this.dir = Points.movement(iso, dst);
    }

    @Override
    public boolean shouldDraw(PointF offset, Point canvasSize) {
        return false;
    }

    @Override
    public void drawMap(Canvas canvas, PointF offset) {
        canvas.drawCircle(screen.x + 100 + offset.x, screen.y + 200 + offset.y, 30, null);
    }

    @Override
    public void drawMiniMap(Canvas canvas, PointF cellSize) {

    }

    @Override
    public void tick() {
        if (i == speed) {
            i = 0;
            speed += speed / 4;
            iso.set(dst);
            dst.set(iso.clone().offset(offset));
            dir.set(Points.movement(iso, dst));
        }
        screen.set(Points.step(screen, dir, speed));
        i++;
    }

    @Override
    public boolean hasEnd() {
        return speed > 40;
    }

    @Override
    public boolean hasCollide(Asset src, Asset dst) {
        return false;
    }
}
