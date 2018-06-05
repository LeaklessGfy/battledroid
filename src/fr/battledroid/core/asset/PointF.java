package fr.battledroid.core.asset;

public class PointF {
    public float x;
    public float y;

    public PointF() {}

    public PointF(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public PointF set(float x, float y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public PointF set(PointF p) {
        this.x = p.x;
        this.y = p.y;
        return this;
    }

    public PointF offset(float x, float y) {
        this.x += x;
        this.y += y;
        return this;
    }

    public PointF offset(PointF p) {
        this.x += p.x;
        this.y += p.y;
        return this;
    }

    @Override
    public String toString() {
        return x + ", " + y;
    }
}
