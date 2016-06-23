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
        this.TestKlasse=TestKlasse=new CompilationUnit(UserInput.getTestName(), UserInput.getTestContent(),true);
        this.Compiler=Compiler;
    }

    public void checkGREEN() {
        Klasse=new CompilationUnit(UserInput.getKlassenName(), UserInput.getClassContent(),false);
        TestKlasse=new CompilationUnit(UserInput.getTestName(), UserInput.getTestContent(),true);
        Compiler=CompilerFactory.getCompiler(Klasse, TestKlasse);
        Compiler.compileAndRunTests();
    }

    public void checkClass(UserCode Foo) {
        Klasse=new CompilationUnit(Foo.getKlassenName(),Foo.getClassContent(),false);
        Compiler=CompilerFactory.getCompiler(Klasse);
        Compiler.compileAndRunTests();
        Result=Compiler.getCompilerResult();
    }

    public void checkREFACTOR(UserCode Foo) {
        Klasse=new CompilationUnit(Foo.getKlassenName(),Foo.getClassContent(),false);
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


    public boolean hasnoFailedTests() {
        return (FailedTests.getNumberOfFailedTests()==0);
    }

    public boolean isallFine() {
        return !Result.hasCompileErrors();
    }
}
