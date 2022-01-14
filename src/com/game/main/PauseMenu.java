package com.game.main;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class PauseMenu extends MouseAdapter {
    private Game game;
    private int winW, winH, buttonW, buttonH, buttonGap, strHeight2;
    private int width, height;

    public PauseMenu(Game game, Dimension defaultSize) {
        this.game = game;

        width = (int) (defaultSize.getWidth());
        height = (int) (defaultSize.getHeight());
        winW = (int)(0.28 * width);
        winH = (int)(0.61 * height);
        buttonW = (int)(0.14 * width);
        buttonH = (int)(0.07 * height);
    }

    public void mousePressed(MouseEvent e){
        int mx = e.getX();
        int my = e.getY();

        if(game.gameState == Game.STATE.PauseMenu) {
            //RESUME
            if(mouseOver(mx, my,width / 2 - buttonW / 2,
                    height / 2 - winH / 2 + (int)(1.5 * buttonGap) + strHeight2, buttonW, buttonH)){
                game.gameState = Game.STATE.MyMap;
                MapGame.prevTime = System.currentTimeMillis();
            }
            //SAVE
            else if(mouseOver(mx, my,width / 2 - buttonW / 2 - 5,
                    height / 2 - winH / 2 + (int)(2.5 * buttonGap) + strHeight2 + buttonH + 5, buttonW, buttonH)){
                try{
                    //Look for prev save file
                    File dir = new File("Saves/");
                    File[] matches = dir.listFiles((dir1, name) -> name.startsWith("save") && name.endsWith(".txt"));
                    //Write a file
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM_dd_yyyy_HH_mm_ss");
                    LocalDateTime now = LocalDateTime.now();
                    String fileName = "save" + dtf.format(now) + ".txt";
                    File myObj = new File("Saves/" + fileName);
                    if (myObj.createNewFile()) {
                        System.out.println("File created: " + myObj.getName());
                        try {
                            FileWriter myWriter = new FileWriter("Saves/" + fileName);
                            myWriter.write("D:"+MapGame.dayNumber + "\n" +
                                    "B:" + MapGame.savedDayMetrics[0] + "\n" +
                                    "F:" + MapGame.savedDayMetrics[1] + "\n" +
                                    "G:" + MapGame.savedDayMetrics[2] + "\n" +
                                    "M:" + MapGame.savedDayMetrics[3] + "\n");
                            myWriter.close();
                            System.out.println("Successfully wrote to the file.");
                        } catch (IOException f) {
                            System.out.println("An error occurred.");
                            f.printStackTrace();
                        }
                        for(int i = 0; i < matches.length; i++){
                            File fileToDel = new File("Saves/" + matches[i].getName());
                            if(fileToDel.delete()) {
                                System.out.println("The old file deleted successfully");
                            } else {
                                System.out.println("Failed to delete the old file");
                            }
                        }
                    } else {
                        System.out.println("File already exists.");
                    }
                }catch (IOException a) {
                System.out.println("An error occurred.");
                a.printStackTrace();
                }
            }
            //LOAD
            else if (mouseOver(mx, my,width / 2 - buttonW / 2 - 5,
                    height / 2 - winH / 2 + (int)(3.5 * buttonGap) + strHeight2 + 2 * buttonH + 5, buttonW, buttonH)) {
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
                MapGame.currentDayIcons = Handler.getCurrentIcons(MapGame.dayNumber);
                MapGame.currentDialogIndex = 0;

                Cutscene.eventIndex = 0;
                MapGame.timePassed = 0;
                MapGame.prevTime = System.currentTimeMillis();
                game.gameState = Game.STATE.MyMap;
            }
            //QUIT
            else if (mouseOver(mx, my,width / 2 - buttonW / 2,
                    height / 2 - winH / 2 + (int)(4.5 * buttonGap) + strHeight2 + 3 * buttonH, buttonW, buttonH)) {
                try{
                    //Look for prev save file
                    File dir = new File("Saves/");
                    File[] matches = dir.listFiles((dir1, name) -> name.startsWith("save") && name.endsWith(".txt"));
                    //Write a file
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM_dd_yyyy_HH_mm_ss");
                    LocalDateTime now = LocalDateTime.now();
                    String fileName = "save" + dtf.format(now) + ".txt";
                    File myObj = new File("Saves/" + fileName);
                    if (myObj.createNewFile()) {
                        System.out.println("File created: " + myObj.getName());
                        try {
                            FileWriter myWriter = new FileWriter("Saves/" + fileName);
                            myWriter.write("D:"+MapGame.dayNumber + "\n" +
                                    "B:" + MapGame.savedDayMetrics[0] + "\n" +
                                    "F:" + MapGame.savedDayMetrics[1] + "\n" +
                                    "G:" + MapGame.savedDayMetrics[2] + "\n" +
                                    "M:" + MapGame.savedDayMetrics[3] + "\n");
                            myWriter.close();
                            System.out.println("Successfully wrote to the file.");
                        } catch (IOException f) {
                            System.out.println("An error occurred.");
                            f.printStackTrace();
                        }
                        for(int i = 0; i < matches.length; i++){
                            File fileToDel = new File("Saves/" + matches[i].getName());
                            if(fileToDel.delete()) {
                                System.out.println("The old file deleted successfully");
                            } else {
                                System.out.println("Failed to delete the old file");
                            }
                        }
                    } else {
                        System.out.println("File already exists.");
                    }
                }catch (IOException a) {
                    System.out.println("An error occurred.");
                    a.printStackTrace();
                }
                game.gameState = Game.STATE.MainMenu;
                MapGame.timePassed = 0;
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
        //Pause Window
        Font font1 = new Font("arial", 1, 40);
        g.setFont(font1);

        g.setColor(Color.BLACK);
        g.fillRect(width / 2 - winW / 2 - 10, height / 2 - winH / 2 + 10, winW, winH);

        g.setColor(new Color(99, 52, 53));
        g.fillRect(width / 2 - winW / 2, height / 2 - winH / 2, winW, winH);
        //Buttons
        buttonGap = (winH - 4 * buttonH - strHeight2) / 5;
        int strHeight1 =  Game.strHeight(g);

        g.setColor(Color.BLACK);
        g.drawString("Pause", width / 2 - Game.strLength(g, "Pause") / 2 - 5,
                height / 2 - winH / 2 + buttonGap / 2 + strHeight2 + 5);

        g.setColor(Color.WHITE);
        g.drawString("Pause", width / 2 - Game.strLength(g, "Pause") / 2,
                height / 2 - winH / 2 + buttonGap / 2 + strHeight2);

        g.setColor(Color.BLACK);
        g.fillRect(width / 2 - buttonW / 2 - 5,
                height / 2 - winH / 2 + (int)(1.5 * buttonGap) + strHeight2 + 5, buttonW, buttonH);

        g.fillRect(width / 2 - buttonW / 2 - 5,
                height / 2 - winH / 2 + (int)(2.5 * buttonGap) + strHeight2 + buttonH + 5, buttonW, buttonH);

        g.fillRect(width / 2 - buttonW / 2 - 5,
                height / 2 - winH / 2 + (int)(3.5 * buttonGap) + strHeight2 + 2 * buttonH + 5, buttonW, buttonH);

        g.fillRect(width / 2 - buttonW / 2 - 5,
                height / 2 - winH / 2 + (int)(4.5 * buttonGap) + strHeight2 + 3 * buttonH + 5, buttonW, buttonH);


        g.setColor(Color.WHITE);
        g.fillRect(width / 2 - buttonW / 2,
                height / 2 - winH / 2 + (int)(1.5 * buttonGap) + strHeight2, buttonW, buttonH);

        g.fillRect(width / 2 - buttonW / 2,
                height / 2 - winH / 2 + (int)(2.5 * buttonGap) + strHeight2 + buttonH, buttonW, buttonH);

        g.fillRect(width / 2 - buttonW / 2,
                height / 2 - winH / 2 + (int)(3.5 * buttonGap) + strHeight2 + 2 * buttonH, buttonW, buttonH);

        g.fillRect(width / 2 - buttonW / 2,
                height / 2 - winH / 2 + (int)(4.5 * buttonGap) + strHeight2 + 3 * buttonH, buttonW, buttonH);

        Font font2 = new Font("arial", 1, 30);
        g.setFont(font2);
        g.setColor(Color.BLACK);

        strHeight2 = Game.strHeight(g);

        g.drawString("Resume", width / 2 - Game.strLength(g, "Resume") / 2,
                height / 2 - winH / 2 + (int)(1.5 * buttonGap) + strHeight1 + buttonH / 3 + strHeight2 / 3);

        g.drawString("Save", width / 2 - Game.strLength(g, "Save") / 2,
                height / 2 - winH / 2 + (int)(2.5 * buttonGap) + strHeight1 + buttonH + buttonH / 3 + strHeight2 / 3);

        g.drawString("Load", width / 2 - Game.strLength(g, "Load") / 2,
                height / 2 - winH / 2 + (int)(3.5 * buttonGap) + strHeight1 + 2 * buttonH + buttonH / 3 + strHeight2 / 3);

        g.drawString("Quit", width / 2 - Game.strLength(g, "Quit") / 2,
                height / 2 - winH / 2 + (int)(4.5 * buttonGap) + strHeight1 + 3 * buttonH + buttonH / 3 + strHeight2 / 3);
    }
}
