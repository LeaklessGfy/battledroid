package fr.battledroid.core.engine;

import fr.battledroid.core.asset.Color;
import fr.battledroid.core.map.Map;

public final class EngineFactory {
    public static Engine create(Map map, Color color) {
        return new EngineImpl(map, color);
    }
}
