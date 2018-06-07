package fr.battledroid.core.player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.LinkedBlockingDeque;

import fr.battledroid.core.adapter.AssetWrapper;
import fr.battledroid.core.map.tile.Context;
import fr.battledroid.core.function.Consumer;
import fr.battledroid.core.map.tile.Tile;
import fr.battledroid.core.particle.Particle;
import fr.battledroid.core.player.item.Weapon;
import fr.battledroid.core.utils.*;
import fr.battledroid.core.adaptee.Asset;
import fr.battledroid.core.player.item.Inventory;
import fr.battledroid.core.player.item.Item;

abstract class AbstractPlayer extends AssetWrapper implements Player {
    private final String uuid;
    private final Asset asset;
    private final Weapon weapon;
    private final Item shield;
    private final Inventory inventory;
    private final ArrayList<PlayerObserver> observers;
    private final LinkedBlockingDeque<Tile> moves;
    private final Consumer<Tile> onChange;
    private final Consumer<Tile> onArrive;
    private final boolean cpu;

    private final PointF screen;
    private Context ctx;
    private Tile last;

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
                    setCurrent(val);
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
        this.screen = new PointF();
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
    public Weapon weapon() {
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
    public Particle shoot(Point offset) {
        return weapon.shoot(getCurrent().iso().clone(), screen.clone(), offset);
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
    public void tick() {
        synchronized (moves) {
            switch (state) {
                case WAITING:
                    nextMove();
                    break;
                case MOVING:
                    nextStep();
                    break;
            }
        }
    }

    @Override
    public Tile getCurrent() {
        synchronized (moves) {
            return asset.getCurrent();
        }
    }

    @Override
    public void setCurrent(Tile position) {
        synchronized (moves) {
            asset.setCurrent(position);
            this.last = last == null ? position : last;
        }
    }

    @Override
    public boolean hasCollide(Player player) {
        return false;
    }

    @Override
    public void onCollide(Player player) {

    }

    private void nextMove() {
        Tile dst = moves.poll();
        if (dst == null) {
            return;
        }
        state = State.MOVING;
        PointF dir = Points.movement(getCurrent().iso(), dst.iso());
        ctx = new Context(dst, dir, speed, onChange, onArrive);
    }

    private void nextStep() {
        if (ctx.i == ctx.max / 2) {
            ctx.dst.setOverlay(this);
            getCurrent().setOverlay(null);
            onChange.accept(ctx.dst);
        } else if (ctx.i > ctx.max) {
            onArrive.accept(ctx.dst);
            ctx = null;
        } else {
            screen.set(Points.step(screen, ctx.dir, ctx.max));
            ctx.i++;
        }
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
