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
    private Alert Info=new Alert(Alert.AlertType.INFORMATION);
    private TrackingUnit Tracker;

    public WarningUnit() {}

    public WarningUnit(TrackingUnit Tracker) {
        this.Tracker=Tracker;
    }

    public void NotOnlyOneFailing(boolean Compiled, int Failures) {
        if (Compiled) {
            Tracker.addEvent("Nicht genau ein fehlschlagender Test");
            Action.setHeaderText("Falsche Tests !");
            Action.setContentText("Es schlagen "+Failures+" Tests fehl !\n" +
                    "Bitte sorge daf" +  "\u00FC" + "r, dass genau ein Test fehlschl" +  "\u00E4" + "gt.");
            Action.showAndWait();
        }
    }

    public void readyforRefactor() {
        Tracker.addEvent("Alle Tests erfolgreich, Refactor erreicht");
        Info.setContentText("Ok, alle Tests wurden erf" + "\u00FC" + "llt !\n Du bist jetzt in Refactor !");
        Info.showAndWait();
    }

    public void failedTests(TestResult Result) {
        Tracker.addEvent("Nicht bestandene Tests");
        String Message="";
        Collection<TestFailure> failures=Result.getTestFailures();
        for (TestFailure F:failures) {
            Message+="Fehler bei:\n"+F.getMethodName()+"\n";
            Message+=F.getMessage()+"\n";
        }
        Action.setHeaderText("Tests schlagen fehl !");
        Action.setContentText("Nicht alle Tests sind erfolgreich ! \n" + Message + "\n" +
                "Bitte sorge daf" + "\u00FC" + "r, dass alle Tests laufen");
        Action.showAndWait();
    }

    public void showCompilerErrors(String Errors,String Event) {
        Tracker.addEvent(Event);
        Action.setHeaderText("Kompilier Fehler");
        Action.setContentText("Dein Programm konnte nicht kompiliert werden:\n"+Errors+
                "\nBitte behebe die Fehler !"
        );
        Action.showAndWait();
    }

    public void savedSettings() {
        Tracker.addEvent("Refactoring verlassen, Code verbessert");
        Info.setHeaderText("Alles OK!");
        Info.setContentText("Deine " + "\u00C4" +  "nderungen wurden gespeichert\n"+
                "Alle Tests waren erfolgreich!"
        );
        Info.showAndWait();
    }

    public void commonError(String Header, String Content) {
        Action.setHeaderText(Header);
        Action.setContentText(Content);
        Action.showAndWait();
    }

    public void noCode() {
        Action.setHeaderText("Kein Code zum Refactoring da !");
        Action.setContentText("Nach erflogreichem Abschluss von GREEN sollte das Refactoring durchgef" + "\u00FC" + "hrt werden.");
        Action.showAndWait();
    }

    public String askForBabySteps() {
        List<String> Optionen = new ArrayList<>();
        Optionen.add("Keine BabySteps aktivieren");
        Optionen.add("0:12 Minuten");
        Optionen.add("2:00 Minuten");
        Optionen.add("2:30 Minuten");
        Optionen.add("3:00 Minuten");
        Optionen.add("4:00 Minuten");
        ChoiceDialog<String> dialog = new ChoiceDialog<>("Keine BabySteps aktivieren", Optionen);
        dialog.setTitle("BabySteps");
        dialog.setHeaderText("M" + "\u00F6" + "chtest du BabySteps aktivieren ?\n" +
                "Bei BabySteps wird die Zeit zum Code Schreiben limitiert.");
        dialog.setContentText("Bitte w" + "\u00E4" + "hle deine gew" + "\u00FC" + "nschte Zeit:");
        Optional<String> Auswahl = dialog.showAndWait();
        if (Auswahl.isPresent()) {
            return Auswahl.get();
        }
        return "";
    }

    public void folderError(){
        Action.setHeaderText("Ordner \"Ubungen\" fehlt!");
        Action.setContentText("Bitte erstellen Sie einen Ordner \"Ubungen\" mit entsprechenden \u00DCbungsdateien");
        Action.showAndWait();
    }
    public void fileError(){
        Action.setHeaderText("Bitte anderen Dateinamen ausw\u00E4hlen!");
        Action.setContentText("Bitte achte darauf, dass die \u00DCbungsdatei nicht \"Test\" hei\u00DFt.");
        Action.showAndWait();
    }

    public void timeUp() {
        Info.setTitle("Zeit ist um !");
        Info.setContentText("Deine Zeit fürs Programmieren ist leider abgelaufen\n"+
                "Du bist jetzt wieder in der vorherigen Phase");
        Info.showAndWait();
    }

    public boolean TimeUpisShowing() {
        return Info.isShowing();
    }
}