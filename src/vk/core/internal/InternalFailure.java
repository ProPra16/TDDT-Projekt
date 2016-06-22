package vk.core.internal;

import org.junit.runner.Description;
import org.junit.runner.notification.Failure;

import vk.core.api.TestFailure;

public class InternalFailure implements TestFailure {

	private String trace;
	private String message;
	private Description description;

	public InternalFailure(Failure f) {
		trace = f.getTrace();
		message = f.getMessage();
		description = f.getDescription();
	}

	@Override
	public String getTestClassName() {
		return description.getClassName();
	}

	@Override
	public String getMethodName() {
		return description.getMethodName();
	}

	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public String getExceptionStackTrace() {
		return trace;
	}

}
