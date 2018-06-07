package fr.battledroid.core.engine;

import fr.battledroid.core.adaptee.AssetColor;
import fr.battledroid.core.map.Map;

public final class EngineFactory {
    public static Engine create(Map map, AssetColor color) {
        return new EngineImpl(map, color);
    }
}
