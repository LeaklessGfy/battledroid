package fr.battledroid.core.engine;

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

final class EngineImpl implements Engine {
    private final Map map;
    private final AssetColor background;
    private final HashSet<Player> players;
    private final Spawn spawner;

    private Listener listener;
    private AIMoveBehaviour behaviour;

    EngineImpl(Map map, AssetColor background) {
        this.map = Utils.requireNonNull(map);
        this.background = Utils.requireNonNull(background);
        this.players = new HashSet<>();
        this.spawner = new Spawn(2, map.size());
    }

    @Override
    public void addPlayer(Player player) {
        players.add(player);
        Point p = spawner.spawn();
        Tile tile = map.tile(p);
        tile.setOverlay(player);
        System.out.println(player.getCurrent());
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
        for (Player player : players) {
            checkParticleCollide(player);
        }
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
        Tile src = player.getCurrent();
        List<Tile> path = map.findPath(src.iso(), tile.iso());
        player.move(path);
    }

    @Override
    public Tile find(double x, double y) {
        return map.screenToTile(x, y);
    }

    @Override
    public void shoot(Player player, Point point) {
        Particle particle = player.shoot(point);
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
        players.forEach(enemy -> {
            if (enemy.equals(player)) {
                return;
            }

            if (enemy.hasCollide(player)) {}
        });

    }

    private void checkArtifactCollide() {

    }

    private void checkParticleCollide(Player player) {
        List<Particle> particles = map.particles();
        for (Particle particle : particles) {
            if (particle.hasCollide(player)) {
                particle.onCollide(player);
            }
        }
    }
}
