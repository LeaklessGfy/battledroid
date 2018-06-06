package fr.battledroid.core.particle;

import fr.battledroid.core.Collider;
import fr.battledroid.core.Drawable;
import fr.battledroid.core.adaptee.Asset;

public interface Particle extends Drawable, Collider<Asset> {
    boolean hasEnd();
}
