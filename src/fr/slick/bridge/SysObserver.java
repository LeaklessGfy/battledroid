package fr.slick.bridge;

import fr.battledroid.core.player.PlayerObserver;
import fr.battledroid.core.player.item.Item;

public class SysObserver implements PlayerObserver {
    @Override
    public void updateHealth(double health) {
        System.out.println("New health " + health);
    }

    @Override
    public void updateDefense(int defense) {
        System.out.println("New defense " + defense);
    }

    @Override
    public void updateSpeed(int speed) {
        System.out.println("New speed " + speed);
    }

    @Override
    public void updateItem(Item item, boolean isNewItem) {
        System.out.println("New item " + item);
    }
}
