package fr.battledroid.core.map.tile;

import fr.battledroid.core.adaptee.Asset;
import fr.battledroid.core.adaptee.Canvas;
import fr.battledroid.core.utils.*;

public final class Tile {
    private final Point iso;
    private final PointF screen;

    private Asset background;
    private Asset overlay;

    public Tile(int x, int y, Asset background, Asset overlay) {
        this.iso = new Point(x, y);
        this.screen = Points.isoToScreen(iso);
        this.background = Utils.requireNonNull(background);
        this.overlay = overlay;
    }

    public void drawBackground(Canvas canvas, PointF offset) {
        if (background.shouldDraw(screen, offset, canvas.getSize())) {
            background.draw(canvas, screen, offset);
        }
    }

    public void drawOverlay(Canvas canvas, PointF offset) {
        if (overlay != null && overlay.shouldDraw(screen, offset, canvas.getSize())) {
            overlay.draw(canvas, screen, offset);
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
            return new HitBox(screen.x, screen.y, background.getWidth(), background.getHeight());
        }
        return new HitBox(screen.x, screen.y, overlay.getWidth(), overlay.getHeight());
    }

    public void setBackground(Asset background) {
        this.background = Utils.requireNonNull(background);
    }

    public void setOverlay(Asset overlay) {
        this.overlay = overlay;
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
