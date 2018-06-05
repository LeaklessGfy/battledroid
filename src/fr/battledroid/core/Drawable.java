package fr.battledroid.core;

import fr.battledroid.core.adaptee.Canvas;

public interface Drawable {
    boolean shouldDraw(PointF offset, Point canvasSize);
    void drawMap(Canvas canvas, PointF offset);
    void drawMiniMap(Canvas canvas, PointF cellSize);
    void tick();
}
