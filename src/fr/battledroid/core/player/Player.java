package fr.battledroid.core.player;

import fr.battledroid.core.adaptee.Asset;
import fr.battledroid.core.map.tile.Tile;
import fr.battledroid.core.particle.Particle;
import fr.battledroid.core.player.item.Inventory;
import fr.battledroid.core.player.item.Item;
import fr.battledroid.core.player.item.Weapon;
import fr.battledroid.core.utils.Point;

import java.util.List;

public interface Player extends Asset {
    double health();
    int defense();
    Weapon weapon();
    Item shield();
    double maxHealth();
    int maxDefense();
    Inventory inventory();
    State state();
    Tile current();
    Tile last();

    void addHealth(double health);
    void addDefense(int defense);
    void addSpeed(int speed);
    void addItem(Item item);
    void setCurrent(Tile tile);

    void useItem(Item item);
    void takeDamage(int damage);
    void move(Tile tile);
    void move(List<Tile> path);
    Particle shoot(Point offset);

    boolean isDead();
    boolean isCPU();

    void onEncounter(Player player);
    void onFightLeave();

    void attach(PlayerObserver observer);
}
