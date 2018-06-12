package fr.battledroid.core.engine;

import fr.battledroid.core.map.Map;

public final class EngineFactory {
    public static Engine create(Map map) {
        return new EngineImpl(map);
    }
}
