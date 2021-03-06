package com.game.main;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;

public class Cutscene extends MouseAdapter {
    private Game game;
    private Dimension defaultSize;
    final long screenDuration = 6000;
    public static long timePassed = 0;
    public static long prevTime = 0;
    public static int dayNumber = 1, eventIndex = 0;
    public static ArrayList<CutsceneFrame> currentCutscenes = new ArrayList(2);
    public static int width, height, skipButtonWH, skipButtonX, skipButtonY;

    public Cutscene(Game game, Dimension defaultSize) {
        this.game = game;
        this.defaultSize = defaultSize;

        width = (int) (defaultSize.getWidth());
        height = (int) (defaultSize.getHeight());
        skipButtonX = (int)(0.9 * width);
        skipButtonY = (int)(0.8 * height);
        skipButtonWH = (int)(0.0555 * width);
    }


    public void mousePressed(MouseEvent e){
        int mx = e.getX();
        int my = e.getY();
        if(game.gameState == Game.STATE.Cutscene) {
            if(game.mouseOver(mx, my, skipButtonX, skipButtonY, skipButtonWH, skipButtonWH)){
                timePassed = screenDuration - 1;
            }
        }
    }

    public void render(Graphics g) throws IOException {
        if(game.gameState == Game.STATE.Cutscene) {
            long currentTime = System.currentTimeMillis();
            timePassed += currentTime - prevTime;
            prevTime = currentTime;
            //Render
            if (timePassed < screenDuration) {
                for (CutsceneFrame cf : currentCutscenes) {
                    if (timePassed >= cf.startTime || cf.startTime + cf.duration <= timePassed) {
                        cf.render(g);
                    }
                }
            }
            else{
                MapGame.currentDialogIndex = 0;
                MapGame.timePassed = 1;
                MapGame.prevTime = System.currentTimeMillis();
                game.gameState = Game.STATE.MyMap;
            }
        }
        //SKIP BUTTON
        Font font1 = new Font("arial", Font.BOLD, skipButtonX / 50);
        g.setColor(new Color(99, 52, 53));
        g.setFont(font1);

        g.setColor(Color.BLACK);
        g.fillOval(skipButtonX - 5, skipButtonY + 5, skipButtonWH, skipButtonWH);

        g.setColor(new Color(99, 52, 53));
        g.fillOval(skipButtonX, skipButtonY, skipButtonWH, skipButtonWH);

        g.setColor(Color.WHITE);
        g.drawString("???", skipButtonX + skipButtonWH / 2 - Game.strLength(g, "???") / 2,
                skipButtonY + skipButtonWH / 2 + Game.strHeight(g) / 3);
    }
}
