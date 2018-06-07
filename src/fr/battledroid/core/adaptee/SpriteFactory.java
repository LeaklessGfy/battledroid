package fr.battledroid.core.adaptee;

import java.nio.file.Path;

public interface SpriteFactory {
    Asset get(Path path);
}
