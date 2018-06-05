package fr.swing.adapter;

import fr.battledroid.core.Settings;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Objects;
import java.util.WeakHashMap;

public final class SpriteFactory {
    private final Settings settings;
    private final WeakHashMap<Path, Image> lazy;

    public SpriteFactory(Settings settings) {
        this.settings = Objects.requireNonNull(settings);
        this.lazy = new WeakHashMap<>();
    }

    public ImageAdapter get(Path path) {
        Image img = lazyLoad(path);
        return new ImageAdapter(img, settings);
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
