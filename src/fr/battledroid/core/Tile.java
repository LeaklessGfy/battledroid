package fr.battledroid.core;

import fr.battledroid.core.adaptee.Asset;
import fr.battledroid.core.adaptee.Canvas;
import fr.battledroid.core.player.State;
import fr.battledroid.core.utils.Points;

import java.util.concurrent.LinkedBlockingDeque;

public class Tile implements Position, Drawable {
    private final LinkedBlockingDeque<Position> moves;
    private final Point iso;
    private final PointF screen;
    private final PointF direction;

    private Asset asset;
    private State state = State.WAITING;
    private Position current;
    private int iteration;
    private int speed = 10;

    public Tile(int x, int y, Asset asset) {
        this.asset = asset;
        this.moves = new LinkedBlockingDeque<>();
        this.iso = new Point(x, y);
        this.screen = asset != null ? Points.isoToScreen(this.iso, this.asset) : new PointF();
        this.direction = new PointF();
    }

    @Override
    public Point iso() {
        return iso;
    }

    @Override
    public PointF screen() {
        return screen;
    }

    @Override
    public void postMove(Position position) {
        moves.offer(position);
    }

    @Override
    public boolean isBusy() {
        return asset != null && asset.isObstacle();
    }

    @Override
    public void setAsset(Asset asset) {
        this.asset = asset;
        if (asset != null) {
            this.screen.set(Points.isoToScreen(this.iso, this.asset));
        } else {
            this.screen.set(0, 0);
        }
    }

    @Override
    public void translate(Asset asset, PointF screen, PointF direction) {
        synchronized (moves) {
            this.asset = asset;
            this.screen.set(screen);
            this.direction.set(direction);
            this.iteration = (speed / 2) + 1;
            this.state = State.MOVING;
        }
    }

    @Override
    public boolean shouldDraw(PointF offset, Point canvasSize) {
        if (asset == null) {
            return false;
        }

        double x = screen.x + offset.x;
        double y = screen.y + offset.y;

        return x >= 0 - asset.getWidth() && x <= canvasSize.x && y >= 0 - asset.getHeight() && y <= canvasSize.y;
    }

    @Override
    public void drawMap(Canvas canvas, PointF offset) {
        if (asset != null) {
            canvas.drawAsset(asset, screen.x + offset.x, screen.y + offset.y);
        }
    }

    @Override
    public void drawMiniMap(Canvas canvas, PointF cellSize) {
        float xOffset = (iso.x * cellSize.x) + 5;
        float yOffset = (iso.y * cellSize.y) + 5;
        canvas.drawRect(xOffset, yOffset, xOffset + cellSize.x, yOffset + cellSize.y, asset.getColor());
    }

    @Override
    public void tick() {
        synchronized (moves) {
            switch (state) {
                case WAITING:
                    nextMove();
                    break;
                case MOVING:
                    step();
                    break;
            }
        }
    }

    private void nextMove() {
        current = moves.poll();
        if (current == null) {
            return;
        }
        PointF dst = Points.isoToScreen(current.iso(), asset);
        state = State.MOVING;
        direction.set(dst.x - screen.x, dst.y - screen.y);
        System.out.println(direction);
        iteration = 0;
    }

    private void step() {
        if (iteration == speed / 2) {
            current.translate(asset, screen, direction);
            state = State.WAITING;
            asset = null;
            return;
        } else if (iteration >= speed) {
            state = State.WAITING;
            nextMove();
            return;
        }

        float x = screen.x + (direction.x / speed);
        float y = screen.y + (direction.y / speed);
        screen.set(x, y);
        iteration++;
    }
}
