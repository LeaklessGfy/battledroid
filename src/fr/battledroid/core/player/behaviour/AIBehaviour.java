package fr.battledroid.core.player.behaviour;

import fr.battledroid.core.engine.Engine;
import fr.battledroid.core.map.Map;
import fr.battledroid.core.player.Player;

public interface AIBehaviour {
    void behave(Player ai, Engine engine, Map map);
}
