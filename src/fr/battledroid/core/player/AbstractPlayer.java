package fr.battledroid.core.player;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import fr.battledroid.core.AbstractAsset;
import fr.battledroid.core.utils.Utils;
import fr.battledroid.core.adaptee.Asset;
import fr.battledroid.core.player.item.Inventory;
import fr.battledroid.core.player.item.Item;
import fr.battledroid.core.function.BiConsumer;
import fr.battledroid.core.function.Consumer;

abstract class AbstractPlayer extends AbstractAsset implements Player {
    final static double DEFAULT_HEALTH = 100;
    final static int DEFAULT_DEFENSE = 0;
    final static int DEFAULT_MAX_DEFENSE = 100;
    final static int DEFAULT_SPEED = 10;
    final static int DEFAULT_FIELD = 1;

    private final String uuid;
    private final Item weapon;
    private final Item shield;
    private final Inventory inventory;
    private final ArrayList<PlayerObserver> observers;

    private double health;
    private double maxHealth;
    private int defense;
    private int maxDefense;
    private int speed;
    private int maxSpeed;
    private State state;

    AbstractPlayer(Builder builder) {
        super(builder.img);
        this.uuid = UUID.randomUUID().toString();
        this.weapon = builder.weapon;
        this.shield = builder.shield;
        this.inventory = builder.inventory;
        this.observers = builder.observers;
        this.health = builder.health;
        this.maxHealth = builder.maxHealth;
        this.defense = builder.defense;
        this.maxDefense = builder.maxDefense;
        this.speed = builder.speed;
        this.maxSpeed = builder.maxSpeed;
        this.state = State.WAITING;
    }

    @Override
    public double health() {
        return health;
    }

    @Override
    public int defense() {
        return defense;
    }

    @Override
    public Item weapon() {
        return weapon;
    }

    @Override
    public Item shield() {
        return shield;
    }

    @Override
    public double maxHealth() {
        return maxHealth;
    }

    @Override
    public int maxDefense() {
        return maxDefense;
    }

    @Override
    public Inventory inventory() {
        return new Inventory() {
            @Override
            public int size() {
                return inventory.size();
            }

            @Override
            public List<Item> asList() {
                return inventory.asList();
            }

            @Override
            public Iterator<Item> iterator() {
                return inventory.iterator();
            }

            @Override
            public void forEach(Consumer<? super Item> consumer) {
                inventory.forEach(consumer);
            }

            @Override
            public void forEach(BiConsumer<Item, Integer> consumer) {
                inventory.forEach(consumer);
            }

            @Override
            public int getQuantity(Item item) {
                return inventory.getQuantity(item);
            }
        };
    }

    @Override
    public State state() {
        synchronized (inventory) {
            return state;
        }
    }

    @Override
    public void addHealth(double health) {
        this.health = Math.max(0, this.health + health);
        this.health = Math.min(maxHealth, this.health);
        for (PlayerObserver o : observers) {
            o.updateHealth(this.health);
        }
    }

    @Override
    public void addDefense(int defense) {
        this.defense = Math.max(0, this.defense + defense);
        this.defense = Math.min(maxDefense, this.defense);
        for (PlayerObserver o : observers) {
            o.updateDefense(this.defense);
        }
    }

    @Override
    public void addSpeed(int speed) {
        this.speed = Math.max(maxSpeed, this.speed - speed);
        for (PlayerObserver o : observers) {
            o.updateSpeed(this.speed);
        }
    }

    @Override
    public void addItem(Item item) {
        boolean isNewItem = inventory.add(Utils.requireNonNull(item));
        for (PlayerObserver o : observers) {
            o.updateItem(item, isNewItem);
        }
    }

    @Override
    public void useItem(Item item) {
        inventory.remove(Utils.requireNonNull(item));
    }

    @Override
    public void takeDamage(int damage) {
        defense = defense - damage;
        if (defense < 0) {
            health = Math.max(0, health + defense);
            defense = 0;
        }
        for (PlayerObserver o : observers) {
            o.updateDefense(defense);
            o.updateHealth(health);
        }
    }

    @Override
    public boolean isDead() {
        return health <= 0;
    }

    @Override
    public void onEncounter(Player player) {
        synchronized (inventory) {
            state = State.FIGHTING;
        }
    }

    @Override
    public void onFightLeave() {
        synchronized (inventory) {
            state = State.WAITING;
        }
    }

    @Override
    public void attach(PlayerObserver observer) {
        observers.add(Utils.requireNonNull(observer));
        observer.updateHealth(health);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractPlayer that = (AbstractPlayer) o;
        return uuid.equals(that.uuid);
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }

    @Override
    public String toString() {
        return uuid;
    }

    static class Builder {
        private int x;
        private int y;
        private Asset img;

        private Item weapon;
        private Item shield;
        private final Inventory inventory;
        private final ArrayList<PlayerObserver> observers;

        private double health;
        private double maxHealth;
        private int defense;
        private int maxDefense;
        private int speed;
        private int maxSpeed;
        private int field;

        Builder(Asset img, Item weapon, Item shield) {
            this.img = Utils.requireNonNull(img);
            this.weapon = Utils.requireNonNull(weapon);
            this.shield = Utils.requireNonNull(shield);
            this.inventory = new Inventory();
            this.observers = new ArrayList<>();
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

        public Builder setWeapon(Item weapon) {
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
    }
}
