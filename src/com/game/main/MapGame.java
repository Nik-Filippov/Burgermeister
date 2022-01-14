package com.game.main;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;


public class MapGame extends MouseAdapter {
    private static Game game;
    private static Dimension defaultSize;

    public static int[] savedDayMetrics = new int[4];

    public static int Berlin = 0, FRG = 0, GDR = 0, MARKS; //B, F, G, M in dialogs

    public static ArrayList<GameIcon> activeIcons = new ArrayList<>();

    //5 min -> 60000 millis -> Default session length

    private final long screenDuration = 8000;
    public static long timePassed = 0;
    public static long prevTime;

    public static int dayNumber = 1;

    public static int currentDialogIndex = -1;

    int pauseButtonX,pauseButtonY, pauseButtonWH, width, height;

    public static ArrayList<GameIcon> currentDayIcons = new ArrayList<>();

    public MapGame(Game game, Dimension defaultSize) {
        MapGame.game = game;
        MapGame.defaultSize = defaultSize;

        width = (int) (defaultSize.getWidth());
        height = (int) (defaultSize.getHeight());
        pauseButtonX = (int)(0.028 * width);
        pauseButtonY = (int)(0.16 * height);
        pauseButtonWH = (int)(0.0555 * width);

        for(int i = 0; i < Handler.getCurrentIcons(dayNumber).size(); i++){
            currentDayIcons.add(i, Handler.getCurrentIcons(dayNumber).get(i));
        }
    }

    public void mousePressed(MouseEvent e){
        int mx = e.getX();
        int my = e.getY();
        if(game.gameState == Game.STATE.MyMap) {
            if (mouseOver(mx, my, pauseButtonX, pauseButtonY, pauseButtonWH, pauseButtonWH)) {
                game.gameState = Game.STATE.PauseMenu;
            }
            for (GameIcon activeIcon : activeIcons) {
                if (mouseOver(mx, my, activeIcon.getX(), activeIcon.getY(), activeIcon.getWidth(), activeIcon.getHeight())){
                    game.gameState = Game.STATE.Dialog;
                    for(int i = 0; i < currentDayIcons.size(); i++){
                        if(currentDayIcons.get(i).equals(activeIcon)){
                            currentDialogIndex = i;
                            currentDayIcons.remove(currentDialogIndex);
                        }
                    }
                    try {
                        Game.gameDialog.setDialogParams(dayNumber, currentDialogIndex);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
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

    public void render(Graphics g) throws IOException {
        if(timePassed == 0){
            //Render "Before" Cutscene
            triggerCutscene(dayNumber);
        }
        else if (timePassed <= screenDuration) {
            //Time keeper
            if(game.gameState == Game.STATE.MyMap) {
                long currentTime = System.currentTimeMillis();
                timePassed += currentTime - prevTime;
                prevTime = currentTime;
            }
            //Pick active icons
            activeIcons.clear();
            for (int i = 0; i < currentDayIcons.size(); i++) {
                    if (timePassed >= currentDayIcons.get(i).getExecutionTime()
                            && timePassed <= currentDayIcons.get(i).getExecutionTime()
                            + currentDayIcons.get(i).getDuration()) {
                        activeIcons.add(currentDayIcons.get(i));
                    }
            }
            //Render active icons
            for (GameIcon activeIcon : activeIcons) {
                activeIcon.render(g, timePassed);
            }

            //RENDER DEFAULT ELEMENTS
            Font font1 = new Font("arial", Font.BOLD, pauseButtonX);
            g.setColor(new Color(99, 52, 53));
            g.setFont(font1);

            g.setColor(Color.BLACK);
            g.fillOval(pauseButtonX - 5, pauseButtonY + 5, pauseButtonWH, pauseButtonWH);

            g.setColor(new Color(99, 52, 53));
            g.fillOval(pauseButtonX, pauseButtonY, pauseButtonWH, pauseButtonWH);

            g.setColor(Color.WHITE);
            g.drawString("I I", pauseButtonX + pauseButtonWH / 2 - Game.strLength(g, "I I") / 2,
                pauseButtonY + pauseButtonWH / 2 + Game.strHeight(g) / 3);

            //RENDER THE CLOCK
            g.setColor(Color.BLACK);
            Font font2 = new Font("rockwell", 1, 28);
            g.setFont(font2);
            int hours = 9 + (int)timePassed / ((int)screenDuration / 9);
            int minutes = ((int)timePassed % ((int)screenDuration / 9)) / ((int)screenDuration / 540);
            String time = hours + ":" + minutes;
            if(Integer.toString(hours).length() == 1) {
                time = "0" + time;
            }
            if(Integer.toString(minutes).length() == 1){
                time = time.substring(0, 3) + "0" + time.substring(3);
            }
            g.drawString("Day " + dayNumber, pauseButtonX, (int)(0.85 * height));
            g.drawString(time, pauseButtonX, (int)(0.9 * height));

            //RENDER THE MONEY
            g.drawString("M: " + MARKS, width - 3 * pauseButtonX, (int) (pauseButtonY + 0.5 * pauseButtonWH));
        }
        else {
            //If time is over
            dayNumber++;
            savedDayMetrics[0] = MapGame.Berlin;
            savedDayMetrics[1] = MapGame.FRG;
            savedDayMetrics[2] = MapGame.GDR;
            savedDayMetrics[3] = MapGame.MARKS;
            currentDayIcons.clear();
            activeIcons.clear();
            for(int i = 0; i < Handler.getCurrentIcons(dayNumber).size(); i++){
                currentDayIcons.add(i, Handler.getCurrentIcons(dayNumber).get(i));
            }
            currentDialogIndex = 0;
            timePassed = 0;
            prevTime = System.currentTimeMillis();
        }
    }

    public void tick(){

    }

    public void triggerCutscene(int dayNumber){
        Cutscene.dayNumber = dayNumber;
        Cutscene.timePassed = 0;
        Cutscene.prevTime = System.currentTimeMillis();
        Cutscene.currentCutscenes = Handler.getFrames(dayNumber);
        game.gameState = Game.STATE.Cutscene;
    }
}
