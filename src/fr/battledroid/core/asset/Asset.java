package fr.battledroid.core.asset;

public interface Asset {
    boolean shouldDraw(PointF offset, int canvasWidth, int canvasHeight);
    void draw(Canvas canvas, PointF offset);
    void drawMiniMap(Canvas canvas, float cellWidth, float cellHeight);
    int width();
    int height();
    boolean isObstacle();
    void tick();

    Point iso();
    PointF screen();

    void moveIso(Point point);
    void moveScreen(PointF point);
}
