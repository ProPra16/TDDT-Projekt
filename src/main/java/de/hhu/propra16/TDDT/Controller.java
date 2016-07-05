package de.hhu.propra16.TDDT;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import vk.core.api.*;
import java.util.concurrent.TimeUnit;

public class Controller {
    @FXML private TextArea Fenster;
    @FXML private Text Import1;
    @FXML private Text Import2;
    @FXML private Text Klassenname;
    @FXML private Button RED;
    @FXML private Button GREEN;
    @FXML private Button REFACTOR;
    @FXML private Label Clock;
    @FXML private Label Anzeige;
    @FXML private PieChart chart;
    private UserCode UserInput=new UserCode("");
    private ActionUnit Action=new ActionUnit(UserInput);
    private WarningUnit Reporter;
    private SetStyles PhaseSetter=new SetStyles();
    private String ActualGREENCode;
    private char Phase='R';
    private BabyStep babyStep;
    private TrackingUnit Tracker;

    public void RED() {
        if (Phase=='F') {
            isReadyForRED();
        }
        else {
            if (Phase=='G') {UserInput.setClass(ActualGREENCode);}
            switchRED();
            Fenster.setText(UserInput.getTestCode());
            UserInput.setTest(Fenster.getText());
            if (UserInput != null) {
                Fenster.setText(UserInput.getTestCode());
                UserInput.setTest(Fenster.getText());
            }
        }
    }

    public void switchRED() {
        Tracker.stopTime(Phase);
        setPhase('R',true);
        if (UserInput.isEmpty()) {
            UserInput.setTest("@Test\n"+"public void testsomething() {\n"+"\n}");
        }
        Fenster.setText(UserInput.getTestCode());
       if (babyStep!=null) babyStep.restart();
    }

    public void isReadyForRED() {
        UserInput.setClass(Fenster.getText());
        Action=new ActionUnit(UserInput);
        Action.compile();
        if (Action.compileErrors()) {
            String CompilerErrors=TestHelpers.getErrorMessages(Action.getCompiler(), Action.getResult());
            Reporter.showCompilerErrors(CompilerErrors,"Kompilier Fehler nach Refactoring");
        }
        else {
            Action.compileAndTest();
            if (Action.hasnoFailedTests()) {
                Phase='R';
                Fenster.clear();
                RED();
                Reporter.savedSettings();
            }
            else {
                Reporter.failedTests(Action.getFailedTests());
                Tracker.addEvent("Fehlschlagende Tests");
            }
        }
    }

    public void GREEN() {
        if (Phase=='G') { return;}
        else if (Phase=='F') {
            Reporter.commonError("Falsche Phase !","In REFACTOR sollst du nur den Code verbessern, die Tests laufen schon !");}
        else{
            String Input=Fenster.getText();
            if (Input==null) {Input="";}
            if (!Input.equals(UserInput.getTestCode())) {Tracker.addEvent("Tests geändert");}
            UserInput.setTest(Input);
            Action = new ActionUnit(UserInput);
            Action.checkGREEN();
            GREEN green = new GREEN(Action.getCompiler());
            if (!green.isready() && !green.wroteTests()) {
                Reporter.commonError("Keine Tests !",green.getError());
            }
            else if (!green.isready() && green.wroteTests()) {
                Reporter.NotOnlyOneFailing(green.isCompiled(),green.getFailures());
            }
            else {
                isreadyForGreen();
            }
        }
    }

    public void isreadyForGreen() {
        Tracker.stopTime(Phase);
        setPhase('G',false);
        ActualGREENCode=UserInput.getClassCode();
        Fenster.clear();
        Fenster.setText(UserInput.getClassCode());
       if (babyStep!=null) babyStep.restart();
    }

    public void REFACTOR() {
        char Actual=Phase;
            switch (Actual) {
                case 'R':   if (!Fenster.getText().equals(UserInput.getTestCode())) {
                            Tracker.addEvent("Tests geändert");}
                            UserInput.setTest(Fenster.getText());
                            break;
                case 'G':   if (!Fenster.getText().equals(UserInput.getClassCode())) {
                            Tracker.addEvent("Code bei GREEN geändert");}
                            UserInput.setClass(Fenster.getText());
                            break;
                case 'F':   return;
            }
        if (UserInput.getClassCode().equals("")) {Reporter.noCode(); return;}
        Action=new ActionUnit(UserInput);
        Action.compile();
        if (Action.compileErrors()) {
            String CompilerErrors=TestHelpers.getErrorMessages(Action.getCompiler(), Action.getResult());
            Reporter.showCompilerErrors(CompilerErrors,"Kompilier Fehler im GREEN-Code");
        }
        else {
            checkUserTestCases();
        }
    }

    public void checkUserTestCases() {
        Action.compileAndTest();
        if (Action.compileErrors()) {
            switchRED();
            String CompilerErrors=TestHelpers.getErrorMessages(Action.getCompiler(), Action.getResult());
            Reporter.showCompilerErrors(CompilerErrors,"Kompilier Fehler in den Tests");
        }
        else {
            if (Action.hasnoFailedTests()) {
                isreadyforREFACTOR(true);
            }
            else {
                Reporter.failedTests(Action.getFailedTests());
            }
        }
    }

    public void isreadyforREFACTOR(boolean report) {
        Tracker.stopTime(Phase);
        setPhase('F',false);
        Fenster.clear();
        Fenster.setText(UserInput.getClassCode());
        Clock.setText("");
      if (report) Reporter.readyforRefactor();
    }

    public void init(Controller controller,UserCode UserInput) {
        this.UserInput=UserInput;
        if (UserInput.hasBabySteps()) {
            Clock.setText(UserInput.getTime());
            babyStep=new BabyStep(UserInput.getTime(),controller);
        }
        Tracker=new TrackingUnit();
        Reporter=new WarningUnit(Tracker);
        switchRED();
        Klassenname.setText("public class "+UserInput.getTestName()+" {");
        if (babyStep!=null) {
           babyStep.countDown(Clock);
        }
    }

    public void switchPhase() {
        Tracker.addEvent("BabySteps Zeit abgelaufen");
        char Actual=Phase;
        switch (Actual) {
            case 'R':   checkSwitches();
                        break;
            case 'G':   switchRED();
                        break;
            case 'F':   babyStep.restart();
                        break;
        }
    }

    public void checkSwitches() {
        if (!(UserInput.getClassCode().equals(""))) {
            babyStep.restart(-1);
            isreadyforREFACTOR(false);
        }
        else {
            switchRED();
        }
    }

    public void setPhase(char Actual, boolean isTest) {
        Phase=Actual;
        PhaseSetter.setPhase(Phase, RED, GREEN, REFACTOR, Anzeige);
        if (!isTest) {
        Import1.setText("");
        Import2.setText("");
        Klassenname.setText("public class "+UserInput.getKlassenName()+" {");}
        else {
            Import1.setText("import static org.junit.Assert.*;");
            Import2.setText("import org.junit.Test;");
            Klassenname.setText("public class "+UserInput.getTestName()+" {");
        }
    }

    public char getPhase()  {
        return Phase;
    }

    public void backToMainScreen(ActionEvent event){
        TDDT main = new TDDT();
        final Node source = (Node) event.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        String UserChoice = event.getSource().toString();
        if(babyStep != null) {
            babyStep.stopTimer();
        }
        stage.close();
        main.starter();
    }

    public void show() throws InterruptedException {
        int ActualTime=0;
        if (babyStep!=null) {
            ActualTime = babyStep.getTime();
            babyStep.setUnvisible();
        }
        TextArea TrackingData= new TextArea(Tracker.getChartInfos(chart,Phase));
        TrackingData.setId("TrackingData");
        TrackingData.setEditable(false);
        TrackingData.setPrefWidth(500);
        Stage stage=setTrackingScene(TrackingData);
        stage.showAndWait();
        if (babyStep!=null) {
            while (stage.isShowing()) {
                TimeUnit.SECONDS.sleep(1);
            }
            babyStep.restart(ActualTime);
        }
    }

    public Stage setTrackingScene(TextArea TrackingData) {
        Stage stage=new Stage();
        Scene scene = new Scene(new GridPane());
        scene.getStylesheets().add("styler.css");
        ((GridPane) scene.getRoot()).add(chart,0,0);
        ((GridPane) scene.getRoot()).add(TrackingData,1,0);
        stage.setTitle("Deine Trackingstatistiken");
        stage.setWidth(1100);
        stage.setHeight(500);
        stage.setScene(scene);
        return stage;
    }
}