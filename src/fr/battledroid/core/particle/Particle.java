package fr.battledroid.core.particle;

import fr.battledroid.core.Collider;
import fr.battledroid.core.Ticker;
import fr.battledroid.core.drawable.MasterDrawable;
import fr.battledroid.core.player.Player;

public interface Particle extends MasterDrawable, Ticker, Collider<Player> {
    boolean hasEnd();
}
