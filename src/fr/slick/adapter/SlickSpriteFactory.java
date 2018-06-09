package fr.slick.adapter;

import fr.battledroid.core.adaptee.Asset;
import fr.battledroid.core.adaptee.AssetInfo;
import fr.battledroid.core.adaptee.SpriteFactory;

import java.util.WeakHashMap;

public final class SlickSpriteFactory implements SpriteFactory {
    private final WeakHashMap<AssetInfo, Asset> lazy;
    private final int scale;

    public SlickSpriteFactory(int scale) {
        this.lazy = new WeakHashMap<>();
        this.scale = scale;
    }

    @Override
    public Asset get(AssetInfo info) {
        return lazyLoad(info);
    }

    private Asset lazyLoad(AssetInfo info) {
        return lazy.computeIfAbsent(info, i -> AssetAdapter.create(scale, i));
    }
}
