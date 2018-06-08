package fr.battledroid.core.player;

import fr.battledroid.core.adaptee.AssetFactory;
import fr.battledroid.core.particle.Laser;
import fr.battledroid.core.player.item.ShieldItem;
import fr.battledroid.core.player.item.WeaponItem;

public final class PlayerFactory {
    public static Droid createDroid(AssetFactory assetFactory) {
        WeaponItem weapon = new WeaponItem(assetFactory.getParticle(Laser.class));
        ShieldItem shield = new ShieldItem();
        return Droid.create(assetFactory.getPlayer(Droid.class), weapon, shield);
    }

    public static Monster createMonster(AssetFactory assetFactory) {
        WeaponItem weapon = new WeaponItem(assetFactory.getParticle(Laser.class));
        ShieldItem shield = new ShieldItem();
        return Monster.create(assetFactory.getPlayer(Monster.class), weapon, shield, false);
    }
}
