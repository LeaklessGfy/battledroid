package fr.swing.ui;

import javax.swing.*;

public final class Window extends JFrame {
    public Window(int width, int height) {
        setTitle("Battledroid");
        setSize(width, height);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    public void start() {
        setVisible(true);
    }
}
