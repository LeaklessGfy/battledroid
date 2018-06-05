package fr.battledroid.core.artifact;

import fr.battledroid.core.adaptee.Asset;
import fr.battledroid.core.player.Player;

public interface Artifact extends Asset {
    void onPick(Player player);
}
