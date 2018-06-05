package fr.battledroid.core.engine;

import fr.battledroid.core.artifact.Artifact;
import fr.battledroid.core.asset.Canvas;
import fr.battledroid.core.asset.Point;
import fr.battledroid.core.asset.PointF;
import fr.battledroid.core.player.behaviour.AIMoveBehaviour;
import fr.battledroid.core.player.Player;

public interface Engine {
    interface Listener {
        void onMove(Player player, Point dst);
        void onCollidePlayer(Player p1, Player p2);
        void onCollideArtifact(Player player, Artifact artifact);
    }

    void draw(Canvas canvas, PointF offset);
    void drawMiniMap(Canvas canvas);
    void tick();

    void setListener(Listener listener);
    void setBehaviour(AIMoveBehaviour behaviour);

    //boolean move(final Player player, double x, double y, final Consumer<Tile> onArrive);
}
