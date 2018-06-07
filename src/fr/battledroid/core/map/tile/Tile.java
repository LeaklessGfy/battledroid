package fr.battledroid.core.map.tile;

import fr.battledroid.core.adaptee.Asset;
import fr.battledroid.core.adaptee.Canvas;
import fr.battledroid.core.utils.Point;
import fr.battledroid.core.utils.PointF;
import fr.battledroid.core.utils.Points;
import fr.battledroid.core.utils.Utils;

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
        insureValidity();
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

    public void setBackground(Asset background) {
        this.background = Utils.requireNonNull(background);
        insureValidity();
    }

    public void setOverlay(Asset overlay) {
        this.overlay = overlay;
        insureValidity();
    }

    private void insureValidity() {
        background.setCurrent(this);
        if (overlay != null) {
            overlay.setCurrent(this);
        }
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
