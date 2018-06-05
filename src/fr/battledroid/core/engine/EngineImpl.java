package fr.battledroid.core.engine;

import fr.battledroid.core.Point;
import fr.battledroid.core.Position;
import fr.battledroid.core.Utils;
import fr.battledroid.core.adaptee.Canvas;
import fr.battledroid.core.adaptee.Color;
import fr.battledroid.core.PointF;
import fr.battledroid.core.map.Map;
import fr.battledroid.core.player.Player;
import fr.battledroid.core.player.behaviour.AIMoveBehaviour;

import java.util.HashMap;
import java.util.List;

final class EngineImpl implements Engine {
    private final Map map;
    private final Color background;
    private final HashMap<Player, Position> playersPositions;
    private final Spawn spawn;

    private Listener listener;
    private AIMoveBehaviour behaviour;

    EngineImpl(Map map, Color background) {
        this.map = Utils.requireNonNull(map);
        this.background = Utils.requireNonNull(background);
        this.playersPositions = new HashMap<>();
        this.spawn = new Spawn(2, map.size());
    }

    @Override
    public void addPlayer(Player player) {
        Point p = spawn.spawn();
        Position position = map.overlay(p);
        playersPositions.put(player, position);
        position.setAsset(player);
    }

    @Override
    public void drawMap(Canvas canvas, PointF offset) {
        canvas.drawColor(background);
        map.drawMap(canvas, offset);
    }

    @Override
    public void drawMiniMap(Canvas canvas) {
        canvas.drawColor(background);
        PointF cellSize = new PointF(canvas.getWidth() / map.size(), canvas.getHeight() / map.size());
        map.drawMiniMap(canvas, cellSize);
    }

    @Override
    public void tick() {
        map.tick();
    }

    @Override
    public void move(Player player, Point point) {
        Position src = playersPositions.get(player);
        Point p = src.iso().transform(point);
        if (!map.valid(p)) {
            return;
        }
        Position dst = map.overlay(p);
        move(player, dst);
    }

    @Override
    public void move(Player player, Position dst) {
        if (dst.isBusy()) {
            return;
        }

        Position src = playersPositions.get(player);
        List<Position> path = map.findPath(src.iso(), dst.iso());

        for (Position pos : path) {
            playersPositions.put(player, pos);
            src.postMove(pos);
            src = pos;
        }
    }

    @Override
    public Position findPosition(double x, double y) {
        return map.screenToTile(x, y);
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
