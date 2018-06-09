package fr.battledroid.core.map.tile;

import fr.battledroid.core.adaptee.Asset;
import fr.battledroid.core.adaptee.Canvas;
import fr.battledroid.core.map.tile.math.TileMath;
import fr.battledroid.core.utils.*;

public final class Tile {
    public final int x;
    public final int y;
    private final Point iso;

    private final TileMath math;
    private final PointF screenBackground;
    private final PointF screenOverlay;

    private Asset background;
    private Asset overlay;

    public Tile(int x, int y, TileMath math) {
        this.x = x;
        this.y = y;
        this.iso = new Point(x, y);
        this.math = Utils.requireNonNull(math);
        this.screenBackground = new PointF();
        this.screenOverlay = new PointF();
    }

    public void drawBackground(Canvas canvas, PointF offset) {
        if (background.shouldDraw(screenBackground, offset, canvas.getSize())) {
            background.draw(canvas, screenBackground, offset);
        }
    }

    public void drawOverlay(Canvas canvas, PointF offset) {
        if (overlay != null && overlay.shouldDraw(screenOverlay, offset, canvas.getSize())) {
            overlay.draw(canvas, screenOverlay, offset);
        }
    }

    public void drawMiniMap(Canvas canvas, PointF cellSize) {
        float xOffset = (iso.x * cellSize.x) + 5;
        float yOffset = (iso.y * cellSize.y) + 5;
        canvas.drawRect(xOffset, yOffset, xOffset + cellSize.x, yOffset + cellSize.y, background.getColor());
    }

    public void tick() {
        if (overlay != null) {
            overlay.tick();
        }
    }

    public Point iso() {
        return new Point(iso);
    }

    public boolean isBusy() {
        return overlay != null;
    }

    public HitBox hitBox() {
        if (overlay == null) {
            return new HitBox(screenBackground.x, screenBackground.y, background.getWidth(), background.getHeight());
        }
        return new HitBox(screenOverlay.x, screenOverlay.y, overlay.getWidth(), overlay.getHeight());
    }

    public void setBackground(Asset background) {
        this.background = Utils.requireNonNull(background);
        this.screenBackground.set(math.isoToScreen(this, background));
    }

    public void setOverlay(Asset overlay) {
        this.overlay = overlay;
        if (overlay != null) {
            this.screenOverlay.set(screenBackground.clone().offset(overlay.getOffset()));
        }
    }

    public PointF moveTo(Tile dst) {
        PointF s = screenBackground;
        PointF d = dst.screenBackground;

        return new PointF(d.x - s.x, d.y - s.y);
    }

    public PointF getScreenBackground() {
        return screenBackground.clone();
    }

    public PointF getScreenOverlay() {
        return screenOverlay.clone();
    }

    @Override
    public String toString() {
        return "Tile{" +
                "iso=" + iso +
                ", background=" + background +
                ", overlay=" + overlay +
                '}';
    }
}
