package fr.battledroid.core.artifact;

import fr.battledroid.core.asset.*;
import fr.battledroid.core.player.Player;
import fr.battledroid.core.utils.Points;

abstract class AbstractArtifact extends ImageAsset implements Artifact {
    public static final int DEFAULT_FIELD = 1;
    private final int field;

    AbstractArtifact(int field) {
        super(null, 0, 0, false);
        this.field = field;
    }

    @Override
    public abstract void onPick(Player player);

    @Override
    public boolean hasCollide(Point src, Point dst) {
        return Points.dist(src, dst) <= field;
    }
}
