package com.game.main;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class GameDialog extends MouseAdapter {
    private final Game game;
    private BufferedImage dialogImage;
    private int width, height, winW, winH, buttonW, buttonH, imgW, imgH, infoY;
    public static int[] option1 = new int[4];
    public static int[] option2 = new int[4];
    public static int[] option3 = new int[4];
    public static int[] option4 = new int[4];

    BufferedReader br;

    private int dayNumber = MapGame.dayNumber;
    private int eventIndex = MapGame.currentDialogIndex;

    ArrayList<String> lines = new ArrayList<>();

    public GameDialog(Game game, Dimension defaultSize) throws IOException {
        this.game = game;

        width = (int)defaultSize.getWidth();
        height = (int)defaultSize.getHeight();
        winW = (int)(0.64 * width);
        winH = (int)(0.71 * height);
        buttonW = (int)(0.58 * width);
        buttonH = (int)(0.0555 * height);
        infoY = (int)(0.355 * height);


    }
    //Very important method that defines the dialog choice.
    public void setDialogParams(int dayNumber, int eventIndex) throws IOException {
        this.dayNumber = dayNumber;
        this.eventIndex = eventIndex;

        br = new BufferedReader(new FileReader("Dialogs/"
                + dayNumber + "." + eventIndex + ".txt"));
        String description;
        while((description = br.readLine()) != null) {
            lines.add(description);
        }
        br.close();
        //Set dialog influence on metrics
        for (String line : lines) {
            if (line.startsWith("*1")) {
                if (line.charAt(2) == 'B') {
                    option1[0] = Integer.parseInt(line.substring(3, 5));
                }
                if (line.charAt(5) == 'F') {
                    option1[1] = Integer.parseInt(line.substring(6, 8));
                }
                if (line.charAt(8) == 'G') {
                    option1[2] = Integer.parseInt(line.substring(9, 11));
                }
                if (line.charAt(11) == 'M') {
                    option1[3] = Integer.parseInt(line.substring(12));
                }
            } else if (line.startsWith("*2")) {
                if (line.charAt(2) == 'B') {
                    option2[0] = Integer.parseInt(line.substring(3, 5));
                }
                if (line.charAt(5) == 'F') {
                    option2[1] = Integer.parseInt(line.substring(6, 8));
                }
                if (line.charAt(8) == 'G') {
                    option2[2] = Integer.parseInt(line.substring(9, 11));
                }
                if (line.charAt(11) == 'M') {
                    option2[3] = Integer.parseInt(line.substring(12));
                }
            } else if (line.startsWith("*3")) {
                if (line.charAt(2) == 'B') {
                    option3[0] = Integer.parseInt(line.substring(3, 5));
                }
                if (line.charAt(5) == 'F') {
                    option3[1] = Integer.parseInt(line.substring(6, 8));
                }
                if (line.charAt(8) == 'G') {
                    option3[2] = Integer.parseInt(line.substring(9, 11));
                }
                if (line.charAt(11) == 'M') {
                    option3[3] = Integer.parseInt(line.substring(12));
                }
            } else if (line.startsWith("*4")) {
                if (line.charAt(2) == 'B') {
                    option4[0] = Integer.parseInt(line.substring(3, 5));
                }
                if (line.charAt(5) == 'F') {
                    option4[1] = Integer.parseInt(line.substring(6, 8));
                }
                if (line.charAt(8) == 'G') {
                    option4[2] = Integer.parseInt(line.substring(9, 11));
                }
                if (line.charAt(11) == 'M') {
                    option4[3] = Integer.parseInt(line.substring(12));
                }
            }
        }
    }

    public void mousePressed(MouseEvent e){
        int mx = e.getX();
        int my = e.getY();

        if(game.gameState == Game.STATE.Dialog) {
            if (mouseOver(mx, my,width / 2 - buttonW / 2, (int)(0.6 * height), buttonW, buttonH)) {
                MapGame.Berlin += option1[0];
                MapGame.FRG += option1[1];
                MapGame.GDR += option1[2];
                MapGame.MARKS += option1[3];
                //System.out.println(MapGame.Berlin + " " + MapGame.FRG + " " + MapGame.GDR);
                game.gameState = Game.STATE.MyMap;
                MapGame.prevTime = System.currentTimeMillis();
                lines.clear();
            }
            else if(mouseOver(mx, my, width / 2 - buttonW / 2, (int)(0.6 * height) + buttonH, buttonW, buttonH)){
                MapGame.Berlin += option2[0];
                MapGame.FRG += option2[1];
                MapGame.GDR += option2[2];
                MapGame.MARKS += option2[3];
                game.gameState = Game.STATE.MyMap;
                MapGame.prevTime = System.currentTimeMillis();
                lines.clear();
            }
            else if(mouseOver(mx, my, width / 2 - buttonW / 2, (int)(0.6 * height) + 2 * buttonH, buttonW, buttonH)){
                MapGame.Berlin += option3[0];
                MapGame.FRG += option3[1];
                MapGame.GDR += option3[2];
                MapGame.MARKS += option3[3];
                game.gameState = Game.STATE.MyMap;
                MapGame.prevTime = System.currentTimeMillis();
                lines.clear();
            }
            else if(mouseOver(mx, my, width / 2 - buttonW / 2, (int)(0.6 * height) + 3 * buttonH, buttonW, buttonH)){
                MapGame.Berlin += option4[0];
                MapGame.FRG += option4[1];
                MapGame.GDR += option4[2];
                MapGame.MARKS += option4[3];
                game.gameState = Game.STATE.MyMap;
                MapGame.prevTime = System.currentTimeMillis();
                lines.clear();
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
        int fontSize = (int)(0.01 * width);
        Font font1 = new Font("arial", 1, fontSize);
        g.setFont(font1);
        //Window
        g.setColor(new Color(99, 52, 53));
        g.fillRect(width / 2 - winW / 2, height / 2 - winH / 2, winW, winH);
        //Buttons
        g.setColor(new Color(235, 227, 203));
        g.fillRect(width / 2 - buttonW / 2, (int)(0.6 * height), buttonW, buttonH);
        g.fillRect(width / 2 - buttonW / 2, (int)(0.6 * height) + buttonH, buttonW, buttonH);
        g.fillRect(width / 2 - buttonW / 2, (int)(0.6 * height) + 2 * buttonH, buttonW, buttonH);
        g.fillRect(width / 2 - buttonW / 2, (int)(0.6 * height) + 3 * buttonH, buttonW, buttonH);

        g.setColor(Color.BLACK);
        g.drawRect(width / 2 - buttonW / 2, (int)(0.6 * height), buttonW, buttonH);
        g.drawRect(width / 2 - buttonW / 2, (int)(0.6 * height) + buttonH, buttonW, buttonH);
        g.drawRect(width / 2 - buttonW / 2, (int)(0.6 * height) + 2 * buttonH, buttonW, buttonH);
        g.drawRect(width / 2 - buttonW / 2, (int)(0.6 * height) + 3 * buttonH, buttonW, buttonH);
        //TEXT
        int y = (int)(0.47 * height);
        for (String line : lines) {
            if(line.length() < 1 || Character.isDigit(line.charAt(0))) {
                //ANSWERS
                if(line.length() > 2) {
                    g.setColor(Color.BLACK);
                    line = line.substring(2);
                }
                g.drawString(line, width / 2 - buttonW / 2 + (int)(0.014 * width), y);
                y += buttonH;
            }
            else if (line.startsWith("*1") || line.startsWith("*2") || line.startsWith("*3") || line.startsWith("*4")) {
                continue;
            }
            else{
                //INFO
                y += (int)(0.028 * height);
                g.drawString(line, width / 2 - buttonW / 2, y);
            }
        }
    }

    public void tick(){

    }
}
