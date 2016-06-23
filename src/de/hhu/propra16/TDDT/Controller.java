package de.hhu.propra16.TDDT;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import vk.core.api.*;

public class Controller {
    @FXML private TextArea Fenster;
    @FXML private Text Import1;
    @FXML private Text Import2;
    @FXML private Text Klassenname;
    @FXML private Button RED;
    @FXML private Button GREEN;
    @FXML private Button REFACTOR;
    private UserCode UserInput=new UserCode();
    private JavaStringCompiler Compiler;
    private ActionUnit Action=new ActionUnit(UserInput,Compiler);
    private WarningUnit Reporter = new WarningUnit();
    private SetStyles PhaseSetter=new SetStyles();
    private String ActualGREENCode;
    private char Phase='R';

    public void RED() {
        if (Phase=='F') {
            isReadyForRED();
        }
        else {
            if (Phase=='G') {UserInput.setClass(ActualGREENCode);}
            Phase = 'R';
            switchRED();
            PhaseSetter.setRED(RED, GREEN, REFACTOR);
            if (UserInput != null) {
                Fenster.setText(UserInput.getTestCode());
                UserInput.setTest(Fenster.getText());
            }
        }
    }

    public void switchRED() {
        Import1.setText("import static org.junit.Assert.*;");
        Import2.setText("import org.junit.Test;");
        Klassenname.setText("public class BarTest {");
        if (UserInput.isEmpty()) {
            UserInput.setTest("@Test\n"+"public void testsomething() {\n"+"\n}");
        }
        Fenster.setText(UserInput.getTestCode());
    }

    public void isReadyForRED() {
        UserInput.setClass(Fenster.getText());
        Action.checkREFACTOR(UserInput);
        if (!Action.isallFine()) {
            Reporter.showCompilerErrors(TestHelpers.getErrorMessages(Action.getCompiler(),Action.getResult()));
        }
        else {
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
                    readyForGreen();
                }
        }
    }

    public void readyForGreen() {
        Phase = 'G';
        PhaseSetter.setGREEN(RED, GREEN, REFACTOR);
        Import1.setText("");
        Import2.setText("");
        Klassenname.setText(UserInput.setHeaderKlasse());
        ActualGREENCode=UserInput.getClassCode();
        Fenster.clear();
        Fenster.setText(UserInput.getClassCode());
    }

    public void REFACTOR() {
        if (Phase!='R' && Phase!='F') {
            UserInput.setClass(Fenster.getText());
            Action.checkClass(UserInput);
            if (!Action.isallFine()) {
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
        Action.checkREFACTOR(UserInput);
        if (!Action.isallFine()) {
            Phase='R';
            switchRED();
            PhaseSetter.setRED(RED,GREEN,REFACTOR);
            Reporter.showCompilerErrors(TestHelpers.getErrorMessages(Action.getCompiler(),Action.getResult()));
        }
        else {
            if (Action.hasnoFailedTests()) {
                Phase = 'F';
                PhaseSetter.setREFACTOR(RED, GREEN, REFACTOR);
                Reporter.readyforRefactor();
            }
            else {
                Reporter.failedTests(Action.getFailedTests());
            }
        }
    }
}
