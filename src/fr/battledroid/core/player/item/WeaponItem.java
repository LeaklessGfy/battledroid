package fr.battledroid.core.player.item;

import fr.battledroid.core.Direction;
import fr.battledroid.core.adaptee.Asset;
import fr.battledroid.core.map.tile.Tile;
import fr.battledroid.core.particle.Laser;
import fr.battledroid.core.particle.Particle;
import fr.battledroid.core.player.Player;
import fr.battledroid.core.utils.Point;
import fr.battledroid.core.utils.PointF;

public final class WeaponItem implements Item, Weapon {
    private int damage = 10;
    private int range = 3;
    private int rate = 1000;

    private Asset asset;
    private long lastShoot;

    public WeaponItem(Asset asset) {
        this.asset = asset;
    }

    @Override
    public void use(Player self, Player target, boolean isCritic) {
        target.takeDamage(isCritic ? damage * 2 : damage);
    }

    @Override
    public Particle shoot(Tile tile, Direction direction, Player owner) {
        long now = System.currentTimeMillis();
        if (now - lastShoot < rate) {
            return null;
        }
        lastShoot = now;

        Point src = tile.iso();
        Point off = Direction.toPoint(direction);
        off.x = off.x == 0 ? off.x : off.x * range;
        off.y = off.y == 0 ? off.y : off.y * range;

        if (direction == Direction.RIGHT) {
            off.x++;
        }

        Point dst = src.clone().offset(off);
        PointF dir = tile.moveTo(dst);

        return new Laser(asset, tile.getScreenBackground(), dir, owner);
    }

    @Override
    public String toString() {
        return "Weapon";
    }
}
