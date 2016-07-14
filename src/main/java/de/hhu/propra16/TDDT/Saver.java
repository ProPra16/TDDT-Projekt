package de.hhu.propra16.TDDT;
import java.io.FileNotFoundException;
import java.io.*;

public class Saver {
    private static WarningUnit Reporter = new WarningUnit();

    public static void codesave(UserCode code) {
        String name = code.getKlassenName();
        try {
            File x = new File("Codes/"+name+".java");
            PrintStream Writer = new PrintStream(x);
            Writer.println(code.getClassContent());
        } catch (FileNotFoundException e) {
            Reporter.commonError("Der Codes Ordner ist nicht verf\u00FCgbar",
                    "Bitte erstelle einen Ordner mit dem Namen \"Codes\",\num den Code aus Green in eine Java-Datei zu schreiben.\n " +
                            "Achte darauf, dass der Ordner im selben Verzeichnis wie die \"TDDT.jar\" Datei ist.");
        }

    }
    public static void timesaver(TrackingUnit time){
        try {
            PrintStream Writer = new PrintStream(new File("Codes/TrackingTimes.txt"));
            Writer.println(time.getTrackingStats());
        } catch (FileNotFoundException e) {
            Reporter.commonError("Der Codes Ordner ist nicht verf\u00FCgbar",
                    "Bitte erstelle einen Ordner mit dem Namen \"Codes\",\num die Trackingdaten in eine Datei zu schreiben.\n " +
                            "Achte darauf, dass der Ordner im selben Verzeichnis wie die \"TDDT.jar\" Datei ist.");
        }
    }
}