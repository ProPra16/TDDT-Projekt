package de.hhu.propra16.TDDT;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.junit.Test;
import vk.core.api.*;
import vk.core.internal.InternalCompiler;

public class Controller {
    private boolean ready;
    @FXML private TextArea Fenster;
    @FXML private Text Import1;
    @FXML private Text Import2;
    @FXML private Text Klassenname;
    @FXML private Button CheckTests;
    private UserCode Test;
    private CompilationUnit Klasse;
    private CompilationUnit TestKlasse;
    private JavaStringCompiler Compiler;
    private Warning Errors= new Warning();

    public void CheckRED(ActionEvent event) {
        Test=new UserCode(Fenster.getText(),true);
        TestKlasse=new CompilationUnit(Test.getTestName(),Test.getTestContent(),true);
        Klasse=new CompilationUnit(Test.getKlassenName(),Test.getClassContent(),false);
        Compiler=CompilerFactory.getCompiler(Klasse, TestKlasse);
        Compiler.compileAndRunTests();
        GREEN green=new GREEN(Test,Compiler);
        if (green.isready() && !Test.isEmpty()) {
            readyForGreen();
        }
    }

    public void readyForGreen() {
        Import1.setText("");
        Import2.setText("");
        Klassenname.setText(Test.setHeaderKlasse());
        Fenster.clear();
    }

    public void checkTests() {
        Test.editCode(Fenster);
        Klasse = new CompilationUnit(Test.getKlassenName(), Test.getClassContent(), false);
        Compiler = CompilerFactory.getCompiler(Klasse, TestKlasse);
        Compiler.compileAndRunTests();
        TestResult Checker = Compiler.getTestResult();
        Errors.readyforRefactor(Checker.getNumberOfFailedTests() == 0);
    }
}
