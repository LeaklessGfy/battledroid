package fr.battledroid.core.engine;

import fr.battledroid.core.utils.Point;

import java.util.ArrayList;

public final class Spawn {
    private final ArrayList<Point> positions;
    private int current = 0;

    public Spawn(int nbPlayer, int mapSize) {
        this.positions = new ArrayList<>();
        int center = mapSize / 2;
        double angle = 2 * Math.PI / nbPlayer;

        for (int i = 0; i < nbPlayer; i++) {
            int x = Math.min(mapSize - 1, (int) (center + Math.cos(angle * i) * center));
            int y = Math.min(mapSize - 1, (int) (center + Math.sin(angle * i) * center));
            positions.add(new Point(x, y));
        }
    }

    public Point spawn() {
        if (current >= positions.size()) {
            throw new IllegalStateException("Spawn can not place player anymore");
        }
        return positions.get(current++);
    }
}
