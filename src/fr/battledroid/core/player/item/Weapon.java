package fr.battledroid.core.player.item;

import fr.battledroid.core.particle.Particle;
import fr.battledroid.core.player.Player;
import fr.battledroid.core.utils.Point;
import fr.battledroid.core.utils.PointF;

public interface Weapon {
    Particle shoot(Point iso, PointF screen, Point offset, Player owner);
}
