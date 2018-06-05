package fr.battledroid.core.asset;

import fr.battledroid.core.Utils;

public class ImageAsset implements Asset {
    protected final Image img;
    private final boolean obstacle;

    private int isoX;
    private int isoY;
    private float screenX;
    private float screenY;

    public ImageAsset(Image img, int x, int y, boolean obstacle) {
        this.img = Utils.requireNonNull(img);
        this.isoX = x;
        this.isoY = y;
        this.screenX = calculateX();
        this.screenY = calculateY();
        this.obstacle = obstacle;
    }

    @Override
    public boolean shouldDraw(PointF offset, int canvasWidth, int canvasHeight) {
        double x = screenX + offset.x;
        double y = screenY + offset.y;

        return x >= 0 - img.getWidth() && x <= canvasWidth && y >= 0 - img.getHeight() && y <= canvasHeight;
    }

    @Override
    public void draw(Canvas canvas, PointF offset) {
        canvas.drawImage(img.get(), screenX + offset.x, screenY + offset.y);
    }

    @Override
    public void drawMiniMap(Canvas canvas, float cellWidth, float cellHeight) {
        float xOffset = (isoX * cellWidth) + 5;
        float yOffset = (isoY * cellHeight) + 5;
        canvas.drawRect(xOffset, yOffset, xOffset + cellWidth, yOffset + cellHeight, null);
    }

    @Override
    public int width() {
        return img.getWidth();
    }

    @Override
    public int height() {
        return img.getHeight();
    }

    @Override
    public boolean isObstacle() {
        return obstacle;
    }

    @Override
    public void tick() {}

    @Override
    public Point iso() {
        return new Point(isoX, isoY);
    }

    @Override
    public PointF screen() {
        return new PointF(screenX, screenY);
    }

    @Override
    public void moveIso(Point point) {
        this.isoX = point.x;
        this.isoY = point.y;
        this.screenX = calculateX();
        this.screenY = calculateY();
    }

    @Override
    public void moveScreen(PointF point) {
        this.screenX = point.x;
        this.screenY = point.y;
    }

    private int calculateX() {
        return (this.isoX - this.isoY) * (img.getWidth() / 2) - diamondTopWidth();
    }

    private int calculateY() {
        return (this.isoX + this.isoY) * (diamondTopHeight() / 4) - img.getAlphaHeight();
    }

    private int diamondTopWidth() {
        return img.getWidth() / 2;
    }

    private int diamondTopHeight() {
        return img.getHeight() - img.getAlphaHeight();
    }

    private int computeAverageColor() {
        int pixels[] = img.getPixels();

        int red = 0;
        int green = 0;
        int blue = 0;
        int pixelCount = 0;

        for (int pixel : pixels) {
            /*if (Color.alpha(pixel) == 255) { //ignore transparent pixels
                red += (pixel >> 16) & 0xFF;
                green += (pixel >> 8) & 0xFF;
                blue += (pixel & 0xFF);
                pixelCount++;
            }*/
        }

        red /= pixelCount;
        green /= pixelCount;
        blue /= pixelCount;

        // Copy of Color.arbg which is in API 26
        return (255 << 24) | (red << 16) | (green << 8) | blue;
    }
}
