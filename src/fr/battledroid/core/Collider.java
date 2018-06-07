package fr.battledroid.core;

public interface Collider<T> {
    boolean hasCollide(T dst);
    void onCollide(T dst);
}
