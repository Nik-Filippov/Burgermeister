package com.game.main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class LinesFileReader {
    public static ArrayList<String> readFile(String path) throws IOException {
        ArrayList lines = new ArrayList();
        BufferedReader br = new BufferedReader(new java.io.FileReader(path));
        String description;
        while((description = br.readLine()) != null) {
            lines.add(description);
        }
        br.close();
        return lines;
    }
}
