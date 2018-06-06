package fr.battledroid.core.engine;

import fr.battledroid.core.map.tile.Tile;
import fr.battledroid.core.particle.Laser;
import fr.battledroid.core.utils.Point;
import fr.battledroid.core.utils.Utils;
import fr.battledroid.core.adaptee.Canvas;
import fr.battledroid.core.adaptee.Color;
import fr.battledroid.core.utils.PointF;
import fr.battledroid.core.map.Map;
import fr.battledroid.core.player.Player;
import fr.battledroid.core.player.behaviour.AIMoveBehaviour;

import java.util.HashSet;
import java.util.List;

final class EngineImpl implements Engine {
    private final Map map;
    private final Color background;
    private final HashSet<Player> players;
    private final Spawn spawner;

    private Listener listener;
    private AIMoveBehaviour behaviour;

    EngineImpl(Map map, Color background) {
        this.map = Utils.requireNonNull(map);
        this.background = Utils.requireNonNull(background);
        this.players = new HashSet<>();
        this.spawner = new Spawn(2, map.size());
    }

    @Override
    public void addPlayer(Player player) {
        Point p = spawner.spawn();
        Tile tile = map.overlay(p);
        player.setCurrent(tile);
        tile.setAsset(player);
    }

    @Override
    public void drawMap(Canvas canvas, PointF offset) {
        background.draw(canvas);
        map.drawMap(canvas, offset);
    }

    @Override
    public void drawMiniMap(Canvas canvas) {
        background.draw(canvas);
        PointF cellSize = new PointF(canvas.getWidth() / map.size(), canvas.getHeight() / map.size());
        map.drawMiniMap(canvas, cellSize);
    }

    @Override
    public void tick() {
        map.tick();
    }

    @Override
    public void move(Player player, Point point) {
        Point src = player.last().iso().clone();
        Point p = src.offset(point);
        if (!map.valid(p)) {
            return;
        }
        Tile dst = map.overlay(p);
        if (dst.isBusy()) {
            return;
        }
        player.move(dst);
    }

    @Override
    public void move(Player player, Tile tile) {
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
    public void shoot(Player player, Point point) {
        Point src = player.current().iso().clone();
        PointF screen = player.current().screen().clone();
        map.addParticle(new Laser(src, screen, point));
    }

    @Override
    public void setListener(Engine.Listener listener) {
        this.listener = Utils.requireNonNull(listener);
    }

    @Override
    public void setBehaviour(AIMoveBehaviour behaviour) {
        this.behaviour = Utils.requireNonNull(behaviour);
    }
}
