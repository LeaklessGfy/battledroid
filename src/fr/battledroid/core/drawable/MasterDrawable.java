package fr.battledroid.core.drawable;

import fr.battledroid.core.adaptee.Canvas;
import fr.battledroid.core.utils.PointF;

public interface MasterDrawable {
    void draw(Canvas canvas, PointF offset);
}
