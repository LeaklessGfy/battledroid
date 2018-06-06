package fr.battledroid.core.map.tile;

import fr.battledroid.core.Drawable;
import fr.battledroid.core.adaptee.Asset;
import fr.battledroid.core.adaptee.Canvas;
import fr.battledroid.core.utils.Point;
import fr.battledroid.core.utils.PointF;
import fr.battledroid.core.utils.Points;

public final class Tile implements Drawable {
    private final Point iso;
    private final PointF screen;

    private Context ctx;
    private Asset asset;

    public Tile(int x, int y, Asset asset) {
        this.iso = new Point(x, y);
        this.screen = Points.isoToScreen(iso);
        this.asset = asset;
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
        if (asset == null) {
            return;
        }
        asset.draw(canvas, screen.x + offset.x, screen.y + offset.y);
    }

    @Override
    public void drawMiniMap(Canvas canvas, PointF cellSize) {
        if (asset == null) {
            return;
        }
        float xOffset = (iso.x * cellSize.x) + 5;
        float yOffset = (iso.y * cellSize.y) + 5;
        canvas.drawRect(xOffset, yOffset, xOffset + cellSize.x, yOffset + cellSize.y, asset.getColor());
    }

    @Override
    public void tick() {
        if (asset != null) {
            asset.tick();
        }
    }

    public Point iso() {
        return new Point(iso);
    }

    public PointF screen() {
        return new PointF(screen);
    }

    public void translate(PointF screen, Context ctx, Asset asset) {
        synchronized (iso) {
            this.screen.set(screen);
            this.ctx = ctx;
            this.ctx.i++;
            this.asset = asset;
        }
    }

    public void nextStep() {
        synchronized (iso) {
            if (ctx.i == ctx.max / 2) {
                ctx.dst.translate(screen, ctx, asset);
                ctx.onChange.accept(ctx.dst);
                ctx = null;
                asset = null;
            } else if (ctx.i > ctx.max) {
                ctx.onArrive.accept(this);
                ctx = null;
            } else {
                screen.set(Points.step(screen, ctx.dir, ctx.max));
                ctx.i++;
            }
        }
    }

    public void move(Context context) {
        synchronized (iso) {
            this.ctx = context;
        }
    }

    public boolean isBusy() {
        return asset != null && asset.isObstacle();
    }

    public void setAsset(Asset asset) {
        this.asset = asset;
    }

    @Override
    public String toString() {
        return "Tile{" +
                "iso=" + iso +
                ", screen=" + screen +
                ", ctx=" + ctx +
                ", asset=" + asset +
                '}';
    }
}
