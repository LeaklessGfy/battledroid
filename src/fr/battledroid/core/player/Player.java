package fr.battledroid.core.player;

import fr.battledroid.core.player.item.Inventory;
import fr.battledroid.core.player.item.Item;
import fr.battledroid.core.asset.Collider;
import fr.battledroid.core.asset.Asset;

public interface Player extends Asset, Collider {
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

    void postMove(int x, int y);
    void attach(PlayerObserver observer);
}
