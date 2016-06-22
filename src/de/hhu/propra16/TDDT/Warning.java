package de.hhu.propra16.TDDT;

import javafx.scene.control.Alert;
import javafx.scene.control.Dialog;
import javafx.stage.Stage;


public class Warning {

    private Alert Action=new Alert(Alert.AlertType.ERROR);

    public void TestsWork(boolean isError, int Failures) {
        if (!isError) {
            Action.setContentText("Es schlagen "+Failures+" Tests fehl !\n" +
                    "Bitte sorge dafür, dass genau ein Test fehlschlägt.");
            Action.showAndWait();
        }
    }

    public void emptyField(boolean NoCode) {
        if (NoCode) {
            Action.setHeaderText("Keine Implementierung");
            Action.setContentText("Bitte implementiere die Tests!");
            Action.showAndWait();
        }
    }

    public void readyforRefactor() {
        Alert Info = new Alert(Alert.AlertType.INFORMATION);
        Info.setContentText("Ok, alle Tests wurden erfüllt !\n Du bist jetzt in Refactor !");
        Info.showAndWait();
    }

    public void failedTests() {
        Action.setHeaderText("Tests schlagen fehl !");
        Action.setContentText("Nicht alle Tests sind erfolgreich ! \n Bitte sorge dafür, dass alle Tests laufen");
        Action.showAndWait();
    }

    public void showCompilerErrors(String Errors) {
        Action.setHeaderText("Kompilier Fehler");
        Action.setContentText("Dein Programm konnte nicht kompiliert werden:\n"+Errors+
                "\nBitte behebe die Fehler !"

        );
        Action.showAndWait();
    }

    public void savedSettings() {
        Alert Info =new Alert(Alert.AlertType.INFORMATION);
        Info.setHeaderText("Alles OK!");
        Info.setContentText("Deine Änderungen wurden gespeichert\n"+
            "Alle Tests waren erfolgreich!"
        );
        Info.showAndWait();
    }

    public void inRED() {
        Action.setHeaderText("Falsche Phase");
        Action.setContentText("Du bist gerade noch in der RED Phase !\nErst nach der GREEN Phase ist REFACTORING möglich.");
        Action.showAndWait();
    }


}
