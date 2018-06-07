package fr.battledroid.core.artifact;

import fr.battledroid.core.adaptee.Asset;
import fr.battledroid.core.adapter.AssetWrapper;
import fr.battledroid.core.player.Player;

abstract class AbstractArtifact extends AssetWrapper implements Artifact {
    private static final int DEFAULT_FIELD = 1;
    private final int field;

    AbstractArtifact(Asset asset, int field) {
        super(asset);
        this.field = field;
    }

    AbstractArtifact(Asset asset) {
        this(asset, DEFAULT_FIELD);
    }

    @Override
    public abstract void onPick(Player player);
}
