package fr.battledroid.core.engine;

import fr.battledroid.core.Direction;
import fr.battledroid.core.Settings;
import fr.battledroid.core.artifact.Artifact;
import fr.battledroid.core.artifact.ArtifactFactory;
import fr.battledroid.core.function.Consumer;
import fr.battledroid.core.map.tile.Tile;
import fr.battledroid.core.particle.Particle;
import fr.battledroid.core.utils.Point;
import fr.battledroid.core.utils.Utils;
import fr.battledroid.core.adaptee.Canvas;
import fr.battledroid.core.adaptee.AssetColor;
import fr.battledroid.core.utils.PointF;
import fr.battledroid.core.map.Map;
import fr.battledroid.core.player.Player;
import fr.battledroid.core.player.behaviour.AIMoveBehaviour;

import java.util.HashSet;
import java.util.List;
import java.util.Random;

final class EngineImpl implements Engine {
    private final Map map;
    private final AssetColor background;
    private final HashSet<Player> players;
    private final HashSet<Artifact> artifacts;
    private final Spawn spawner;

    private Listener listener;
    private AIMoveBehaviour behaviour;

    EngineImpl(Map map, AssetColor background) {
        this.map = Utils.requireNonNull(map);
        this.background = Utils.requireNonNull(background);
        this.players = new HashSet<>();
        this.artifacts = new HashSet<>();
        this.spawner = new Spawn(Settings.instance().nbPlayer, map.size());
    }

    @Override
    public void addPlayer(Player player) {
        players.add(player);
        Point p = spawner.spawn();
        Tile tile = map.tile(p);
        player.current(tile);
    }

    @Override
    public void generateArtifact(ArtifactFactory factory) {
        Settings s = Settings.instance();
        int size = s.mapSize * s.mapSize;
        int nbArtifact = size * 10 / 100;
        Random rand = new Random(s.seed);

        for (int i = 0; i < nbArtifact; i++) {
            Artifact artifact = factory.createRandom();
            int x = rand.nextInt(s.mapSize) % s.mapSize;
            int y = rand.nextInt(s.mapSize) % s.mapSize;
            Tile tile = map.tile(x, y);
            if (!tile.isBusy()) {
                artifact.current(tile);
                artifacts.add(artifact);
            }
        }
    }

    @Override
    public void drawMap(Canvas canvas, PointF offset) {
        canvas.drawColor(background);
        map.draw(canvas, offset);
    }

    @Override
    public void drawMiniMap(Canvas canvas) {
        canvas.drawColor(background);
        PointF cellSize = new PointF(canvas.getWidth() / map.size(), canvas.getHeight() / map.size());
        //map.drawMiniMap(canvas, cellSize);
    }

    @Override
    public void tick() {
        map.tick();
        HashSet<Player> dead = new HashSet<>();
        for (Player player : players) {
            checkParticleCollide(player);
            checkArtifactCollide(player);
            if (player.isDead()) {
                player.resetCurrent();
                dead.add(player);
            }
        }
        players.removeAll(dead);
    }

    @Override
    public void move(Player player, Point point, Consumer<Tile> onArrive) {
        Point src = player.last().iso().clone();
        Point p = src.offset(point);
        if (!map.valid(p)) {
            return;
        }
        Tile dst = map.tile(p);
        if (dst.isBusy()) {
            return;
        }
        player.move(dst);
        onArrive.accept(dst);
    }

    @Override
    public void move(Player player, Tile tile, Consumer<Tile> onArrive) {
        if (tile.isBusy()) {
            return;
        }
        Tile src = player.current();
        List<Tile> path = map.findPath(src.iso(), tile.iso());
        player.move(path);
    }

    @Override
    public Tile find(double x, double y) {
        return map.screenToTile(x, y);
    }

    @Override
    public void shoot(Player player, Direction direction) {
        Particle particle = player.shoot(direction);
        map.addParticle(particle);
    }

    @Override
    public void setListener(Engine.Listener listener) {
        this.listener = Utils.requireNonNull(listener);
    }

    @Override
    public void setBehaviour(AIMoveBehaviour behaviour) {
        this.behaviour = Utils.requireNonNull(behaviour);
    }

    private void checkPlayerCollide(Player player) {
        for (Player enemy : players) {
            if (enemy.equals(player)) {
                return;
            }
        }
    }

    private void checkArtifactCollide(Player player) {
        HashSet<Artifact> collides = new HashSet<>();
        for (Artifact artifact : artifacts) {
            if (!collides.contains(artifact) && artifact.hasCollide(player)) {
                artifact.onCollide(player);
                collides.add(artifact);
            }
        }
        for (Artifact artifact : collides) {
            artifact.resetCurrent();
            artifacts.remove(artifact);
        }
    }

    private void checkParticleCollide(Player player) {
        HashSet<Particle> collides = new HashSet<>();
        for (Particle particle : map.particles()) {
            if (!collides.contains(particle) && particle.hasCollide(player)) {
                particle.onCollide(player);
                collides.add(particle);
            }
        }
        map.particles().removeAll(collides);
    }
}
