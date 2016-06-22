package de.hhu.propra16.TDDT;

import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * Created by Asri on 22.06.2016.
 */
public class SetStyles {

    public void setRED(Button RED, Button GREEN, Button REFACTOR) {
        RED.setStyle("-fx-background-color: #FF0000;");
        GREEN.setStyle("");
        REFACTOR.setStyle("");
    }

    public void setGREEN(Button RED, Button GREEN, Button REFACTOR) {
        RED.setStyle("");
        GREEN.setStyle("-fx-background-color: #00EE00;");
        REFACTOR.setStyle("");
    }

    public void setREFACTOR(Button RED, Button GREEN, Button REFACTOR) {
        RED.setStyle("");
        GREEN.setStyle("");
        REFACTOR.setStyle("-fx-text-fill:white; -fx-background-color: #000000;");

    }

    public void setREDInfos(Label Anzeige) {
        Anzeige.setText("");
        Anzeige.setText("Du bist jetzt in der RED Phase.\n" +
                "Du sollst jetzt genau einen fehlschlagenden Test schreiben.\n" +
                "Wenn du meinst, du hast dies erfüllt, dann wechsle zur GREEN Phase");
    }

    public void setGREENInfos(Label Anzeige) {
        Anzeige.setText("");
        Anzeige.setText("Du bist jetzt in der GREEN Phase.\n" +
                "Du sollst nun den vorhin fehlschlagenden Test zum Laufen bringen.\n" +
                "Wenn du meinst, du hast dues erfüllt, dann wechsle zur REFACTORING Phase.\n"+
                "Andernfalls kannst du nochmal zu RED wechseln, bedenke hierbei, dass Änderungen verloren gehen.");
    }
}
