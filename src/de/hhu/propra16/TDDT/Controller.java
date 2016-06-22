package de.hhu.propra16.TDDT;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
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
    private UserCode Test;

    public void CheckRED(ActionEvent event) {
        Test=new UserCode(Fenster.getText(),true);
        CompilationUnit Klasse=new CompilationUnit(Test.getKlassenName(),Test.getClassContent(), false);
        CompilationUnit TestKlasse=new CompilationUnit(Test.getTestName(),Test.getTestContent(),true);
        JavaStringCompiler Compiler=CompilerFactory.getCompiler(Klasse, TestKlasse);
        Compiler.compileAndRunTests();
        GREEN green=new GREEN(Test,Compiler);
        if (green.isready()) {
            readyForGreen();
        }
    }

    public void readyForGreen() {
        Import1.setText("");
        Import2.setText("");
        Klassenname.setText(Test.setHeaderKlasse());
        Fenster.clear();
    }
}
