package com.game.main;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class Popup extends MouseAdapter {
    private Game game;
    private Dimension defaultSize;
    final long screenDuration = 6000;
    public static long timePassed = 0;
    public static long prevTime = 0;

    public Popup(Game game, Dimension defaultSize) {
        this.game = game;
        this.defaultSize = defaultSize;
    }


    public void mousePressed(MouseEvent e){
        int mx = e.getX();
        int my = e.getY();
        if(game.gameState == Game.STATE.Popup) {

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

    public void render(Graphics g) throws IOException {
        if(game.gameState == Game.STATE.Cutscene) {
            
        }
    }

    public void tick(){

    }
}
