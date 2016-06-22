package vk.core.api;

public interface CompileError {

	/**
	 * @return the 1-based line number of the line that contains the error.
	 */
	long getLineNumber();

	/**
	 * @return the 1-based column number of the error.
	 */
	long getColumnNumber();

	/**
	 * @return the compiler message for the error
	 */
	String getMessage();

	/**
	 * @return the code line that contains the error
	 */
	String getCodeLineContainingTheError();

	/**
	 * @return a pretty printed error message similar to the message produced by
	 *         the console javac compiler.
	 */
	String toString();
}