package com.game.main;

import javax.swing.*;
import java.awt.*;

public class Window extends Canvas {

    private static final long serialVersionUID = -2043341771866813498L;

    JFrame frame;

    public Window(Dimension d, String title, Game game) {
        frame = new JFrame(title);

        frame.setPreferredSize(d);
        frame.setMaximumSize(new Dimension((int)d.getWidth() * 4, (int)d.getHeight() * 4));
        frame.setMinimumSize(new Dimension((int)d.getWidth() / 4, (int)d.getHeight() / 4));
        frame.setSize(d);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.setLocationRelativeTo(null);
        frame.add(game);
        frame.setVisible(true);


        game.start();
    }

    public int getWidth() {
        return super.getWidth();
    }

    public int getHeight() {
        return super.getHeight();
    }

    public JFrame getFrame() {
        return frame;
    }
}
