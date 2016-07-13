package de.hhu.propra16.TDDT;
import java.io.FileNotFoundException;
import java.util.Formatter;
import java.util.Scanner;
import java.io.*;
import java.util.*;

/**
 * Created by Henrik on 13.07.16.
 */
public class Saver {
    public static void codesave(UserCode code) {
        String name = code.getKlassenName();
        try {
            File x = new File("Codes/"+name+".txt");
            PrintStream Writer = new PrintStream(x);
            Writer.println(code.getClassCode());
        } catch (FileNotFoundException e) {
            System.out.println("Datei nicht gefunden");
        }

    }
    public static void timesaver(TrackingUnit time){

        try {
            PrintStream Writer = new PrintStream(new File("Codes/TrackingTimes"));
            Writer.println(time.getTrackingStats());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }
}