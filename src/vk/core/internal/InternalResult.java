package vk.core.internal;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.tools.Diagnostic;
import javax.tools.Diagnostic.Kind;
import javax.tools.JavaFileObject;

import vk.core.api.CompilationUnit;
import vk.core.api.CompileError;
import vk.core.api.CompilerResult;
import vk.core.api.TestFailure;
import vk.core.api.TestResult;

public class InternalResult implements CompilerResult, TestResult {

	private final HashMap<CompilationUnit, Collection<CompileError>> errors = new HashMap<>();
	private final HashMap<CompilationUnit, Collection<CompileError>> otherProblems = new HashMap<>();
	private InternalStatistics stats;
	private long compiletime;
	private List<TestFailure> failures;
	private boolean compileErrors;

	@Override
	public Collection<CompileError> getCompilerErrorsForCompilationUnit(CompilationUnit cu) {
		Collection<CompileError> es = errors.get(cu);
		return Collections.unmodifiableCollection(es == null ? Collections.emptyList() : es);
	}

	void addProblem(CompilationUnit cu, Diagnostic<? extends JavaFileObject> r) {
		if (r.getKind() == Kind.ERROR) {
			addProblem(cu, r, errors);
		} else {
			addProblem(cu, r, otherProblems);
		}
	}

	private void addProblem(CompilationUnit cu, Diagnostic<? extends JavaFileObject> r,
			HashMap<CompilationUnit, Collection<CompileError>> target) {
		Collection<CompileError> problems = target.get(cu);
		if (problems == null) {
			problems = new ArrayList<>();
			target.put(cu, problems);
		}
		problems.add(new InternalCompileProblem(r, cu));
	}

	public void setStatistics(InternalStatistics internalStatistics) {
		this.stats = internalStatistics;
	}

	@Override
	public int getNumberOfSuccessfulTests() {
		return stats.runCount - stats.failureCount;
	}

	@Override
	public int getNumberOfFailedTests() {
		return stats.failureCount;
	}

	@Override
	public int getNumberOfIgnoredTests() {
		return stats.ignoreCount;
	}

	@Override
	public Duration getTestDuration() {
		return Duration.ofMillis(stats.runtime);
	}

	@Override
	public Duration getCompileDuration() {
		return Duration.ofMillis(compiletime);
	}

	public void setCompileTime(long start, long end) {
		compiletime = end - start;
	}

	public void setFailures(List<TestFailure> failures) {
		this.failures = failures;
	}

	@Override
	public Collection<TestFailure> getTestFailures() {
		return Collections.unmodifiableCollection(this.failures);
	}

	@Override
	public boolean hasCompileErrors() {
		return this.compileErrors;
	}

	public void setCompileErrors(boolean b) {
		this.compileErrors = b;
	}

}
