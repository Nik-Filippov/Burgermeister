package com.game.main;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class Handler {
    public static ArrayList<ArrayList<GameIcon>> icons = new ArrayList<>();

    //First layer - days, second layer - day-specific positioning (before - 0, after - 1), third layer - array of CutsceneFrames itself
    public static ArrayList<ArrayList<ArrayList<CutsceneFrame>>> frames = new ArrayList<>();

    public Handler(Game game, Dimension d) throws IOException {
        //Icons
        ArrayList<GameIcon> d1 = new ArrayList<>();
        ArrayList<GameIcon> d2 = new ArrayList<>();
        ArrayList<GameIcon> d3 = new ArrayList<>();
        icons.add(0, new ArrayList<>());
        d1.add(new BasicIcon(game,0, 6000, ID.BasicIcon, 200, 200, 50, 50, d));
        //d1.add(new BasicIcon(game,0, 6000, ID.BasicIcon, 200, 200, 50, 50, d));
        icons.add(1, d1);
        d2.add(new BasicIcon(game,0, 6000, ID.BasicIcon, 400, 400, 50, 50, d));
        //d2.add(new BasicIcon(game,0, 6000, ID.BasicIcon, 200, 400, 50, 50, d));
        icons.add(2, d2);
        d3.add(new BasicIcon(game,0, 6000, ID.BasicIcon, 600, 600, 50, 50, d));
        //d3.add(new BasicIcon(game,0, 6000, ID.BasicIcon, 400, 400, 50, 50, d));
        icons.add(3, d3);

        //Cutscene
        ArrayList<CutsceneFrame> img1 = new ArrayList<>();
        ArrayList<CutsceneFrame> img2 = new ArrayList<>();
        CutsceneFrame frame1 = new CutsceneFrame("DialogImages/InterCutscene/Inter1.jpg", "",0, 2000, d);
        CutsceneFrame frame2 = new CutsceneFrame("DialogImages/InterCutscene/Inter2.jpg", "",2000, 2000, d);
        CutsceneFrame frame3 = new CutsceneFrame("DialogImages/InterCutscene/Inter3.jpg", "",4000, 2000, d);

        img1.add(frame1);
        img1.add(frame2);
        img1.add(frame3);

        frame1 = new CutsceneFrame("DialogImages/InterCutscene/Inter3.jpg", "",0, 2000, d);
        frame2 = new CutsceneFrame("DialogImages/InterCutscene/Inter2.jpg", "",2000, 2000, d);
        frame3 = new CutsceneFrame("DialogImages/InterCutscene/Inter1.jpg", "",4000, 2000, d);

        img2.add(frame1);
        img2.add(frame2);
        img2.add(frame3);

        //day-specific positioning (before - 0, after - 1)
        ArrayList<ArrayList<CutsceneFrame>> placement = new ArrayList<>(2);

        placement.add(0, img1);
        placement.add(1, img2);

        frames.add(0, placement);
        frames.add(1, placement);
        frames.add(2, placement);
        frames.add(3, placement);
    }

    public static ArrayList<GameIcon> getCurrentIcons(int index){
        return icons.get(index);
    }

    public static ArrayList<ArrayList<CutsceneFrame>> getFrames(int dayNumber){
        return frames.get(dayNumber);
    }
}
