package fr.battledroid.core;

import fr.battledroid.core.adaptee.Asset;
import fr.battledroid.core.adaptee.Canvas;
import fr.battledroid.core.player.State;
import fr.battledroid.core.utils.Point;
import fr.battledroid.core.utils.PointF;
import fr.battledroid.core.utils.Points;

import java.util.concurrent.LinkedBlockingDeque;

public class Tile implements Position, Drawable {
    private final Point iso;
    private final PointF screen;
    private final LinkedBlockingDeque<Position> moves;

    private Asset asset;
    private State state;
    private Context ctx;

    public Tile(int x, int y, Asset asset) {
        this.iso = new Point(x, y);
        this.screen = new PointF();
        this.moves = new LinkedBlockingDeque<>();
        this.asset = asset;
        this.state = State.WAITING;
        updateScreen();
    }

    public Tile(int x, int y) {
        this(x, y, null);
    }

    @Override
    public Point iso() {
        return new Point(iso);
    }

    @Override
    public PointF screen() {
        return new PointF(screen);
    }

    @Override
    public void move(Position position) {
        moves.offer(position);
    }

    @Override
    public boolean isBusy() {
        return asset != null && asset.isObstacle();
    }

    @Override
    public void setAsset(Asset asset) {
        this.asset = asset;
        updateScreen();
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
            asset.draw(canvas, screen.x + offset.x, screen.y + offset.y);
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
                    nextStep();
                    break;
            }
        }
    }

    private void updateScreen() {
        if (asset != null) {
            screen.set(Points.isoToScreen(iso));
        } else {
            screen.set(0, 0);
        }
    }

    private void nextMove() {
        Position dst = moves.poll();
        if (dst == null) {
            return;
        }
        PointF nScreen = Points.isoToScreen(dst.iso());
        state = State.MOVING;
        ctx = new Context();
        ctx.dst = dst;
        ctx.iteration = 0;
        ctx.max = 10;
        ctx.dir = new PointF(nScreen.x - screen.x, nScreen.y - screen.y);
    }

    private void nextStep() {
        if (ctx.iteration == ctx.max / 2) {
            state = State.WAITING;
            ctx.dst.translate(asset, screen, ctx);
            asset = null;
        } else if (ctx.iteration == ctx.max) {
            state = State.WAITING;
            nextMove();
        } else {
            float x = screen.x + (ctx.dir.x / ctx.max);
            float y = screen.y + (ctx.dir.y / ctx.max);
            screen.set(x, y);
            ctx.iteration++;
        }
    }

    @Override
    public void translate(Asset asset, PointF screen, Context context) {
        synchronized (moves) {
            this.state = State.MOVING;
            this.asset = asset;
            this.screen.set(screen);
            this.ctx = context;
            this.ctx.iteration++;
        }
    }
}
