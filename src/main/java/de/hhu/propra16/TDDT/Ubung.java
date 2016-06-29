package de.hhu.propra16.TDDT;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Ubung {

    private ArrayList <String> files = new ArrayList<String>();
    private String [] buttons = new String[4];
    private String [] inhalt = new String [4];
    private String beschrTeil = "";
    private String codeTeil = "";

    public void filer(){
        File filer = new File("build\\classes\\main\\New\\Übungen");
        for(File file:filer.listFiles()) {
            files.add(file.getName());
        }
    }

    public String[] fillArray(){
        for (int i=0; i<4; i++){
            buttons[i]=files.get(i);
        }
        return buttons;
    }

    public int anzahlÜbungen(){
        return files.size();
    }

    public String[] down(String [] buttons){
        if(!(buttons[3].equals(files.get(files.size()-1)))){
            int i=0;
            boolean richtigeStelle=false;
            while(i<files.size() && richtigeStelle == false){
                if(buttons[0].equals(files.get(i))){
                    richtigeStelle=true;
                }
                else {
                    i++;
                }
            }
            for(int j=i; j<i+4; j++){
                buttons[j-i]=files.get(j+1);
            }
        }
        return buttons;
    }

    public String[] up(String [] buttons){
        if(!(buttons[0].equals(files.get(0)))){
            int i=0;
            boolean richtigeStelle=false;
            while(i<files.size() && richtigeStelle == false){
                if(buttons[0].equals(files.get(i))){
                    richtigeStelle=true;
                }
                else {
                    i++;
                }
            }
            for(int j=i; j<i+4; j++){
                buttons[j-i]=files.get(j-1);
            }
        }
        return buttons;
    }

    public void trenneTeile(String filename){
        String path = "build\\resources\\main\\New\\Übungen\\" + filename;
        File file=new File(path);
        try{
            List<String> list = Files.readAllLines(Paths.get(path), StandardCharsets.UTF_8);
            this.inhalt = list.toArray(new String[list.size()]);
        }
        catch (Exception e){}
        boolean imBereich = false;
        for(int i=0; i<inhalt.length; i++){
            if(this.inhalt[i].contains("+++description")) {
                if (imBereich == true) {
                    imBereich = false;
                } else {
                    imBereich = true;
                }
            }
            else{
                if(imBereich == true){
                    this.beschrTeil += this.inhalt[i];
              //      System.out.println(this.beschrTeil);
                } else{
                    this.codeTeil += this.inhalt[i];
             //       System.out.println(this.codeTeil);
                }
            }
        }
    }
    public void clearAll(){
        this.inhalt = null;
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