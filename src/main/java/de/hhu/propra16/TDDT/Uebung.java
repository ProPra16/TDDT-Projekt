package de.hhu.propra16.TDDT;

import java.io.*;
import java.util.ArrayList;

public class Uebung {

    private ArrayList<String> dateien = new ArrayList<>();
    private String[] buttons = new String[4];
    ArrayList<String> inhalt = new ArrayList<>();
    private String beschrTeil = "";
    private String testTeil = "";
    private String codeTeil = "";
    private File file = new File("");
    private String path = null;
    private BufferedReader br = null;
    private int jarVar = 0;
    private WarningUnit error = new WarningUnit();

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
        file = new File(path + "/\u00DCbungen");
        if(!file.exists()){
            error.folderError();
        }
        else {
            File[] listOfFiles = file.listFiles();
            for (File file4 : listOfFiles) {
                String pfad = file4.toString();
                int pos = pfad.indexOf("\u00DCbungen");
                pfad = pfad.substring(pos + 8, pfad.length());
                dateien.add(pfad);
            }
        }
    }

    public void buttonNamerForJava() throws Exception {
        path = (new File(".").getCanonicalPath() + "/build/libs/\u00DCbungen");
        file = new File(path);
        if(!file.exists()){
            error.folderError();
        }
        else{
            File[] listOfFiles = file.listFiles();
            for (File file4 : listOfFiles) {
                String pfad = file4.toString();
                int pos = pfad.indexOf("\u00DCbungen");
                pfad = pfad.substring(pos + 8, pfad.length());
                dateien.add(pfad);
            }
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

    public void readFile(String filename, boolean istInUbung) {
        String directory = "\u00DCbungen/";
        if(!istInUbung){
            directory = "";
        }
        try {
            if (jarVar == 1) {
                path = (new File(".").getCanonicalPath());
                file = new File(path + "/" + directory + filename);
            } else {
                path = (new File(".").getCanonicalPath() + "/build/libs/" + directory);
                file = new File(path + filename);
            }
            br = new BufferedReader(new FileReader(file));
        }
        catch (Exception e){
            br = null;
            path = null;
            file = null;
        }
        if(br != null) {
            try {
                String line;
                while ((line = br.readLine()) != null) {
                    inhalt.add(line);
                }
            } catch (Exception ignored) {
            }
        }
    }

    public void trenneTeile() {
        boolean imBeschreibungsBereich = false;
        boolean imTestBereich = false;
        boolean imCodeBereich = false;
        for (String anInhalt : this.inhalt) {
            if (anInhalt.length() > 3 && anInhalt.substring(0, 3).equals("+++")) {
                if (anInhalt.contains("+++description")) {
                    imBeschreibungsBereich = true;
                    imTestBereich = false;
                    imCodeBereich = false;
                }
                if (anInhalt.contains("+++code")) {
                    imCodeBereich = true;
                    imTestBereich = false;
                    imBeschreibungsBereich = false;
                }
                if (anInhalt.contains("+++test")) {
                    imTestBereich = true;
                    imBeschreibungsBereich = false;
                    imCodeBereich = false;
                }
            } else {
                if (imBeschreibungsBereich == true) {
                    this.beschrTeil += anInhalt + "\n";
                }
                if (imTestBereich == true) {
                    this.testTeil += anInhalt + "\n";
                }
                if (imCodeBereich == true) {
                    this.codeTeil += anInhalt + "\n";
                }
            }
        }
    }

    public String[] down(String[] buttons) {
        if (dateien.size() > 4) {
            if (!(buttons[3].equals(dateien.get(dateien.size() - 1)))) {
                int i = 0;
                boolean richtigeStelle = false;
                while (i < dateien.size() && !richtigeStelle) {
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
                while (i < dateien.size() && !richtigeStelle) {
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
        this.beschrTeil = "";
        this.testTeil = "";
        this.codeTeil = "";
    }
    public String gibInhalt(){
        try {
            String ausgabe = "";
            for (String s : inhalt) {
                ausgabe = ausgabe + s + "\n";
            }
            return ausgabe;
        }
        catch (Exception e){}
        return "";
    }

    public String replacer(String s){
        return s.replace("ae","\u00E4").replace("ue","\u00FC").replace("'Ue","\u00DC").replace("Oe","\u00D6");
    }

    public String gibBeschr(){return this.beschrTeil;}

    public String gibTestCode(){return this.testTeil;}

    public String gibCode(){return this.codeTeil;}

}
