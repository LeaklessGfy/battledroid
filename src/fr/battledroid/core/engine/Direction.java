package fr.battledroid.core.engine;

import fr.battledroid.core.utils.Point;

public enum Direction {
    UP, DOWN, LEFT, RIGHT, UP_LEFT, UP_RIGHT, DOWN_LEFT, DOWN_RIGHT;

    public static Point toPoint(Direction direction) {
        switch (direction) {
            case LEFT:
                return new Point(-1, 0);
            case RIGHT:
                return new Point(1, 0);
            case UP:
                return new Point(0, -1);
            case DOWN:
                return new Point(0, 1);
        }
        return new Point(0,0);
    }
}
