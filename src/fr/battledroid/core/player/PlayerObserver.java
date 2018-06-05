package fr.battledroid.core.player;

import fr.battledroid.core.player.item.Item;

public interface PlayerObserver {
    void updateHealth(double health);
    void updateDefense(int defense);
    void updateSpeed(int speed);
    void updateItem(Item item, boolean isNewItem);
}
