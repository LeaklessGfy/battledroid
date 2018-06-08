package fr.battledroid.core.artifact;

import fr.battledroid.core.adaptee.Asset;
import fr.battledroid.core.adaptee.AssetWrapper;
import fr.battledroid.core.map.tile.Tile;
import fr.battledroid.core.player.Player;
import fr.battledroid.core.utils.Points;
import fr.battledroid.core.utils.Utils;

abstract class AbstractArtifact extends AssetWrapper implements Artifact {
    private static final int DEFAULT_FIELD = 1;
    private final int field;

    private Tile current;

    AbstractArtifact(Asset asset, int field) {
        super(asset);
        this.field = field;
    }

    AbstractArtifact(Asset asset) {
        this(asset, DEFAULT_FIELD);
    }

    @Override
    public Tile current() {
        return current;
    }

    @Override
    public void current(Tile current) {
        this.current = Utils.requireNonNull(current);
        this.current.setOverlay(this);
    }

    @Override
    public void resetCurrent() {
        this.current.setOverlay(null);
        this.current = null;
    }

    @Override
    public boolean hasCollide(Player player) {
        Tile pTile = player.current();
        int d = Points.dist(pTile.iso(), current.iso());
        if (d <= field) {
            return true;
        }
        return false;
    }

    @Override
    public abstract void onCollide(Player player);
}
