package de.hhu.propra16.TDDT;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import vk.core.api.*;


public class StartController {
    private boolean ready;
    private Main m = new Main();
    @FXML private TextArea Fenster;

    public void CheckRED(ActionEvent event) {
        UserCode Test=new UserCode(Fenster.getText(), true);
        CompilationUnit Klasse=new CompilationUnit("Bar",Test.getClassContent(), false);
        CompilationUnit TestKlasse=new CompilationUnit("BarTest",Test.getTestContent(),true);
        JavaStringCompiler Compiler=CompilerFactory.getCompiler(Klasse, TestKlasse);
        Compiler.compileAndRunTests();
        GREEN NextStep=new GREEN(Compiler);
    }
    public void CheckGREEN(ActionEvent event) {
        UserCode Test=new UserCode(Fenster.getText(), true);
        CompilationUnit Klasse=new CompilationUnit("Bar",Test.getClassContent(), false);
        CompilationUnit TestKlasse=new CompilationUnit("BarTest",Test.getTestContent(),true);
        JavaStringCompiler Compiler=CompilerFactory.getCompiler(Klasse, TestKlasse);
        Compiler.compileAndRunTests();
        GREEN NextStep=new GREEN(Compiler);
    }
    public void CheckREFACTOR(ActionEvent event) {
        UserCode Test=new UserCode(Fenster.getText(), true);
        CompilationUnit Klasse=new CompilationUnit("Bar",Test.getClassContent(), false);
        CompilationUnit TestKlasse=new CompilationUnit("BarTest",Test.getTestContent(),true);
        JavaStringCompiler Compiler=CompilerFactory.getCompiler(Klasse, TestKlasse);
        Compiler.compileAndRunTests();
        GREEN NextStep=new GREEN(Compiler);
    }
}
