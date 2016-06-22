package vk.core.api;

import java.time.Duration;
import java.util.Collection;

public interface CompilerResult {

	/**
	 * @return true, if and only if the compilation process produced no error
	 *         messages
	 */
	boolean hasCompileErrors();

	/**
	 * @return Duration of the compilation process 
	 */
	Duration getCompileDuration();

	
	/**
	 * @param cu - a compilation unit
	 * @return all errors for that compilation unit
	 */
	Collection<CompileError> getCompilerErrorsForCompilationUnit(CompilationUnit cu);

}
