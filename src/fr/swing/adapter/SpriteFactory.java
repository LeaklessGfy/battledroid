package fr.swing.adapter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Path;
import java.util.WeakHashMap;

public final class SpriteFactory {
    private final WeakHashMap<Path, Image> lazy;

    public SpriteFactory() {
        this.lazy = new WeakHashMap<>();
    }

    public AssetAdapter get(Path path) {
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
