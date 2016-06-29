package de.hhu.propra16.TDDT;

import vk.core.api.*;

public class ActionUnit {
    private UserCode UserInput;
    private CompilationUnit Klasse;
    private CompilationUnit TestKlasse;
    private CompilerResult Result;
    private TestResult FailedTests;
    private JavaStringCompiler Compiler;

    public ActionUnit(UserCode UserInput) {
        this.UserInput=UserInput;
        this.Klasse=new CompilationUnit(UserInput.getKlassenName(), UserInput.getClassContent(),false);
        this.TestKlasse=new CompilationUnit(UserInput.getTestName(), UserInput.getTestContent(),true);
    }

    public void checkGREEN() {
        Klasse=new CompilationUnit(UserInput.getKlassenName(), UserInput.getClassContent(),false);
        TestKlasse=new CompilationUnit(UserInput.getTestName(), UserInput.getTestContent(),true);
        Compiler=CompilerFactory.getCompiler(Klasse, TestKlasse);
        Compiler.compileAndRunTests();
    }

    public void compile() {
        Klasse=new CompilationUnit(UserInput.getKlassenName(),UserInput.getClassContent(),false);
        Compiler=CompilerFactory.getCompiler(Klasse);
        Compiler.compileAndRunTests();
        Result=Compiler.getCompilerResult();
    }

    public void compileAndTest() {
        Klasse=new CompilationUnit(UserInput.getKlassenName(),UserInput.getClassContent(),false);
        Compiler=CompilerFactory.getCompiler(Klasse, TestKlasse);
        Compiler.compileAndRunTests();
        Result=Compiler.getCompilerResult();
        FailedTests=Compiler.getTestResult();
    }

    public CompilerResult getResult() {
        return Result;
    }

    public TestResult getFailedTests() {return FailedTests;}

    public JavaStringCompiler getCompiler() {
        return Compiler;
    }

    public boolean compileErrors() {return Result.hasCompileErrors();}

    public boolean hasnoFailedTests() {
        return (FailedTests.getNumberOfFailedTests()==0);
    }
}