package vk.core.api;

public interface TestFailure {

	/**
	 * @return name of the test class containing the failed test
	 */
	String getTestClassName();

	/**
	 * @return name of the test method containing the failed assertion
	 */
	String getMethodName();

	/**
	 * @return message of the failing assertion
	 */
	String getMessage();

	/**
	 * @return Stacktrace for the failed assertion
	 */
	String getExceptionStackTrace();

}
