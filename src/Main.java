import fr.battledroid.core.adaptee.AssetFactory;
import fr.battledroid.core.player.Player;
import fr.battledroid.core.player.PlayerFactory;
import fr.swing.adapter.CanvasAdapter;
import fr.swing.adapter.ColorAdapter;
import fr.battledroid.core.engine.Engine;
import fr.battledroid.core.engine.EngineFactory;
import fr.battledroid.core.engine.ViewContext;
import fr.battledroid.core.map.Map;
import fr.battledroid.core.map.MapFactory;
import fr.swing.adapter.SwingSpriteFactory;
import fr.swing.ui.Window;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Main {
    public static void main(String[] args) {
        SwingSpriteFactory spriteFactory = new SwingSpriteFactory();
        AssetFactory assetFactory = new AssetFactory(spriteFactory);

        Map map = MapFactory.createRandom(assetFactory);
        Player player = PlayerFactory.createDroid(assetFactory);

        Engine engine = EngineFactory.create(map, new ColorAdapter(Color.BLACK));
        engine.addPlayer(player);

        ViewContext context = new ViewContext(engine, player);

        GraphicsConfiguration config = GraphicsEnvironment.getLocalGraphicsEnvironment()
                        .getDefaultScreenDevice()
                        .getDefaultConfiguration();

        BufferedImage buff = config.createCompatibleImage(1000, 700, Transparency.TRANSLUCENT);
        CanvasAdapter adapter = new CanvasAdapter((Graphics2D) buff.getGraphics());

        Window w = new Window(1000, 700);
        Canvas canvas = new Canvas(config);
        w.add(canvas, 0);
        w.start();

        canvas.createBufferStrategy(2);
        BufferStrategy strategy = canvas.getBufferStrategy();
        Graphics2D g = (Graphics2D) strategy.getDrawGraphics();


        //View view = new View(context);
        //w.setContentPane(view);

        //view.setDoubleBuffered(true);
        //RepaintManager.currentManager(w).setDoubleBufferingEnabled(true);

        Thread fps = new Thread(() -> {
            while (!Thread.interrupted()) {
                try {
                    engine.tick();
                    //view.repaint();
                    g.drawImage(buff, 0, 0, null);
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
