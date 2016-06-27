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
import vk.core.api.TestFailure;
import vk.core.api.TestResult;

import java.util.Collection;

public class GREEN {
    private JavaStringCompiler compiler;
    private boolean ready;
    private boolean Compiled;
    private boolean wroteTests=true;
    private int Failures;
    private TestResult Tests;
    private String Error="";

    public GREEN(JavaStringCompiler compiler) {
        this.compiler = compiler;
        this.Tests=compiler.getTestResult();
        checkifReady();
        if (Compiled) {
            BadTest();
        }
    }

    public void checkifReady() {
        CompilerResult UserCode = compiler.getCompilerResult();
        if (UserCode.hasCompileErrors()) {
            ready = true;
        }
        else {
            Failures=Tests.getNumberOfFailedTests();
            if (Failures == 1) {
                ready = true;
            }
            Compiled=true;
        }
    }
    public void BadTest() {
        Collection<TestFailure> failures=Tests.getTestFailures();
        for (TestFailure F:failures) {
            Error=F.getMessage();
        }
        if (Error.equals("No runnable methods")) {
            Error = "Du hast keine Tests implementiert !\n" +
                    "Falls schon, dann denke bitte an das @Test !\n";
            ready=false;
            wroteTests=false;
        }
        else {
            wroteTests=true;
        }
    }

    public TestResult getTests() {return Tests;}

    public String getError() {return Error;}

    public int getFailures() {return Failures;}

    public boolean isCompiled() {return Compiled;}

    public boolean isready() {return ready;}

    public boolean wroteTests() {
        return wroteTests;
    }
}