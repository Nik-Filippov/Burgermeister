package com.game.main;

import java.awt.*;

public abstract class GameIcon {
    protected long executionTime, duration;
    protected ID id;
    protected Game game;
    protected int iconX, iconY, iconWidth, iconHeight;
    protected Dimension d;

    public GameIcon(Game game, long executionTime, long duration, ID id,
                    int iconX, int iconY, int iconWidth, int iconHeight, Dimension d) {
        this.game = game;
        this.executionTime = executionTime;
        this.duration = duration;
        this.id = id;
        this.iconX = iconX;
        this.iconY = iconY;
        this.iconWidth = iconWidth;
        this.iconHeight = iconHeight;
        this.d = d;
    }

    public abstract void tick();

    public abstract void render(Graphics g, long timePassed);

    public ID getId() {
        return id;
    }

    public int getX() {
        return iconX;
    }

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
