package com.game.main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;

public class CutsceneFrame {
    String imgPath, textPath;
    long duration, startTime;
    BufferedImage image;
    Dimension size;

    public CutsceneFrame (String imgPath, String textPath, long startTime, long duration, Dimension size) throws IOException {
        this.imgPath = imgPath;
        this.textPath = textPath;
        this.startTime = startTime;
        this.duration = duration;
        this.size = size;

        File imgFile = new File(imgPath);
        image = ImageIO.read(imgFile);
    }

    public void render(Graphics g) throws IOException {
        ImageDrawer.drawScaledImage(image, Game.win.getFrame(), g);
        if(!textPath.isEmpty()){
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, 10, 10);

            ArrayList<String> lines = new ArrayList<>();

            BufferedReader br = new BufferedReader(new FileReader(textPath));
            String description;
            while((description = br.readLine()) != null) {
                lines.add(description);
            }
            br.close();

            int fontSize = (int)(0.01 * size.getWidth());
            Font font1 = new Font("arial", 1, fontSize);
            g.setFont(font1);

            int y = (int)(0.47 * size.getHeight());
            for (String line : lines) {
                y += (int)(0.028 * size.getHeight());
                g.drawString(line, 20, 20);
            }
        }
    }
}
