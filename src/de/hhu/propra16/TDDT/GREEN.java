package de.hhu.propra16.TDDT;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import vk.core.api.CompilerResult;
import vk.core.api.JavaStringCompiler;
import vk.core.api.TestResult;

public class GREEN {
    private JavaStringCompiler compiler;
    private UserCode UserTest;
    private Warning Errors=new Warning();
    private boolean ready;
    private int Failures;

    public GREEN(UserCode UserTest, JavaStringCompiler compiler) {
        this.compiler = compiler;
        this.UserTest=UserTest;
        checkifReady();
        Errors.TestsWork(ready, Failures);
        Errors.emptyField(UserTest.isEmpty());
    }

    public void checkifReady() {
        CompilerResult UserCode = compiler.getCompilerResult();
        if (UserCode.hasCompileErrors()) {
            ready = true;
        }
        else {
            TestResult Tests = compiler.getTestResult();
             Failures= Tests.getNumberOfFailedTests();
            if (Failures == 1)
                ready = true;
        }
    }

    public boolean isready() {return ready;}
}
