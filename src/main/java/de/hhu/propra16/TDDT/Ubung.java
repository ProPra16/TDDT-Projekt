package de.hhu.propra16.TDDT;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class Ubung {

    private ArrayList <String> dateien = new ArrayList<String>();
    private String [] buttons = new String[4];
    ArrayList <String> inhalt = new ArrayList<String>();
    private String beschrTeil = "";
    private String codeTeil = "";
    private int jarVar = 0;

    public String gibtDatei(){
        return dateien.get(0);
    }

    /*
    public void fileOut()  throws Exception {
        final String path = "Ubungen";
        final File jarFile = new File(getClass().getProtectionDomain().getCodeSource().getLocation().getFile());
        if (jarFile.isFile()) {
            final JarFile jar = new JarFile(jarFile);
            final Enumeration<JarEntry> entries = jar.entries();
            while (entries.hasMoreElements()) {
                final String name = entries.nextElement().getName();
                if (name.startsWith(path + "/")) {
                    int i = name.indexOf("Ubungen");
                    String dateiname = name.substring(i+8,name.length());
                    this.dateien.add(dateiname);
                }
            }
            this.jarVar = 1;
            this.dateien.remove(0);
            jar.close();
        } else {
            final URL url = getClass().getResource("/" + path);
            if (url != null) {
                try {
                    final File apps = new File(url.toURI());
                    for (File app : apps.listFiles()) {
                        String appy = app.toString();
                        int i = appy.indexOf("Ubungen");
                        String dateiname = appy.substring(i+8,appy.length());
                        this.dateien.add(dateiname);
                    }
                } catch (URISyntaxException ex) {}
            }
        }
    }
    */

    public void buttonNamer()throws Exception{
        String path = null;
        path = (new File(".").getCanonicalPath());
        File file = new File(path + "/Ubungen");
        File[] listOfFiles = file.listFiles();
        for(File file4 : listOfFiles){
            String pfad = file4.toString();
            int pos = pfad.indexOf("Ubungen");
            pfad = pfad.substring(pos+8,pfad.length());
            dateien.add(pfad);
        }
    }

    public String[] fillArray(){
        for (int i=0; i<buttons.length; i++){
            if(dateien.size() > i) {
                buttons[i] = dateien.get(i);
            }
            else{
                buttons[i]="";
            }
        }
        return buttons;
    }

    public int anzahlUbungen(){
        return dateien.size();
    }

    public String[] down(String [] buttons){
        if(dateien.size() > 4) {
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

    public String[] up(String [] buttons){
        if(dateien.size() > 4){
        if(!(buttons[0].equals(dateien.get(0)))){
            int i=0;
            boolean richtigeStelle=false;
            while(i<dateien.size() && richtigeStelle == false){
                if(buttons[0].equals(dateien.get(i))){
                    richtigeStelle=true;
                }
                else {
                    i++;
                }
            }
            for(int j=i; j<i+4; j++){
                buttons[j-i]=dateien.get(j-1);
            }
            return buttons;
        }
        }
        return null;
    }

    public void trenneTeile(String filename) throws Exception{

        /*
        String path = (getClass().getResource("/Ubungen").getFile() + "/" + filename);
        File file=new File(path);
        */
        String path = null;
        path = (new File(".").getCanonicalPath());
        File file = new File(path + "/Ubungen/" + filename);

        BufferedReader br = null;
        try {
            if(jarVar==1){
                br = new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream("Ubungen/" + filename)));
            }
            else {
                br = new BufferedReader(new FileReader(file));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try{
            String line;
            while((line = br.readLine()) != null){
                inhalt.add(line);
            }
        }
        catch (Exception e){}
        boolean imBereich = false;

        for(int i=0; i<this.inhalt.size(); i++){
            if(this.inhalt.get(i).contains("+++description")) {
                if (imBereich == true) {
                    imBereich = false;
                } else {
                    imBereich = true;
                }
            }
            else{
                if(imBereich == true){
                    this.beschrTeil += this.inhalt.get(i) + "\n";
                } else{
                    this.codeTeil += this.inhalt.get(i) + "\n";
                }
            }
        }

    }
    public void clearAll(){
        this.inhalt = new ArrayList<String>();
        this.codeTeil = "";
        this.beschrTeil = "";
    }

    public String gibCode(){
        return this.codeTeil;
    }
    public String gibBeschr(){
        return this.beschrTeil;
    }

}
