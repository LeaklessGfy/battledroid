package fr.battledroid.core.player.behaviour;

import fr.battledroid.core.Direction;
import fr.battledroid.core.engine.Engine;
import fr.battledroid.core.map.Map;
import fr.battledroid.core.map.tile.Tile;
import fr.battledroid.core.player.Player;
import fr.battledroid.core.utils.Points;

import java.util.List;

public class DefaultAIBehaviour implements AIBehaviour {
    @Override
    public void behave(Player ai, Engine engine, Map map) {
        int center = (map.size() - 1) / 2;
        Tile src = ai.last();
        Tile dst = map.tile(center, center);

        if (Points.dist(src.iso(), dst.iso()) > 5) {
            List<Tile> path = map.findNearestPath(src.iso(), dst.iso());
            ai.move(path);
        }

        for (Player player : engine.enemies(ai)) {
            int dist = Points.dist(src.iso(), player.current().iso());

            if (dist < 4) {
                Direction direction = Direction.fromPoints(src.iso(), player.current().iso());
                engine.shoot(ai, direction);
            }
        }
    }
}
