package de.hhu.propra16.TDDT;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;
import org.junit.Test;
import vk.core.api.*;
import vk.core.internal.InternalCompiler;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


public class Controller {
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
