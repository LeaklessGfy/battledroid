package fr.battledroid.core.player.item;

import fr.battledroid.core.Direction;
import fr.battledroid.core.map.tile.Tile;
import fr.battledroid.core.particle.Particle;
import fr.battledroid.core.player.Player;

public interface Weapon {
    Particle shoot(Tile src, Direction direction, Player owner);
}
