package fr.battledroid.core.engine;

import fr.battledroid.core.Point;
import fr.battledroid.core.Position;
import fr.battledroid.core.artifact.Artifact;
import fr.battledroid.core.adaptee.Canvas;
import fr.battledroid.core.PointF;
import fr.battledroid.core.player.behaviour.AIMoveBehaviour;
import fr.battledroid.core.player.Player;

public interface Engine {
    interface Listener {
        void onMove(Player player, Position dst);
        void onCollidePlayer(Player p1, Player p2);
        void onCollideArtifact(Player player, Artifact artifact);
    }

    void addPlayer(Player player);

    void drawMap(Canvas canvas, PointF offset);
    void drawMiniMap(Canvas canvas);
    void tick();

    void move(Player player, Point point);
    void move(Player player, Position dst);
    Position findPosition(double x, double y);

    void setListener(Listener listener);
    void setBehaviour(AIMoveBehaviour behaviour);
}
