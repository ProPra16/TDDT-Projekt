package de.hhu.propra16.TDDT;

import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceDialog;
import vk.core.api.TestFailure;
import vk.core.api.TestResult;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;


public class WarningUnit {

    private Alert Action=new Alert(Alert.AlertType.ERROR);

    public WarningUnit() {}

    public void NotOnlyOneFailing(boolean Compiled, int Failures) {
        if (Compiled) {
            Action.setContentText("Es schlagen "+Failures+" Tests fehl !\n" +
                    "Bitte sorge dafuer, dass genau ein Test fehlschlaegt.");
            Action.showAndWait();
        }
    }

    public void readyforRefactor() {
        Alert Info = new Alert(Alert.AlertType.INFORMATION);
        Info.setContentText("Ok, alle Tests wurden erfuellt !\n Du bist jetzt in Refactor !");
        Info.showAndWait();
    }

    public void failedTests(TestResult Result) {
        String Message="";
        Collection<TestFailure> failures=Result.getTestFailures();
        for (TestFailure F:failures) {
            Message+="Fehler bei:\n"+F.getMethodName()+"\n";
            Message+=F.getMessage()+"\n";
        }
        Action.setHeaderText("Tests schlagen fehl !");
        Action.setContentText("Nicht alle Tests sind erfolgreich ! \n" + Message + "\n" +
                "Bitte sorge dafuer, dass alle Tests laufen");
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
        Info.setContentText("Deine Aenderungen wurden gespeichert\n"+
                "Alle Tests waren erfolgreich!"
        );
        Info.showAndWait();
    }

    public void commonError(String Header, String Content) {
        Action.setHeaderText(Header);
        Action.setContentText("\n"+Content);
        Action.showAndWait();
    }

    public void noCode() {
        Action.setHeaderText("Kein Code zum Refactoring da !");
        Action.setContentText("Nach erflogreichem Abschluss von GREEN sollte das Refactoring durchgeführt werden.");
        Action.showAndWait();
    }

    public String askForBabySteps() {
        List<String> Optionen=new ArrayList<>();
        Optionen.add("Keine BabySteps aktivieren");
        Optionen.add("1 Minute");
        Optionen.add("2 Minuten");
        Optionen.add("3 Minuten");
        ChoiceDialog<String> dialog=new ChoiceDialog<>("Keine BabySteps aktivieren",Optionen);
        dialog.setTitle("BabySteps");
        dialog.setHeaderText("Möchtest du BabySteps aktivieren ?\n" +
                "Bei BabySteps wird die Zeit zum Code Schreiben limitiert.");
        dialog.setContentText("Bitte wähle deine gewünschte Zeit:");
        Optional<String> Auswahl=dialog.showAndWait();
        if (Auswahl.isPresent()) {
            return Auswahl.get();
        }
        return "Keine BabySteps aktivieren";
    }
}