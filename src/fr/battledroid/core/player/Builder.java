package fr.battledroid.core.player;

import fr.battledroid.core.adaptee.Asset;
import fr.battledroid.core.player.item.Inventory;
import fr.battledroid.core.player.item.Item;
import fr.battledroid.core.player.item.Weapon;
import fr.battledroid.core.utils.Utils;

import java.util.ArrayList;

public final class Builder {
    final static double DEFAULT_HEALTH = 100;
    final static int DEFAULT_DEFENSE = 0;
    final static int DEFAULT_MAX_DEFENSE = 100;
    final static int DEFAULT_SPEED = 10;
    final static int DEFAULT_FIELD = 1;

    int x;
    int y;
    Asset img;

    Weapon weapon;
    Item shield;
    final Inventory inventory;
    final ArrayList<PlayerObserver> observers;
    boolean cpu;

    double health;
    double maxHealth;
    int defense;
    int maxDefense;
    int speed;
    int maxSpeed;
    int field;

    Builder(Asset img, Weapon weapon, Item shield) {
        this.img = Utils.requireNonNull(img);
        this.weapon = Utils.requireNonNull(weapon);
        this.shield = Utils.requireNonNull(shield);
        this.inventory = new Inventory();
        this.observers = new ArrayList<>();
        this.cpu = false;
        this.health = DEFAULT_HEALTH;
        this.maxHealth = DEFAULT_HEALTH;
        this.defense = DEFAULT_DEFENSE;
        this.maxDefense = DEFAULT_MAX_DEFENSE;
        this.speed = DEFAULT_SPEED;
        this.maxSpeed = DEFAULT_SPEED;
        this.field = DEFAULT_FIELD;
    }

    public Builder setImage(Asset img) {
        this.img = Utils.requireNonNull(img);
        return this;
    }

    public Builder setWeapon(Weapon weapon) {
        this.weapon = Utils.requireNonNull(weapon);
        return this;
    }

    public Builder setShield(Item shield) {
        this.shield = Utils.requireNonNull(shield);
        return this;
    }

    public Builder setHealth(double health) {
        this.health = Utils.requireMin(health, 0);
        if (this.health > maxHealth) {
            this.health = maxHealth;
        }
        return this;
    }

    public Builder setMaxHealth(double maxHealth) {
        this.maxHealth = Utils.requireMin(maxHealth, 0);
        if (this.health > maxHealth) {
            this.health = maxHealth;
        }
        return this;
    }

    public Builder setDefense(int defense) {
        this.defense = Utils.requireMin(defense, 0);
        if (this.defense > maxDefense) {
            this.defense = maxDefense;
        }
        return this;
    }

    public Builder setMaxDefense(int maxDefense) {
        this.maxDefense = Utils.requireMin(maxDefense, 0);
        if (this.defense > maxDefense) {
            this.defense = maxDefense;
        }
        return this;
    }

    public Builder setSpeed(int speed) {
        this.speed = Utils.requireMin(speed, 1);
        if (this.speed < maxSpeed) {
            this.speed = maxSpeed;
        }
        return this;
    }

    public Builder setMaxSpeed(int maxSpeed) {
        this.maxSpeed = Utils.requireMin(maxSpeed, 1);
        if (this.speed < maxSpeed) {
            this.speed = maxSpeed;
        }
        return this;
    }

    public Builder setField(int field) {
        this.field = Utils.requireMin(field, 1);
        return this;
    }

    public Builder attach(PlayerObserver observer) {
        this.observers.add(Utils.requireNonNull(observer));
        return this;
    }

    public Builder addItem(Item item) {
        this.inventory.add(Utils.requireNonNull(item));
        return this;
    }

    public Builder setCPU(boolean cpu) {
        this.cpu = cpu;
        return this;
    }
}
