package fr.battledroid.core;

import fr.battledroid.core.utils.Point;

public enum Direction {
    LEFT, RIGHT, UP, DOWN, UP_LEFT, UP_RIGHT, DOWN_LEFT, DOWN_RIGHT, STAY;

    private static final int STAY_ = 0x00000000;
    private static final int LEFT_ = 0x00000001;
    private static final int RIGHT_ = 0x00000010;
    private static final int UP_ = 0x00000100;
    private static final int DOWN_ = 0x00001000;

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
            case UP_LEFT:
                return new Point(-1, -1);
            case UP_RIGHT:
                return new Point(1, -1);
            case DOWN_LEFT:
                return new Point(-1, 1);
            case DOWN_RIGHT:
                return new Point(1, 1);
        }
        return new Point(0,0);
    }

    public static Direction fromPoints(Point src, Point dst) {
        int dX = dst.x - src.x;
        int dY = dst.y - src.y;
        int dir = STAY_;

        if (dX < 0) {
            dir |= LEFT_;
        } else if (dX > 0) {
            dir |= RIGHT_;
        }
        if (dY < 0) {
            dir |= UP_;
        } else if (dY > 0) {
            dir |= DOWN_;
        }

        return fromInt(dir);
    }

    public static Direction fromInt(int dir) {
        if ((dir & UP_) != 0) {
            if ((dir & LEFT_) != 0) {
                return UP_LEFT;
            }
            if ((dir & RIGHT_) != 0) {
                return UP_RIGHT;
            }
            return UP;
        }
        if ((dir & DOWN_) != 0) {
            if ((dir & LEFT_) != 0) {
                return DOWN_LEFT;
            }
            if ((dir & RIGHT_) != 0) {
                return DOWN_RIGHT;
            }
            return DOWN;
        }
        if ((dir & LEFT_) != 0) {
            return LEFT;
        }
        if ((dir & RIGHT_) != 0) {
            return RIGHT;
        }
        return STAY;
    }
}
