package fr.swing.ui;

import fr.battledroid.core.engine.Direction;
import fr.swing.adapter.CanvasAdapter;
import fr.battledroid.core.engine.ViewContext;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Objects;

public final class View extends JPanel {
    private final ViewContext context;
    private final CanvasAdapter adapter;

    private Point pt = new Point();

    public View(ViewContext context) {
        this.context = Objects.requireNonNull(context);
        this.adapter = new CanvasAdapter(this);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                pt.setLocation(e.getPoint());
            }
        });
        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int dx = e.getX() - pt.x;
                int dy = e.getY() - pt.y;
                pt.setLocation(e.getPoint());
                context.offset(-dx, -dy);
                View.this.repaint();
            }
        });
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                switch (key) {
                    case KeyEvent.VK_LEFT:
                        context.move(Direction.LEFT);
                        break;
                    case KeyEvent.VK_RIGHT:
                        context.move(Direction.RIGHT);
                        break;
                    case KeyEvent.VK_UP:
                        context.move(Direction.UP);
                        break;
                    case KeyEvent.VK_DOWN:
                        context.move(Direction.DOWN);
                        break;
                }
                repaint();
            }
        });
        setFocusable(true);
    }

    @Override
    public void update(Graphics g) {
        context.draw(adapter.init(g));
    }

    @Override
    public void paint(Graphics g) {
        context.draw(adapter.init(g));
    }
}
