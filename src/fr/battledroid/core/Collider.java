package fr.battledroid.core;

public interface Collider<T> {
    boolean hasCollide(T src, T dst);
}
