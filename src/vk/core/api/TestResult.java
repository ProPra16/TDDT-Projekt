package vk.core.api;

import java.time.Duration;
import java.util.Collection;

public interface TestResult {
	/**
	 * @return number of tests that succeeded
	 */
	int getNumberOfSuccessfulTests();

	/**
	 * @return number of tests that failed
	 */
	int getNumberOfFailedTests();

	/**
	 * @return number of tests that were skipped
	 */
	int getNumberOfIgnoredTests();

	/**
	 * @return Duration of the testing process
	 */
	Duration getTestDuration();

	/**
	 * @return collection of all testing failures
	 */
	Collection<TestFailure> getTestFailures();
}
