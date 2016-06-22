package de.hhu.propra16.TDDT;

import com.sun.org.apache.regexp.internal.RE;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;
import vk.core.api.*;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


public class Controller {
    private boolean ready;
    private TDDT m = new TDDT();
    @FXML private TextArea Fenster;
    @FXML private Text Import1;
    @FXML private Text Import2;
    @FXML private Text Klassenname;
    @FXML private Label Hinweise;
    @FXML private Button RED;
    @FXML private Button GREEN;
    @FXML private Button REFACTOR;
    private UserCode UserInput=new UserCode();
    private CompilationUnit Klasse;
    private CompilationUnit TestKlasse;
    private JavaStringCompiler Compiler;
    private ActionUnit Action=new ActionUnit(UserInput,Compiler);
    private Warning Errors= new Warning();
    private SetStyles PhaseSetter=new SetStyles();
    private char Phase='R';

    public void RED() {
        if (Phase=='F') {
            isReadyForRED();
        }
        else {
            Phase = 'R';
            switchtoRED();
            PhaseSetter.setRED(RED, GREEN, REFACTOR);
            if (UserInput != null) {
                Fenster.setText(UserInput.getTestCode());
                UserInput.setTest(Fenster.getText());
            }
        }
    }

    private void isReadyForRED() {
        UserInput.setClass(Fenster.getText());
        Action.checkClasses(UserInput);
        Compiler=Action.getCompiler();
        CompilerResult Result=Action.getResult();
        if (Result.hasCompileErrors()) {
            Errors.showCompilerErrors(TestHelpers.getErrorMessages(Compiler,Result));
        }
        else {
            TestResult PassedTests = Compiler.getTestResult();
            boolean readyForRED=PassedTests.getNumberOfFailedTests()==0;
            if (readyForRED) {
                Fenster.clear();
                Phase='R';
                RED();
                Errors.savedSettings();
            }
            else {
                Errors.failedTests();
            }
        }
    }

    public void GREEN() {
        if (Phase!='F') {
            UserInput.setTest(Fenster.getText());
            Action = new ActionUnit(UserInput, Compiler);
            Action.newGREENStartup();
            Compiler = Action.getCompiler();
            GREEN green = new GREEN(UserInput, Compiler);
            if (green.isready() && !UserInput.isEmpty()) {
                readyForGreen();
                Phase = 'G';
                PhaseSetter.setGREEN(RED, GREEN, REFACTOR);
            }
        }
    }

    public void readyForGreen() {
        Import1.setText("");
        Import2.setText("");
        Klassenname.setText(UserInput.setHeaderKlasse());
        Fenster.clear();
        Fenster.setText(UserInput.getClassCode());
    }

    public void checkUserClass() {
        if (Phase!='R' && Phase!='F') {
            UserInput.setClass(Fenster.getText());
            Action.checkClass(UserInput);
            Compiler = Action.getCompiler();
            CompilerResult Result = Action.getResult();
            if (Result.hasCompileErrors()) {
                Errors.showCompilerErrors(TestHelpers.getErrorMessages(Compiler, Result));
            } else {
                checkUserTestCases();
            }
        }
        else {
            Errors.inRED();
        }
    }

    public void checkUserTestCases() {
        Action.checkClasses(UserInput);
        Compiler=Action.getCompiler();
        CompilerResult Result=Action.getResult();
        if (Result.hasCompileErrors()) {
            switchtoRED();
            PhaseSetter.setRED(RED,GREEN,REFACTOR);
            Errors.showCompilerErrors(TestHelpers.getErrorMessages(Compiler,Result));
        }
        else {
            TestResult PassedTests = Compiler.getTestResult();
            boolean readyForRefactor=PassedTests.getNumberOfFailedTests()==0;
            if (readyForRefactor) {
                Errors.readyforRefactor();
                PhaseSetter.setREFACTOR(RED, GREEN, REFACTOR);
                Phase = 'F';
            }
            else {
                Errors.failedTests();
            }
        }
    }

    public void switchtoRED() {
        Import1.setText("import static org.junit.Assert.*;");
        Import2.setText("import org.junit.Test;");
        Klassenname.setText("public class BarTest {");
        Fenster.setText(UserInput.getTestCode());
    }
}
