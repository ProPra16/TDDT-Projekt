package de.hhu.propra16.TDDT;

import javafx.scene.control.Alert;
import javafx.scene.control.Dialog;
import javafx.stage.Stage;


public class Warning {

    private Alert Action=new Alert(Alert.AlertType.ERROR);

    public void TestsWork(boolean isError) {
        if (!isError) {
            Action.setHeaderText("Tests klappen!");
            Action.setContentText("Deine Tests werden alle bestanden.\n" +
                    "Bitte sorge dafür, dass mind. ein Test fehlschlägt.");
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

    public void readyforRefactor(boolean allfine) {
        if (allfine) {
            Alert Info = new Alert(Alert.AlertType.INFORMATION);
            Info.setContentText("Ok, alle Tests werden erfüllt !\n Du kannst jetzt zu Refactor wechseln !");
            Action.showAndWait();
        }
        else {
            Action.setHeaderText("Tests schlagen fehl !");
            Action.setContentText("Nicht alle Tests sind erfolgreich ! \n Bitte sorge dafür, dass alle Tests laufen");
            Action.showAndWait();
        }
    }

}
