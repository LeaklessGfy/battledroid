package fr.swing.adapter;

import fr.battledroid.core.adaptee.Asset;
import fr.battledroid.core.adaptee.SpriteFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Path;
import java.util.WeakHashMap;

public final class SwingSpriteFactory implements SpriteFactory {
    private final WeakHashMap<Path, Image> lazy;

    public SwingSpriteFactory() {
        this.lazy = new WeakHashMap<>();
    }

    @Override
    public Asset get(Path path) {
        Image img = lazyLoad(path);
        return AssetAdapter.create(img);
    }

    private Image lazyLoad(Path path) {
        return lazy.computeIfAbsent(path, p -> {
            try {
                return load(p);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private Image load(Path path) throws IOException {
        return ImageIO.read(path.toFile());
    }
}
