package fr.slick.bridge;

import fr.battledroid.core.artifact.Artifact;
import fr.battledroid.core.engine.Engine;
import fr.battledroid.core.map.tile.Tile;
import fr.battledroid.core.player.Player;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;

public final class SysListener implements Engine.Listener {
    @Override
    public void onMove(Player player, Tile dst) {

    }

    @Override
    public void onShoot(Player player) {
        try {
            new Music("res/music/laser.ogg").play();
        } catch (SlickException ignored) {}
    }

    @Override
    public void onCollidePlayer(Player p1, Player p2) {

    }

    @Override
    public void onCollideArtifact(Player player, Artifact artifact) {

    }
}
