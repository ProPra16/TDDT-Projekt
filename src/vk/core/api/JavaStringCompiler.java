package vk.core.api;

import java.util.Set;

public interface JavaStringCompiler {

	/**
	 * Compiles the provided compilation units and runs all tests. This method
	 * must be called before getting the results.
	 */
	void compileAndRunTests();

	/**
	 * Get the information related to compilation (i.e., errors, warnings,
	 * compiler info).
	 * @return result of the compilation process
	 * @see CompilerResult
	 */
	CompilerResult getCompilerResult();

	/**
	 * Get the information related to testing (i.e., failures and statistics).
	 * @return result of all tests
	 * @see TestResult
	 */
	TestResult getTestResult();

	/**
	 * @return a set of all names of the compilation units
	 */
	Set<String> getAllCompilationUnitNames();

	/**
	 * @param name the name of a compilation unit
	 * @return the compilation unit object for the name
	 */
	CompilationUnit getCompilationUnitByName(String name);

}
