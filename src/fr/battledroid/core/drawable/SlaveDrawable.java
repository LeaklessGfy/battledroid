package fr.battledroid.core.drawable;

import fr.battledroid.core.adaptee.Canvas;
import fr.battledroid.core.utils.Point;
import fr.battledroid.core.utils.PointF;

public interface SlaveDrawable {
    boolean shouldDraw(PointF screen, PointF offset, Point canvasSize);
    void draw(Canvas canvas, PointF screen, PointF offset);
}
