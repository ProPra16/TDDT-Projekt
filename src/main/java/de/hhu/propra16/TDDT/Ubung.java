package de.hhu.propra16.TDDT;

import java.io.*;
import java.util.ArrayList;

public class Ubung {

    private ArrayList<String> dateien = new ArrayList<String>();
    private String[] buttons = new String[4];
    ArrayList<String> inhalt = new ArrayList<String>();
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
        file = new File(path + "/Ubungen");
        if(!file.exists()){
            error.folderError();
        }
        else {
            File[] listOfFiles = file.listFiles();
            for (File file4 : listOfFiles) {
                String pfad = file4.toString();
                int pos = pfad.indexOf("Ubungen");
                pfad = pfad.substring(pos + 8, pfad.length());
                dateien.add(pfad);
            }
        }
    }

    public void buttonNamerForJava() throws Exception {
        path = (new File(".").getCanonicalPath() + "/build/libs/Ubungen");
        file = new File(path);
        if(!file.exists()){
            error.folderError();
        }
        else{
            File[] listOfFiles = file.listFiles();
            for (File file4 : listOfFiles) {
                String pfad = file4.toString();
                int pos = pfad.indexOf("Ubungen");
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
        String directory = "Ubungen/";
        if(istInUbung == false){
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
            } catch (Exception e) {
            }
        }
    }

    public void trenneTeile() {
        boolean imBeschreibungsBereich = false;
        boolean imTestBereich = false;
        boolean imCodeBereich = false;
        for (int i = 0; i < this.inhalt.size(); i++) {
            if(this.inhalt.get(i).length()> 3 && this.inhalt.get(i).substring(0,3).equals("+++")) {
                if (this.inhalt.get(i).contains("+++description")) {
                    imBeschreibungsBereich = true;
                    imTestBereich = false;
                    imCodeBereich = false;
                }
                if (this.inhalt.get(i).contains("+++code")) {
                    imCodeBereich = true;
                    imTestBereich = false;
                    imBeschreibungsBereich = false;
                }
                if (this.inhalt.get(i).contains("+++test")) {
                    imTestBereich = true;
                    imBeschreibungsBereich = false;
                    imCodeBereich = false;
                }
            }
            else{
                if(imBeschreibungsBereich == true) {
                    this.beschrTeil += this.inhalt.get(i) + "\n";
                }
                if(imTestBereich == true) {
                    this.testTeil += this.inhalt.get(i) + "\n";
                }
                if(imCodeBereich == true) {
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
