package de.hhu.propra16.TDDT;

import java.io.File;
import java.io.FilenameFilter;

public class Übung {

    public File[] finder(){
        File dir = new File("res/Übungen");

        return dir.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String filename)
            {
                return filename.endsWith(".txt"); }
        } );
    }

    public int anzahlÜbungen(){
        return finder().length;
    }
  //  public
}
