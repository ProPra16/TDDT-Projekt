package de.hhu.propra16.TDDT;

import vk.core.api.*;

public class ActionUnit {
    private UserCode UserInput;
    private CompilationUnit Klasse;
    private CompilationUnit TestKlasse;
    private CompilerResult Result;
    private TestResult FailedTests;
    private JavaStringCompiler Compiler;

    public ActionUnit(UserCode UserInput, JavaStringCompiler Compiler) {
        this.UserInput=UserInput;
        this.Klasse=new CompilationUnit(UserInput.getKlassenName(), UserInput.getClassContent(),false);
        this.TestKlasse=new CompilationUnit(UserInput.getTestName(), UserInput.getTestContent(),true);
        this.Compiler=Compiler;
    }

    public void checkGREEN() {
        Klasse=new CompilationUnit(UserInput.getKlassenName(), UserInput.getClassContent(),false);
        TestKlasse=new CompilationUnit(UserInput.getTestName(), UserInput.getTestContent(),true);
        Compiler=CompilerFactory.getCompiler(Klasse, TestKlasse);
        Compiler.compileAndRunTests();
    }

    public boolean hasErrors(UserCode Foo) {
        Klasse=new CompilationUnit(Foo.getKlassenName(),Foo.getClassContent(),false);
        Compiler=CompilerFactory.getCompiler(Klasse);
        Compiler.compileAndRunTests();
        Result=Compiler.getCompilerResult();
        return Result.hasCompileErrors();
    }

    public boolean isallFine(UserCode Foo) {
        Klasse=new CompilationUnit(Foo.getKlassenName(),Foo.getClassContent(),false);
        Compiler=CompilerFactory.getCompiler(Klasse, TestKlasse);
        Compiler.compileAndRunTests();
        Result=Compiler.getCompilerResult();
        if (Result.hasCompileErrors())
            return false;
        FailedTests=Compiler.getTestResult();
        if (FailedTests.getNumberOfFailedTests()==0)
            return true;
        return false;
    }

    public CompilerResult getResult() {
        return Result;
    }

    public TestResult getFailedTests() {return FailedTests;}

    public JavaStringCompiler getCompiler() {
        return Compiler;
    }


    public boolean hasnoFailedTests() {
        return (FailedTests.getNumberOfFailedTests()==0);
    }
}