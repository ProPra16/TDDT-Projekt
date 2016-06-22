package de.hhu.propra16.TDDT;

import vk.core.api.CompilationUnit;
import vk.core.api.CompilerFactory;
import vk.core.api.CompilerResult;
import vk.core.api.JavaStringCompiler;

public class ActionUnit {
    private UserCode UserInput;
    private CompilationUnit Klasse;
    private CompilationUnit TestKlasse;
    private JavaStringCompiler Compiler;

    public ActionUnit(UserCode UserInput, JavaStringCompiler Compiler) {
        this.UserInput=UserInput;
        this.Compiler=Compiler;
    }

    public void newGREENStartup() {
        Klasse=new CompilationUnit(UserInput.getKlassenName(), UserInput.getClassContent(),false);
        TestKlasse=new CompilationUnit(UserInput.getTestName(), UserInput.getTestContent(),true);
        Compiler=CompilerFactory.getCompiler(Klasse, TestKlasse);
        Compiler.compileAndRunTests();
    }

    public void checkClass(UserCode Foo) {
        Klasse=new CompilationUnit(Foo.getKlassenName(),Foo.getClassContent(),false);
        Compiler=CompilerFactory.getCompiler(Klasse);
        Compiler.compileAndRunTests();
    }

    public void checkClasses(UserCode Foo) {
        Klasse=new CompilationUnit(Foo.getKlassenName(),Foo.getClassContent(),false);
        Compiler=CompilerFactory.getCompiler(Klasse, TestKlasse);
        Compiler.compileAndRunTests();
    }

    public CompilerResult getResult() {
        return Compiler.getCompilerResult();
    }

    public JavaStringCompiler getCompiler() {
        return Compiler;
    }
}
