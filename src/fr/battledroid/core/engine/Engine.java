package fr.battledroid.core.engine;

import fr.battledroid.core.Direction;
import fr.battledroid.core.artifact.ArtifactFactory;
import fr.battledroid.core.function.Consumer;
import fr.battledroid.core.map.tile.Tile;
import fr.battledroid.core.utils.Point;
import fr.battledroid.core.artifact.Artifact;
import fr.battledroid.core.adaptee.Canvas;
import fr.battledroid.core.utils.PointF;
import fr.battledroid.core.player.behaviour.AIBehaviour;
import fr.battledroid.core.player.Player;

public interface Engine {
    interface Listener {
        void onMove(Player player, Tile dst);
        void onShoot(Player player);
        void onCollidePlayer(Player p1, Player p2);
        void onCollideArtifact(Player player, Artifact artifact);
    }

    void addHuman(Player player);
    void addMonster(Player player);
    void generateArtifact(ArtifactFactory factory);

    void drawMap(Canvas canvas, PointF offset);
    void drawMiniMap(Canvas canvas);
    void tick();

    void move(Player player, Point point, Consumer<Tile> onArrive);
    void move(Player player, Tile tile, Consumer<Tile> onArrive);
    Tile find(double x, double y);

    void shoot(Player player, Direction direction);

    void setListener(Listener listener);
    void setBehaviour(AIBehaviour behaviour);
}
