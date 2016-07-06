package de.hhu.propra16.TDDT;

import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class SetStyles {

    public void setPhase(char Phase, Button RED, Button GREEN, Button REFACTOR, Label Anzeige) {
        switch (Phase) {
            case 'R':setRED(RED,GREEN,REFACTOR,Anzeige); break;
            case 'G':setGREEN(RED,GREEN,REFACTOR,Anzeige); break;
            case 'F':setREFACTOR(RED,GREEN,REFACTOR, Anzeige);break;
        }
    }

    public void setRED(Button RED, Button GREEN, Button REFACTOR, Label Anzeige) {
        RED.setStyle("-fx-background-color: #FF0000;");
        GREEN.setStyle("");
        REFACTOR.setStyle("");
        Anzeige.setTranslateX(65);
        Anzeige.setText("Schreibe bitte genau einen fehlschlagenden Test");
        Anzeige.setStyle("-fx-text-fill:red");
    }

    public void setGREEN(Button RED, Button GREEN, Button REFACTOR, Label Anzeige) {
        RED.setStyle("");
        GREEN.setStyle("-fx-background-color: #00EE00;");
        REFACTOR.setStyle("");
        Anzeige.setTranslateX(100);
        Anzeige.setText("Schreibe nun Code, der alle Tests besteht");
        Anzeige.setStyle("-fx-text-fill:green");
    }

    public void setREFACTOR(Button RED, Button GREEN, Button REFACTOR, Label Anzeige) {
        RED.setStyle("");
        GREEN.setStyle("");
        REFACTOR.setStyle("-fx-text-fill:white; -fx-background-color: #000000;");
        Anzeige.setText("Du kannst nun den Code verbessern");
        Anzeige.setStyle("");

    }
}