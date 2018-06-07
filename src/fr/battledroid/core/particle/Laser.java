package fr.battledroid.core.particle;

import fr.battledroid.core.adaptee.Canvas;
import fr.battledroid.core.player.Player;
import fr.battledroid.core.utils.*;

public final class Laser implements Particle {
    private final Point iso;
    private final PointF screen;
    private final Point offset;
    private final Point dst;
    private final PointF dir;
    private final Player owner;

    private int i = 0;
    private int speed = 20;

    public Laser(Point iso, PointF screen, Point offset, Player owner) {
        this.iso = Utils.requireNonNull(iso);
        this.screen = Utils.requireNonNull(screen);
        this.offset = Utils.requireNonNull(offset);
        this.dst = iso.clone().offset(offset);
        this.dir = Points.movement(iso, dst);
        this.owner = Utils.requireNonNull(owner);
    }

    @Override
    public void draw(Canvas canvas, PointF offset) {
        canvas.drawCircle(screen.x + 100 + offset.x, screen.y + 200 + offset.y, 30, null);
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
    public boolean hasCollide(Player dst) {
        HitBox hitBox = dst.hitBox();

        if (hitBox.x < screen.x + 30 && hitBox.x + hitBox.width > screen.x
                && hitBox.y < screen.y + 30 && hitBox.y + hitBox.height > screen.y) {
            return true;
        }

        return false;
    }

    @Override
    public void onCollide(Player dst) {
        if (!dst.equals(owner)) {
            dst.takeDamage(20);
        }
    }
}
