package fr.battledroid.core.player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.LinkedBlockingDeque;

import fr.battledroid.core.adaptee.Color;
import fr.battledroid.core.map.tile.Context;
import fr.battledroid.core.adaptee.Canvas;
import fr.battledroid.core.function.Consumer;
import fr.battledroid.core.map.tile.Tile;
import fr.battledroid.core.utils.PointF;
import fr.battledroid.core.utils.Points;
import fr.battledroid.core.utils.Utils;
import fr.battledroid.core.adaptee.Asset;
import fr.battledroid.core.player.item.Inventory;
import fr.battledroid.core.player.item.Item;

abstract class AbstractPlayer implements Player {
    private final String uuid;
    private final Asset asset;
    private final Item weapon;
    private final Item shield;
    private final Inventory inventory;
    private final ArrayList<PlayerObserver> observers;
    private final LinkedBlockingDeque<Tile> moves;
    private final Consumer<Tile> onChange;
    private final Consumer<Tile> onArrive;
    private final boolean cpu;

    private Tile current;
    private Tile last;
    private double health;
    private double maxHealth;
    private int defense;
    private int maxDefense;
    private int speed;
    private int maxSpeed;
    private State state;

    AbstractPlayer(Builder builder) {
        this.uuid = UUID.randomUUID().toString();
        this.asset = builder.img;
        this.weapon = builder.weapon;
        this.shield = builder.shield;
        this.inventory = builder.inventory;
        this.observers = builder.observers;
        this.moves = new LinkedBlockingDeque<>();
        this.onChange = new Consumer<Tile>() {
            @Override
            public void accept(Tile val) {
                synchronized (moves) {
                    current = val;
                }
            }
        };
        this.onArrive = new Consumer<Tile>() {
            @Override
            public void accept(Tile val) {
                synchronized (moves) {
                    state = State.WAITING;
                }
            }
        };
        this.cpu = builder.cpu;
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
        return inventory;
    }

    @Override
    public State state() {
        synchronized (inventory) {
            return state;
        }
    }

    @Override
    public Tile current() {
        synchronized (moves) {
            return current;
        }
    }

    @Override
    public Tile last() {
        synchronized (moves) {
            return last;
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
    public boolean isCPU() {
        return cpu;
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
    public void setCurrent(Tile position) {
        this.current = position;
        this.last = last == null ? current : last;
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
    public void move(Tile tile) {
        System.out.println("OFFER " + tile);
        moves.offer(tile);
        last = tile;
    }

    @Override
    public void move(List<Tile> path) {
        for (Tile tile : path) {
            moves.offer(tile);
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
    public int getWidth() {
        return asset.getWidth();
    }

    @Override
    public int getHeight() {
        return asset.getHeight();
    }

    @Override
    public int getAlphaWidth() {
        return asset.getAlphaWidth();
    }

    @Override
    public int getAlphaHeight() {
        return asset.getAlphaHeight();
    }

    @Override
    public boolean isObstacle() {
        return true;
    }

    @Override
    public Color getColor() {
        return asset.getColor();
    }

    @Override
    public void draw(Canvas canvas, float x, float y) {
        asset.draw(canvas, x, y);
    }

    @Override
    public void tick() {
        synchronized (moves) {
            switch (state) {
                case WAITING:
                    nextMove();
                    break;
                case MOVING:
                    current.nextStep();
                    break;
            }
        }
    }

    private void nextMove() {
        Tile dst = moves.poll();
        if (dst == null) {
            return;
        }
        PointF screen = current.screen();
        PointF nScreen = Points.isoToScreen(dst.iso());
        state = State.MOVING;
        Context ctx = new Context(dst, new PointF(nScreen.x - screen.x, nScreen.y - screen.y), 10, onChange, onArrive);
        current.move(ctx);
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
}
