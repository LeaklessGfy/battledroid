import fr.battledroid.core.player.Player;
import fr.battledroid.core.player.PlayerFactory;
import fr.swing.adapter.AssetFactoryAdapter;
import fr.swing.adapter.ColorAdapter;
import fr.battledroid.core.Settings;
import fr.battledroid.core.engine.Engine;
import fr.battledroid.core.engine.EngineFactory;
import fr.battledroid.core.engine.ViewContext;
import fr.battledroid.core.map.Map;
import fr.battledroid.core.map.MapFactory;
import fr.swing.adapter.SpriteFactory;
import fr.swing.ui.View;
import fr.swing.ui.Window;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main {
    public static void main(String[] args) {
        Settings settings = new Settings();
        SpriteFactory spriteFactory = new SpriteFactory(settings);
        AssetFactoryAdapter assetFactory = AssetFactoryAdapter.create(spriteFactory, settings);

        Map map = MapFactory.createRandom(assetFactory, settings);
        Player player = PlayerFactory.createDroid(spriteFactory);

        Engine engine = EngineFactory.create(map, new ColorAdapter(Color.BLACK));
        ViewContext context = new ViewContext(engine, player);

        engine.addPlayer(player);

        View view = new View(context);
        Window w = new Window(1000, 700);
        w.setContentPane(view);
        w.start();

        Thread fps = new Thread(() -> {
            while (!Thread.interrupted()) {
                try {
                    engine.tick();
                    view.repaint();
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    return;
                }
            }
        });
        fps.start();

        w.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                e.getWindow().dispose();
                fps.interrupt();
            }
        });
    }
}
