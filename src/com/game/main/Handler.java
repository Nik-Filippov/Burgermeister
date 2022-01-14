package com.game.main;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class Handler {
    private static ArrayList<ArrayList<GameIcon>> icons = new ArrayList<>();

    //First layer - days, second layer - array of CutsceneFrames itself.
    private static ArrayList<ArrayList<CutsceneFrame>> frames = new ArrayList<>();

    public Handler(Game game, Dimension d) throws IOException {
        //Icons
        ArrayList<GameIcon> d1 = new ArrayList<>();
        ArrayList<GameIcon> d2 = new ArrayList<>();
        ArrayList<GameIcon> d3 = new ArrayList<>();
        ArrayList<GameIcon> d4 = new ArrayList<>();

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
        d4.add(new BasicIcon(game,0, 6000, ID.BasicIcon, 230, 600, 50, 50, d));
        //d4.add(new BasicIcon(game,0, 6000, ID.BasicIcon, 400, 400, 50, 50, d));
        icons.add(4, d4);

        //Cutscenes

        //Inter Cutscenes
        ArrayList<CutsceneFrame> interBefore = new ArrayList<>();
        ArrayList<CutsceneFrame> interAfter = new ArrayList<>();
        CutsceneFrame frame1 = new CutsceneFrame("DialogImages/InterCutscene/Inter1.jpg", "",0, 2000, d);
        CutsceneFrame frame2 = new CutsceneFrame("DialogImages/InterCutscene/Inter2.jpg", "",2000, 2000, d);
        CutsceneFrame frame3 = new CutsceneFrame("DialogImages/InterCutscene/Inter3.jpg", "",4000, 2000, d);

        interBefore.add(frame1);
        interBefore.add(frame2);
        interBefore.add(frame3);

        CutsceneFrame frame4 = new CutsceneFrame("DialogImages/InterCutscene/Inter3.jpg", "",0, 2000, d);
        CutsceneFrame frame5 = new CutsceneFrame("DialogImages/InterCutscene/Inter2.jpg", "",2000, 2000, d);
        CutsceneFrame frame6 = new CutsceneFrame("DialogImages/InterCutscene/Inter1.jpg", "",4000, 2000, d);

        interAfter.add(frame4);
        interAfter.add(frame5);
        interAfter.add(frame6);

        frames.add(0, interBefore);
        frames.add(1, interBefore);
        frames.add(2, interBefore);
        frames.add(3, interBefore);
        frames.add(4, interBefore);
    }

    public static ArrayList<GameIcon> getCurrentIcons(int index){
        return icons.get(index);
    }

    public static ArrayList<CutsceneFrame> getFrames(int dayNumber){
        return frames.get(dayNumber);
    }
}
