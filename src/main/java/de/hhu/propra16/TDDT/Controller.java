package de.hhu.propra16.TDDT;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import vk.core.api.*;

public class Controller {
    @FXML private TextArea Fenster;
    @FXML private Text Import1;
    @FXML private Text Import2;
    @FXML private Text Klassenname;
    @FXML private Label Anzeige;
    @FXML private Button RED;
    @FXML private Button GREEN;
    @FXML private Button REFACTOR;
    @FXML private Label Clock;
    private UserCode UserInput=new UserCode("");
    private ActionUnit Action=new ActionUnit(UserInput);
    private WarningUnit Reporter = new WarningUnit();
    private SetStyles PhaseSetter=new SetStyles();
    private String ActualGREENCode;
    private char Phase='R';
    private BabyStep babyStep;

    public void RED() {
        Anzeige.setTranslateX(25);
        Anzeige.setText("Schreiben Sie nun mindestens einen fehlschlagenden Test");
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
        setPhase('R',true);
        if (UserInput.isEmpty()) {
            UserInput.setTest("@Test\n"+"public void testsomething() {\n"+"\n}");
        }
        Anzeige.setText("Schreiben Sie nun mindestens einen fehlschlagenden Test");
        Fenster.setText(UserInput.getTestCode());
       if (babyStep!=null) babyStep.restart();
    }

    public void isReadyForRED() {
        UserInput.setClass(Fenster.getText());
        Action=new ActionUnit(UserInput);
        Action.compile();
        if (Action.compileErrors()) {
            Reporter.showCompilerErrors(TestHelpers.getErrorMessages(Action.getCompiler(),Action.getResult()));
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
            }
        }
    }

    public void GREEN() {

        Anzeige.setTranslateX(100);
        Anzeige.setText("Schreiben Sie nun den zu testenden Code");
    //    Anzeige.setStyle("-fx-text-fill:green");

        if (Phase=='G') { Reporter.commonError("Falsche Phase !","Du bist schon in GREEN !");}
        else if (Phase=='F') {
            Reporter.commonError("Falsche Phase !","In REFACTOR sollst du nur den Code verbessern, die Tests laufen schon !");}
        else{
            String Input=Fenster.getText();
            if (Input==null) {Input="";}
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
        setPhase('G',false);
        ActualGREENCode=UserInput.getClassCode();
        Fenster.clear();
        Fenster.setText(UserInput.getClassCode());
       if (babyStep!=null) babyStep.restart();
    }

    public void REFACTOR() {
        char Actual=Phase;
            switch (Actual) {
                case 'R':UserInput.setTest(Fenster.getText()); break;
                case 'G':UserInput.setClass(Fenster.getText()); break;
                case 'F':return;
            }
        if (UserInput.getClassCode().equals("")) {Reporter.noCode(); return;}
        Action=new ActionUnit(UserInput);
        Action.compile();
        if (Action.compileErrors()) {
            Reporter.showCompilerErrors(TestHelpers.getErrorMessages(Action.getCompiler(), Action.getResult()));
        }
        else {
            checkUserTestCases();
        }
    }

    public void checkUserTestCases() {
        Action.compileAndTest();
        if (Action.compileErrors()) {
            switchRED();
            Reporter.showCompilerErrors(TestHelpers.getErrorMessages(Action.getCompiler(),Action.getResult()));
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
        setPhase('F',false);
        Fenster.clear();
        Fenster.setText(UserInput.getClassCode());
        Anzeige.setText("Sie koennen nun Ihre Tests verbessern");
        Clock.setText("");
      if (report) Reporter.readyforRefactor();
    }

    public void init(Controller controller,UserCode UserInput) {
        this.UserInput=UserInput;

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

    public void switchPhase() {
        char Actual=Phase;
        switch (Actual) {
            case 'R':checkSwitches();break;
            case 'G':switchRED();
            case 'F':babyStep.restart();
        }
    }

    public void checkSwitches() {
        if (!(UserInput.getClassCode().equals(""))) {
            babyStep.restart();
            isreadyforREFACTOR(false);
        }
        else {
            switchRED();
        }
    }

    public void setPhase(char Actual, boolean isTest) {
        Phase=Actual;
        PhaseSetter.setPhase(Phase,RED, GREEN, REFACTOR);
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
}