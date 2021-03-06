package fr.slick.adapter;

import fr.battledroid.core.adaptee.Asset;
import fr.battledroid.core.adaptee.SpriteFactory;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import java.nio.file.Path;
import java.util.WeakHashMap;

public final class SlickSpriteFactory implements SpriteFactory {
    private final WeakHashMap<Path, Asset> lazy;

    public SlickSpriteFactory() {
        this.lazy = new WeakHashMap<>();
    }

    @Override
    public Asset get(Path path) {
        return lazyLoad(path);
    }

    private Asset lazyLoad(Path path) {
        return lazy.computeIfAbsent(path, p -> {
            try {
                return AssetAdapter.create(load(p));
            } catch (SlickException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private Image load(Path path) throws SlickException {
        return new Image(path.toString());
    }
}
