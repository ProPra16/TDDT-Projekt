package vk.core.internal;

public class InternalStatistics {
	public final int runCount;
	public final int ignoreCount;
	public final long runtime;
	public final int failureCount;

	public InternalStatistics(int runCount, int failureCount, int ignoreCount, long runtime) {
		this.runCount = runCount;
		this.failureCount = failureCount;
		this.ignoreCount = ignoreCount;
		this.runtime = runtime;
	}
}
