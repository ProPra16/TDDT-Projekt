package vk.core.api;

import java.nio.file.Path;
import java.nio.file.Paths;

public class CompilationUnit {

	private final String className;
	private final String classContent;
	private final boolean isATest;

	/**
	 * Defines an input for the Java Compiler and the test runner.
	 * 
	 * @param className
	 *            Name of the public class (e.g. Foo). Note that this is
	 *            <b>not</b> the filename (Foo.java)
	 * @param classContent
	 *            Content of the class, a String containing the sourcecode that
	 *            you would normally store in a .java file.
	 * @param isATest
	 *            true if this compilation unit should be treated as a test
	 *            (i.e., included in the test run)
	 */
	public CompilationUnit(String className, String classContent, boolean isATest) {
		this.className = className;
		this.classContent = classContent;
		this.isATest = isATest;
	}

	public String getClassName() {
		return className;
	}

	public String getClassContent() {
		return classContent;
	}

	public boolean isATest() {
		return isATest;
	}

	public Path getSourceFile() {
		return Paths.get(className + ".java");
	}

}
