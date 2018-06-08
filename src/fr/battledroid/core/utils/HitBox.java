package fr.battledroid.core.utils;

public final class HitBox {
    public final float x;
    public final float y;
    public final float width;
    public final float height;

    public HitBox(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public boolean intersect(HitBox hit) {
        if (x < hit.x + hit.width && x + width > hit.x
                && y < hit.y + hit.height && y + height > hit.y) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "HitBox{" +
                "x=" + x +
                ", y=" + y +
                ", width=" + width +
                ", height=" + height +
                '}';
    }
}
