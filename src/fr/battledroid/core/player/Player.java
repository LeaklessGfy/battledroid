package fr.battledroid.core.player;

import fr.battledroid.core.adaptee.Asset;
import fr.battledroid.core.player.item.Inventory;
import fr.battledroid.core.player.item.Item;

public interface Player extends Asset {
    double health();
    int defense();
    Item weapon();
    Item shield();
    double maxHealth();
    int maxDefense();
    Inventory inventory();
    State state();

    void addHealth(double health);
    void addDefense(int defense);
    void addSpeed(int speed);
    void addItem(Item item);
    void useItem(Item item);
    void takeDamage(int damage);

    boolean isDead();
    boolean isCPU();

    void onEncounter(Player player);
    void onFightLeave();

    void attach(PlayerObserver observer);
}
