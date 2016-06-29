package de.hhu.propra16.TDDT;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import vk.core.api.*;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller {
    @FXML private GridPane Board;
    @FXML private TextArea Fenster;
    @FXML private Text Import1;
    @FXML private Text Import2;
    @FXML private Text Klassenname;
    @FXML private Button RED;
    @FXML private Button GREEN;
    @FXML private Button REFACTOR;
    private UserCode UserInput=new UserCode("");
    private JavaStringCompiler Compiler;
    private ActionUnit Action=new ActionUnit(UserInput,Compiler);
    private WarningUnit Reporter = new WarningUnit();
    private SetStyles PhaseSetter=new SetStyles();
    private String ActualGREENCode;
    private char Phase='R';
    private BabyStep babyStep;
    protected Label Clock;
    private boolean expired=false;

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
        Phase = 'R';
        Import1.setText("import static org.junit.Assert.*;");
        Import2.setText("import org.junit.Test;");
        PhaseSetter.setRED(RED,GREEN,REFACTOR);
        Klassenname.setText("public class "+UserInput.getTestName()+" {");
        PhaseSetter.setRED(RED, GREEN, REFACTOR);
        Import1.setText("import static org.junit.Assert.*;");
        Import2.setText("import org.junit.Test;");
        if (UserInput.isEmpty()) {
            UserInput.setTest("@Test\n"+"public void testsomething() {\n"+"\n}");
        }
        Fenster.setText(UserInput.getTestCode());
        babyStep.restart(UserInput.getTime());
        System.out.println("Switched !");
    }

    public void isReadyForRED() {
        UserInput.setClass(Fenster.getText());
        if (Action.hasErrors(UserInput)) {
            Reporter.showCompilerErrors(TestHelpers.getErrorMessages(Action.getCompiler(),Action.getResult()));
        }
        else {
            if (Action.isallFine(UserInput)) {
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
        if (Phase=='G') { Reporter.commonError("Falsche Phase !","Du bist schon in GREEN !");}
        else if (Phase=='F') {
            Reporter.commonError("Falsche Phase !","In REFACTOR sollst du nur den Code verbessern, die Tests laufen schon !");}
        else{
            String Input=Fenster.getText();
            if (Input==null) {Input="";}
            UserInput.setTest(Input);
            Action = new ActionUnit(UserInput, Compiler);
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
        Phase = 'G';
        PhaseSetter.setGREEN(RED, GREEN, REFACTOR);
        Import1.setText("");
        Import2.setText("");
        Klassenname.setText("public class "+UserInput.getKlassenName()+"{");
        ActualGREENCode=UserInput.getClassCode();
        Fenster.clear();
        Fenster.setText(UserInput.getClassCode());
        babyStep.restart(UserInput.getTime());
    }

    public void REFACTOR() {
        if (Phase!='R' && Phase!='F') {
            UserInput.setClass(Fenster.getText());
            if (Action.hasErrors(UserInput)) {
              Reporter.showCompilerErrors(TestHelpers.getErrorMessages(Action.getCompiler(), Action.getResult()));
            }
            else {
                checkUserTestCases();
            }
        }
        else {
            Reporter.commonError("Falsche Phase !","Du kannst nur nach erfolgreichem Abschluss von GREEN nach REFACTOR wechseln !");
        }
    }

    public void checkUserTestCases() {
        if (Action.hasErrors(UserInput)) {
            switchRED();
            Reporter.showCompilerErrors(TestHelpers.getErrorMessages(Action.getCompiler(),Action.getResult()));
        }
        else {
            if (Action.isallFine(UserInput)) {
                Phase = 'F';
                PhaseSetter.setREFACTOR(RED, GREEN, REFACTOR);
                Reporter.readyforRefactor();
            }
            else {
                Reporter.failedTests(Action.getFailedTests());
            }
        }
    }

    public void init(Controller controller,UserCode UserInput) {
        this.UserInput=UserInput;
        if (UserInput.hasBabySteps()) {
            Clock=new Label(UserInput.getTime());
            Board.getChildren().add(Clock);
            Clock.setTranslateX(300);
            Clock.setTranslateY(100);
            babyStep=new BabyStep(controller);
        }
        switchRED();
        Klassenname.setText("public class "+UserInput.getTestName()+" {");
        if (babyStep!=null) {
           babyStep.countDown(Clock,UserInput.getTime());
        }
    }

    public char switchPhase() {
        switch (Phase) {
            case 'R':
                if (Action.isallFine(UserInput)) {
                    Phase = 'F';
                    PhaseSetter.setREFACTOR(RED, GREEN, REFACTOR);
                    Fenster.clear();
                    Fenster.setText(UserInput.getClassCode());
                } else {
                    switchRED();
                }
            case 'G':
                switchRED();
        }
        return Phase;
    }
}