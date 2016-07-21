package de.hhu.propra16.TDDT;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import vk.core.api.*;
import java.util.concurrent.TimeUnit;

public class Controller {
    @FXML private TextArea Fenster;
    @FXML private Text Imports;
    @FXML private Text Klassenname;
    @FXML private Button RED;
    @FXML private Button GREEN;
    @FXML private Button REFACTOR;
    @FXML private Label Clock;
    @FXML private Label Anzeige;
    @FXML private PieChart chart;
    private String [] Inhalte = {"",""};
    private UserCode UserInput=new UserCode("", Inhalte);
    private ActionUnit Action=new ActionUnit(UserInput);
    private WarningUnit Reporter;
    private PhaseSetter phasesetter=new PhaseSetter();
    private String ActualGREENCode;
    private char Phase='R';
    private BabyStep babyStep;
    private TrackingUnit Tracker;
    private Stage myStage;
    private long trackerStart;

    public void setStage(Stage stage) {
        myStage = stage;
    }

    public void init(Controller controller,UserCode UserInput) {
        this.UserInput=UserInput;
        Tracker=new TrackingUnit();
        Reporter=new WarningUnit(Tracker);
        if (UserInput.hasBabySteps()) {
            Clock.setText(UserInput.getTime());
            babyStep=new BabyStep(UserInput.getTime(),controller);
        }
        switchRED();
        Klassenname.setText("public class "+UserInput.getTestName()+" {");
        if (babyStep!=null) {
            babyStep.countDown(Clock);
        }
    }
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
                Saver.codesave(UserInput);
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
        if (Phase=='G') {return;
        }
        else if (Phase=='F') {
            Reporter.commonError("Falsche Phase !","In REFACTOR sollst du nur den Code verbessern, die Tests laufen schon !");}
        else{
            String Input=Fenster.getText();
            if (Input==null) {Input="";}
            if (!Input.equals(UserInput.getTestCode())) {Tracker.addEvent("Tests ge" + "\u00E4"+ "ndert");}
            UserInput.setTest(Input);
            Action = new ActionUnit(UserInput);
            Action.checkGREEN();
            if (Action.causedSeriousError()) {
                isreadyForGreen();
                return;
            }
            GreenValidator greenValidator = new GreenValidator(Action.getCompiler());
            if (greenValidator.causedSeriousError()) {
                Reporter.commonError("Tests sind fehlerhaft !","Bitte nochmal die Tests anpassen !");
            }
            else if (!greenValidator.isValid() && !greenValidator.foundTests()) {
                Reporter.commonError("Keine Tests !", greenValidator.getError());
            }
            else if (!greenValidator.isValid() && greenValidator.foundTests()) {
                Reporter.NotOnlyOneFailing(greenValidator.isCompiled(), greenValidator.getFailures());
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
        if (babyStep!=null)
            babyStep.restart();
    }

    public void REFACTOR() {
        char Actual=Phase;
        switch (Actual) {
            case 'R':   if (!Fenster.getText().equals(UserInput.getTestCode())) {
                Tracker.addEvent("Tests ge" + "\u00E4"+ "ndert");}
                UserInput.setTest(Fenster.getText());
                break;
            case 'G':   if (!Fenster.getText().equals(UserInput.getClassCode())) {
                Tracker.addEvent("Code bei GREEN ge" + "\u00E4"+ "ndert");}
                UserInput.setClass(Fenster.getText());
                break;
            case 'F': return;
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
        if (Action.causedSeriousError()) {
            switchRED();
            String Error="In den Tests sind unpassende Parameter zu den zu testenden Funktionen !\n";
            Reporter.showCompilerErrors(Error,"Kompilier Fehler in den Tests");
            return;
        }
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

    public void switchPhase() {
        if (!Tracker.isShowing()){ Tracker.addEvent("BabySteps Zeit abgelaufen");}
        if (Phase!='F') {
            switchRED();
        }
        else {
            babyStep.restart();
        }
    }

    public void setPhase(char Actual, boolean isTest) {
        Phase=Actual;
        phasesetter.setPhase(Phase, RED, GREEN, REFACTOR, Anzeige);
        if (!isTest) {
            Imports.setText("");
            Klassenname.setText("public class "+UserInput.getKlassenName()+" {");}
        else {
            Imports.setText("import static org.junit.Assert.*;\n"+"import org.junit.Test;");
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
        if(babyStep != null) {
            babyStep.stopTimer();
        }
        stage.close();
        main.starter();
    }

    public void showTracker() throws InterruptedException {
        trackerStart = System.currentTimeMillis();
        int ActualTime=0;
        if (babyStep!=null) {
            ActualTime = babyStep.getTime();
            babyStep.setUnvisible();
        }
        TextArea TrackingData= new TextArea(Tracker.getChartInfos(chart,Phase));
        TrackingData.setId("TrackingData");
        TrackingData.setMouseTransparent(true);
        TrackingData.setEditable(false);
        TrackingData.setPrefWidth(500);
        Tracker.setTrackerIsShowing(true);
        Stage stage=setTrackingScene(TrackingData);
        myStage.hide();
        Saver.timesaver(Tracker);
        stage.showAndWait();
        Tracker.setCorrection(trackerStart);
        myStage.show();
        if (babyStep!=null) {
            while (stage.isShowing()) {
                TimeUnit.SECONDS.sleep(1);
            }
            babyStep.restart(ActualTime);
        }
    }

    public Stage setTrackingScene(TextArea TrackingData) {
        Stage stage =new Stage();
        stage.setResizable(false);
        Scene scene = new Scene(new GridPane());
        scene.getStylesheets().add("styler.css");
        Button backbutton = new Button();
        backbutton.minWidth(100);
        backbutton.setTranslateX(-40);
        backbutton.setTranslateY(30);
        backbutton.setText("Zur\u00FCck zu TDDT");
        backbutton.minHeight(50);
        backbutton.setOnAction(e -> {
            if(babyStep != null) babyStep.clear();
            Tracker.setTrackerIsShowing(false);
            stage.close();
        });
        ((GridPane) scene.getRoot()).add(chart,0,0);
        ((GridPane) scene.getRoot()).add(TrackingData,1,0);
        ((GridPane) scene.getRoot()).add(backbutton,1,2);
        stage.setTitle("Deine Trackingstatistiken");
        stage.setWidth(1100);
        stage.setHeight(500);
        stage.setScene(scene);
        return stage;
    }

    public WarningUnit getWarningUnit() {
        return Reporter;
    }
}