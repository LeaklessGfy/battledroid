package fr.battledroid.core.engine;

import fr.battledroid.core.Utils;
import fr.battledroid.core.asset.Canvas;
import fr.battledroid.core.asset.Color;
import fr.battledroid.core.asset.PointF;
import fr.battledroid.core.map.Map;
import fr.battledroid.core.player.behaviour.AIMoveBehaviour;

final class EngineImpl implements Engine {
    private final Map map;
    private final Color background;

    private Listener listener;
    private AIMoveBehaviour behaviour;

    EngineImpl(Map map, Color background) {
        this.map = Utils.requireNonNull(map);
        this.background = Utils.requireNonNull(background);
    }

    @Override
    public void draw(Canvas canvas, PointF offset) {
        canvas.drawColor(background);
        map.draw(canvas, offset);
    }

    @Override
    public void drawMiniMap(Canvas canvas) {
        canvas.drawColor(background);
        map.drawMiniMap(canvas);
    }

    @Override
    public void tick() {
        map.tick();
    }

    @Override
    public void setListener(Engine.Listener listener) {
        this.listener = Utils.requireNonNull(listener);
    }

    @Override
    public void setBehaviour(AIMoveBehaviour behaviour) {
        this.behaviour = Utils.requireNonNull(behaviour);
    }
}
