package com.game.main;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.ArrayList;

public class MainMenu extends MouseAdapter {

    private Game game;
    private final int buttonWidth;
    private final int buttonHeight;
    private final int buttonHorizontalGap;
    private final int buttonYCoord;

    BufferedReader br;

    public MainMenu(Game game, Dimension defaultSize) {
        this.game = game;
        int width = (int) (defaultSize.getWidth());
        int height = (int) (defaultSize.getHeight());
        buttonWidth = (int)(0.14 * width);
        buttonHeight = (int)(0.07 * height);
        buttonHorizontalGap = (int)(0.088 * width);
        buttonYCoord = (int)(0.844 * height);
    }

    public void mousePressed(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();

        if(game.gameState == Game.STATE.MainMenu) {
            //New Game
            if (mouseOver(mx, my,buttonHorizontalGap,
                    buttonYCoord, buttonWidth, buttonHeight)) {
                game.gameState = Game.STATE.MyMap;
                MapGame.prevTime = System.currentTimeMillis();
                MapGame.dayNumber = 1;
                MapGame.currentDialogIndex = 0;
                MapGame.timePassed = 0;
            }
            //Load
            else if (mouseOver(mx, my,2 * buttonHorizontalGap + buttonWidth,
                    buttonYCoord, buttonWidth, buttonHeight)) {

                File dir = new File("Saves/");
                File[] matches = dir.listFiles((dir1, name) -> name.startsWith("save") && name.endsWith(".txt"));
                ArrayList<String> lines = new ArrayList<>();

                try {
                    lines = LinesFileReader.readFile("Saves/" + matches[0].getName());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                MapGame.dayNumber = Integer.parseInt(lines.get(0).substring(2));
                MapGame.Berlin = Integer.parseInt(lines.get(1).substring(2));
                MapGame.FRG = Integer.parseInt(lines.get(2).substring(2));
                MapGame.GDR = Integer.parseInt(lines.get(3).substring(2));
                MapGame.MARKS = Integer.parseInt(lines.get(4).substring(2));
                MapGame.currentDialogs = Handler.getCurrentIcons(MapGame.dayNumber);
                MapGame.currentDialogIndex = 0;

                Cutscene.eventIndex = 0;
                MapGame.timePassed = 0;
                MapGame.prevTime = System.currentTimeMillis();
                game.gameState = Game.STATE.MyMap;
            }
            //Settings
            else if (mouseOver(mx, my,3 * buttonHorizontalGap + 2 * buttonWidth,
                    buttonYCoord, buttonWidth, buttonHeight)) {
                game.gameState = Game.STATE.Settings;
            }
            //Help
            else if (mouseOver(mx, my, 4 * buttonHorizontalGap + 3 * buttonWidth,
                    buttonYCoord, buttonWidth, buttonHeight)) {
                game.gameState = Game.STATE.Help;
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
        Font font1 = new Font("arial", 1, 30);
        g.setColor(new Color(235, 227, 203));
        g.setFont(font1);


        //Buttons
        g.drawString("New Game",
                buttonHorizontalGap + buttonWidth / 2 - Game.strLength(g, "New Game") / 2,
                buttonYCoord + buttonHeight / 2 + (int)(Game.strHeight(g) / 4));

        g.drawString("Load Game",
                2 * buttonHorizontalGap + (int)(1.5 * buttonWidth) - Game.strLength(g, "Load Game") / 2,
                buttonYCoord + buttonHeight / 2 + (int)(Game.strHeight(g) / 4));

        g.drawString("Settings",
                3 * buttonHorizontalGap + (int)(2.5 * buttonWidth) - Game.strLength(g, "Settings") / 2,
                buttonYCoord + buttonHeight / 2 + (int)(Game.strHeight(g) / 4));

        g.drawString("Help",
                4 * buttonHorizontalGap + (int)(3.5 * buttonWidth) - Game.strLength(g, "Help") / 2,
                buttonYCoord + buttonHeight / 2 + (int)(Game.strHeight(g) / 4));

        g.drawRect(buttonHorizontalGap, buttonYCoord, buttonWidth, buttonHeight);

        g.drawRect(2 * buttonHorizontalGap + buttonWidth, buttonYCoord, buttonWidth, buttonHeight);

        g.drawRect(3 * buttonHorizontalGap + 2 * buttonWidth, buttonYCoord, buttonWidth, buttonHeight);

        g.drawRect(4 * buttonHorizontalGap + 3 * buttonWidth, buttonYCoord, buttonWidth, buttonHeight);

    }

    public void tick(){

    }
}
