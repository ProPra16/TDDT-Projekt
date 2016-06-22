package vk.core.api;

import java.util.Set;

public class TestHelpers {

	/**
	 * @param compiler
	 * @param compilerResult
	 * @return String containing all error messages, used if we do not expect
	 *         Errors but the compiler found some. Very useful during when
	 *         trying to identify reason for a failed test.
	 */
	public static String getErrorMessages(JavaStringCompiler compiler, CompilerResult compilerResult) {
		Set<String> cus = compiler.getAllCompilationUnitNames();
		StringBuilder sb = new StringBuilder();
		for (String cuName : cus) {
			CompilationUnit cu = compiler.getCompilationUnitByName(cuName);
			for (CompileError compileError : compilerResult.getCompilerErrorsForCompilationUnit(cu)) {
				sb.append(compileError.toString());
				sb.append(System.lineSeparator());
			}
		}
		return sb.toString();
	}

}
