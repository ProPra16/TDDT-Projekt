package de.hhu.propra16.TDDT;

import java.io.*;
import java.util.ArrayList;

public class Ubung {

    private ArrayList<String> dateien = new ArrayList<String>();
    private String[] buttons = new String[4];
    ArrayList<String> inhalt = new ArrayList<String>();
    private String beschrTeil = "";
    private String codeTeil = "";
    private File file = new File("");
    private String path = null;
    private BufferedReader br = null;
    private int jarVar = 0;


    public void buttonNamer() throws Exception {
        if (getClass().getProtectionDomain().getCodeSource().getLocation().getFile().contains("jar")) {
            jarVar = 1;
        }
        if (jarVar == 1) {
            buttonNamerForJar();
        } else {
            buttonNamerForJava();
        }
    }

    public void buttonNamerForJar() throws Exception {
        path = (new File(".").getCanonicalPath());
        file = new File(path + "/Ubungen");
        File[] listOfFiles = file.listFiles();
        for (File file4 : listOfFiles) {
            String pfad = file4.toString();
            int pos = pfad.indexOf("Ubungen");
            pfad = pfad.substring(pos + 8, pfad.length());
            dateien.add(pfad);
        }
    }

    public void buttonNamerForJava() throws Exception {
        path = (new File(".").getCanonicalPath() + "/build/libs/Ubungen");
        file = new File(path);
        File[] listOfFiles = file.listFiles();
        for (File file4 : listOfFiles) {
            String pfad = file4.toString();
            int pos = pfad.indexOf("Ubungen");
            pfad = pfad.substring(pos + 8, pfad.length());
            dateien.add(pfad);
        }
    }

    public String[] fillArray() {
        for (int i = 0; i < buttons.length; i++) {
            if (dateien.size() > i) {
                buttons[i] = dateien.get(i);
            } else {
                buttons[i] = "";
            }
        }
        return buttons;
    }

    public int anzahlUbungen() {
        return dateien.size();
    }

    public void readFile(String filename, boolean istInUbung) throws Exception {
        String directory = "Ubungen/";
        if(istInUbung == false){
            directory = "";
        }
        if (jarVar == 1) {
            path = (new File(".").getCanonicalPath());
            file = new File(path + "/" + directory + filename);
        } else {
            path = (new File(".").getCanonicalPath() + "/build/libs/" + directory);
            file = new File(path + filename);
        }
        br = new BufferedReader(new FileReader(file));
        try {
            String line;
            while ((line = br.readLine()) != null) {
                inhalt.add(line);
            }
        } catch (Exception e) {}
    }

    public void trenneTeile() {
        boolean imBereich = false;
        for (int i = 0; i < this.inhalt.size(); i++) {
            if (this.inhalt.get(i).contains("+++description")) {
                if (imBereich == true) {
                    imBereich = false;
                } else {
                    imBereich = true;
                }
            } else {
                if (imBereich == true) {
                    this.beschrTeil += this.inhalt.get(i) + "\n";
                } else {
                    this.codeTeil += this.inhalt.get(i) + "\n";
                }
            }
        }
    }

    public String[] down(String[] buttons) {
        if (dateien.size() > 4) {
            if (!(buttons[3].equals(dateien.get(dateien.size() - 1)))) {
                int i = 0;
                boolean richtigeStelle = false;
                while (i < dateien.size() && richtigeStelle == false) {
                    if (buttons[0].equals(dateien.get(i))) {
                        richtigeStelle = true;
                    } else {
                        i++;
                    }
                }
                for (int j = i; j < i + buttons.length; j++) {
                    buttons[j - i] = dateien.get(j + 1);
                }
            }
            return buttons;
        }
        return null;
    }

    public String[] up(String[] buttons) {
        if (dateien.size() > 4) {
            if (!(buttons[0].equals(dateien.get(0)))) {
                int i = 0;
                boolean richtigeStelle = false;
                while (i < dateien.size() && richtigeStelle == false) {
                    if (buttons[0].equals(dateien.get(i))) {
                        richtigeStelle = true;
                    } else {
                        i++;
                    }
                }
                for (int j = i; j < i + 4; j++) {
                    buttons[j - i] = dateien.get(j - 1);
                }
                return buttons;
            }
        }
        return null;
    }

    public void clearAll(){
        this.inhalt = new ArrayList<String>();
        this.codeTeil = "";
        this.beschrTeil = "";
    }
    public String gibInhalt(){
        String ausgabe = "";
        for(String s : inhalt){
            ausgabe = ausgabe +  s + "\n";
        }
        return ausgabe;
    }

    public String replacer(String s){
        return s.replace("ae","\u00E4").replace("Ae","\u00C4").replace("ue","\u00FC").replace("Ue","\u00D4").replace("oe","\u00F6").replace("Oe","\u00D6");
    }

    public String gibCode(){
        return this.codeTeil;
    }
    public String gibBeschr(){
        return this.beschrTeil;
    }

}
