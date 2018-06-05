package fr.battledroid.core.player;

import fr.battledroid.core.player.item.Item;
import fr.battledroid.core.player.item.ShieldItem;
import fr.battledroid.core.player.item.WeaponItem;
import fr.swing.adapter.SpriteFactory;

import java.nio.file.Paths;

public class PlayerFactory {
    public static Droid createDroid(SpriteFactory spriteFactory) {
        Item weapon = new WeaponItem();
        Item shield = new ShieldItem();
        return Droid.create(spriteFactory.get(Paths.get("resources/players/droid.png")), weapon, shield);
    }
}
