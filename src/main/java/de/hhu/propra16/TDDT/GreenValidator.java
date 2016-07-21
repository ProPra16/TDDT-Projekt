package de.hhu.propra16.TDDT;

import vk.core.api.CompilerResult;
import vk.core.api.JavaStringCompiler;
import vk.core.api.TestFailure;
import vk.core.api.TestResult;

import java.util.Collection;

public class GreenValidator {
    private JavaStringCompiler compiler;
    private boolean ready;
    private boolean Compiled;
    private boolean wroteTests=true;
    private boolean SeriousError;
    private int Failures;
    private TestResult Tests;
    private String Error="";

    public GreenValidator(JavaStringCompiler compiler) {
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
        if (failures==null) {SeriousError=true; return;}
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

    public String getError() {return Error;}

    public int getFailures() {return Failures;}

    public boolean causedSeriousError() {return SeriousError;}

    public boolean isCompiled() {return Compiled;}

    public boolean isValid() {return ready;}

    public boolean foundTests() {
        return wroteTests;
    }
}