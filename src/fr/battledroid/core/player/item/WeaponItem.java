package fr.battledroid.core.player.item;

import fr.battledroid.core.adaptee.Asset;
import fr.battledroid.core.particle.Laser;
import fr.battledroid.core.particle.Particle;
import fr.battledroid.core.player.Player;
import fr.battledroid.core.utils.Point;
import fr.battledroid.core.utils.PointF;

public final class WeaponItem implements Item, Weapon {
    private int damage = 10;

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
    public Particle shoot(Point iso, PointF screen, Point offset, Player owner) {
        return new Laser(asset, iso, screen, offset, owner);
    }
}
