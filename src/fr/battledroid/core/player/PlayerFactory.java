package fr.battledroid.core.player;

import fr.battledroid.core.adaptee.AssetFactory;
import fr.battledroid.core.player.item.Item;
import fr.battledroid.core.player.item.ShieldItem;
import fr.battledroid.core.player.item.WeaponItem;

public final class PlayerFactory {
    public static Droid createDroid(AssetFactory assetFactory) {
        Item weapon = new WeaponItem();
        Item shield = new ShieldItem();
        return Droid.create(assetFactory.getPlayer(Droid.class), weapon, shield);
    }
}
