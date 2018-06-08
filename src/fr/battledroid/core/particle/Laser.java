package fr.battledroid.core.particle;

import fr.battledroid.core.adaptee.Asset;
import fr.battledroid.core.adaptee.Canvas;
import fr.battledroid.core.player.Player;
import fr.battledroid.core.utils.*;

public final class Laser implements Particle {
    private final Asset asset;

    private final PointF screen;
    private final PointF dir;
    private final Player owner;

    private int i = 0;
    private int speed = 30;
    private int range = 3;
    private int size = 30;

    public Laser(Asset asset, Point iso, PointF screen, Point offset, Player owner) {
        this.asset = asset;
        Utils.requireNonNull(iso);
        Utils.requireNonNull(offset);
        this.screen = Utils.requireNonNull(screen);
        this.dir = Points.movement(iso, iso.clone().offset(rangedOffset(offset)));
        this.owner = Utils.requireNonNull(owner);
    }

    @Override
    public void draw(Canvas canvas, PointF offset) {
        canvas.drawCircle(screen.x + 130 + offset.x, screen.y + 200 + offset.y, size, null);
        canvas.drawAsset(asset, screen.x + offset.x, screen.y + offset.y);
    }

    @Override
    public void tick() {
        if (i == speed) {
            return;
        }
        screen.offset(Points.step(dir, speed));
        i++;
    }

    @Override
    public boolean hasEnd() {
        return i == speed;
    }

    @Override
    public boolean hasCollide(Player dst) {
        if (dst.equals(owner)) {
            return false;
        }
        HitBox hitBox = dst.current().hitBox();
        HitBox mHitBox = new HitBox(screen.x, screen.y,  size, size);
        return hitBox.intersect(mHitBox);
    }

    @Override
    public void onCollide(Player dst) {
        dst.takeDamage(20);
    }

    private Point rangedOffset(Point offset) {
        offset.x = offset.x == 0 ? offset.x : offset.x * range;
        offset.y = offset.y == 0 ? offset.y : offset.y * range;
        return offset;
    }
}
