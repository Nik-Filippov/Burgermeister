package com.game.main;

import java.awt.*;

public class BasicIcon extends GameIcon {
    private long startTime;
    private boolean isFirstRender = true, isVisible = true;
    public BasicIcon(Game game, long executionTime, long duration, ID id,
                     int iconX, int iconY, int iconWidth, int iconHeight, Dimension d) {
        super(game,executionTime, duration, id, iconX, iconY, iconWidth, iconHeight, d);

    }

    public void tick() {

    }

    public void render(Graphics g, long timePassed) {
        if(isVisible) {
            if (isFirstRender) {
                startTime = timePassed;
                isFirstRender = false;
            }
            iconWidth = (int) ((0.0555 / 2) * d.getWidth());
            g.setColor(Color.BLACK);
            g.fillOval(iconX - 5, iconY + 5, iconWidth, iconWidth);
            g.setColor(new Color(99, 52, 53));
            g.fillOval(iconX, iconY, iconWidth, iconWidth);
            g.setColor(Color.BLACK);


            g.drawOval(iconX, iconY, iconWidth, iconWidth);

            Font font1 = new Font("rockwell", 1, 15);
            g.setFont(font1);
            g.setColor(new Color(235, 227, 203));
            int minutes = (int) (duration - timePassed + executionTime) / 60000;
            int seconds = (int) (duration - timePassed + executionTime) / 1000;
            String timer = minutes + ":" + seconds;
            g.drawString(timer, iconX + iconWidth / 2 - Game.strLength(g, timer) / 2,
                    iconY + iconWidth / 2 + Game.strHeight(g) / 3);
        }
    }
}
