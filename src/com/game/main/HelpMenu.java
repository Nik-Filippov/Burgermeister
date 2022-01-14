package com.game.main;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class HelpMenu extends MouseAdapter {
    private final Game game;

    private final int backButtonXY, backButtonWH;
    private final int width, height;

    public HelpMenu(Game game, Dimension defaultSize) {
        this.game = game;

        width = (int) (defaultSize.getWidth());
        height = (int) (defaultSize.getHeight());
        backButtonXY = (int)(0.035 * width);
        backButtonWH = (int)(0.0555 * width);
    }

    public void mousePressed(MouseEvent e){
        int mx = e.getX();
        int my = e.getY();

        if(game.gameState == Game.STATE.Help) {
            if (mouseOver(mx, my, backButtonXY, backButtonXY, backButtonWH, backButtonWH)) {
                game.gameState = Game.STATE.MainMenu;
            }
        }
    }

    private boolean mouseOver(int mx, int my, int x, int y, int width, int height){
        if(mx > x &&  mx < x + width){
            if(my > y && my < y + height){
                return true;
            }
        }
        return false;
    }

    public void render(Graphics g) {
        //Back button
        Font font1 = new Font("arial", 1, 40);
        g.setColor(Color.WHITE);
        g.setFont(font1);

        g.setColor(Color.BLACK);
        g.fillOval(backButtonXY - backButtonXY / 10, backButtonXY + backButtonXY / 10, backButtonWH, backButtonWH);

        g.setColor(Color.WHITE);
        g.fillOval(backButtonXY, backButtonXY, backButtonWH, backButtonWH);

        g.setColor(Color.BLACK);
        g.drawOval(backButtonXY, backButtonXY, backButtonWH, backButtonWH);

        g.setColor(Color.BLACK);
        g.drawString("⬅", (backButtonXY + backButtonWH / 2 - Game.strLength(g, "⬅") / 2),
                (backButtonXY + backButtonWH / 2 + Game.strHeight(g) / 3));
        //Title
        g.drawString("About the game", width / 2 - Game.strLength(g, "About the game") / 2,
                (int)(0.15 * height));
    }
}
