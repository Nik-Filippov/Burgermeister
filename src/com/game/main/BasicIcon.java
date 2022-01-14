package com.game.main;

import java.awt.*;

public class BasicIcon{
    protected long executionTime, duration;
    protected Game game;
    protected int iconX, iconY, iconWidth, iconHeight;
    protected Dimension d;
    public BasicIcon(Game game, long executionTime, long duration,
                     int iconX, int iconY, int iconWidth, int iconHeight, Dimension d) {
        this.game = game;
        this.executionTime = executionTime;
        this.duration = duration;
        this.iconX = iconX;
        this.iconY = iconY;
        this.iconWidth = iconWidth;
        this.iconHeight = iconHeight;
        this.d = d;
    }

    public void render(Graphics g, long timePassed) {
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

    public int getX() { return iconX; }

    public int getY() {
        return iconY;
    }

    public int getWidth() {
        return iconWidth;
    }

    public int getHeight() {
        return iconHeight;
    }

    public long getExecutionTime(){
        return executionTime;
    }

    public long getDuration(){
        return duration;
    }
}
