package fr;

import fr.battledroid.core.Direction;
import fr.battledroid.core.engine.Engine;
import fr.battledroid.core.engine.EngineFactory;
import fr.battledroid.core.engine.ViewContext;
import fr.battledroid.core.map.Map;
import fr.battledroid.core.map.MapFactory;
import fr.battledroid.core.player.Player;
import fr.battledroid.core.player.PlayerFactory;
import fr.swing.adapter.AssetFactoryAdapter;
import fr.swing.adapter.CanvasAdapter;
import fr.swing.adapter.ColorAdapter;
import fr.swing.adapter.SpriteFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Test extends Thread {
    private final Engine engine;
    private final ViewContext context;

    private boolean isRunning = true;
    private Canvas canvas;
    private BufferStrategy strategy;
    private BufferedImage background;
    private Graphics2D backgroundGraphics;
    private Graphics2D graphics;
    private JFrame frame;
    private int width = 1000;
    private int height = 700;
    private int scale = 1;
    private GraphicsConfiguration config = GraphicsEnvironment.getLocalGraphicsEnvironment()
                    .getDefaultScreenDevice()
                    .getDefaultConfiguration();

    // create a hardware accelerated image
    public final BufferedImage create(final int width, final int height,
                                      final boolean alpha) {
        return config.createCompatibleImage(width, height, alpha
                ? Transparency.TRANSLUCENT : Transparency.OPAQUE);
    }

    public Test() {
        SpriteFactory spriteFactory = new SpriteFactory();
        AssetFactoryAdapter assetFactory = AssetFactoryAdapter.create(spriteFactory);

        Map map = MapFactory.createRandom(assetFactory);
        Player player = PlayerFactory.createDroid(assetFactory);

        engine = EngineFactory.create(map, new ColorAdapter(Color.BLACK));
        engine.addPlayer(player);

        context = new ViewContext(engine, player);

        // JFrame
        frame = new JFrame();
        frame.addWindowListener(new FrameClose());
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.setSize(width * scale, height * scale);
        frame.setVisible(true);

        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                switch (key) {
                    case KeyEvent.VK_Q:
                        context.move(Direction.LEFT);
                        break;
                    case KeyEvent.VK_D:
                        context.move(Direction.RIGHT);
                        break;
                    case KeyEvent.VK_Z:
                        context.move(Direction.UP);
                        break;
                    case KeyEvent.VK_S:
                        context.move(Direction.DOWN);
                        break;
                    case KeyEvent.VK_LEFT:
                        context.shoot(Direction.LEFT);
                        break;
                    case KeyEvent.VK_RIGHT:
                        context.shoot(Direction.RIGHT);
                        break;
                    case KeyEvent.VK_UP:
                        context.shoot(Direction.UP);
                        break;
                    case KeyEvent.VK_DOWN:
                        context.shoot(Direction.DOWN);
                        break;
                }
            }
        });

        // Canvas
        canvas = new Canvas(config);
        canvas.setSize(width * scale, height * scale);
        frame.add(canvas, 0);

        // Background & Buffer
        background = create(width, height, false);
        canvas.createBufferStrategy(2);

        do {
            strategy = canvas.getBufferStrategy();
        } while (strategy == null);
        start();
    }

    private class FrameClose extends WindowAdapter {
        @Override
        public void windowClosing(final WindowEvent e) {
            isRunning = false;
        }
    }

    // Screen and buffer stuff
    private Graphics2D getBuffer() {
        if (graphics == null) {
            try {
                graphics = (Graphics2D) strategy.getDrawGraphics();
            } catch (IllegalStateException e) {
                return null;
            }
        }
        return graphics;
    }

    private boolean updateScreen() {
        graphics.dispose();
        graphics = null;

        try {
            strategy.show();
            Toolkit.getDefaultToolkit().sync();
            return (!strategy.contentsLost());
        } catch (NullPointerException | IllegalStateException e) {
            return true;
        }
    }

    public void run() {
        backgroundGraphics = (Graphics2D) background.getGraphics();
        long fpsWait = (long) (1.0 / 30 * 1000);

        main: while (isRunning) {
            long renderStart = System.nanoTime();
            updateGame();

            // Update Graphics
            do {
                Graphics2D bg = getBuffer();
                if (!isRunning) {
                    break main;
                }
                renderGame(backgroundGraphics); // this calls your draw method
                // thingy
                bg.drawImage(background, 0, 0, null);
                bg.dispose();
            } while (!updateScreen());

            // Better do some FPS limiting here
            long renderTime = (System.nanoTime() - renderStart) / 1000000;
            try {
                Thread.sleep(Math.max(0, fpsWait - renderTime));
            } catch (InterruptedException e) {
                Thread.interrupted();
                break;
            }
            renderTime = (System.nanoTime() - renderStart) / 1000000;
        }
        frame.dispose();
    }

    public void updateGame() {
        engine.tick();
    }

    public void renderGame(Graphics2D g) {
        context.draw(new CanvasAdapter(g));
    }

    public static void main(final String args[]) {
        new Test();
    }
}