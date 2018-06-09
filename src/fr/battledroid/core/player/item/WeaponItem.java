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

    private Asset asset;

    public WeaponItem(Asset asset) {
        this.asset = asset;
    }

    @Override
    public void use(Player self, Player target, boolean isCritic) {
        target.takeDamage(isCritic ? damage * 2 : damage);
    }

    @Override
    public String toString() {
        return "Weapon";
    }

    @Override
    public Particle shoot(Tile tile, Direction direction, Player owner) {
        Point src = tile.iso();
        Point off = Direction.toPoint(direction);
        off.x = off.x == 0 ? off.x : off.x * range;
        off.y = off.y == 0 ? off.y : off.y * range;

        return new Laser(asset, tile.getScreenBackground(), new PointF(), owner);
    }
}
