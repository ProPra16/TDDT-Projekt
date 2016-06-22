package vk.core.internal;

import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

import vk.core.api.CompilationUnit;
import vk.core.api.CompileError;

public class InternalCompileProblem implements CompileError {

	private final String message;
	private final long lineNumber;
	private long columnNumber;
	private CompilationUnit compilationUnit;
	private String errorLine;

	public InternalCompileProblem(Diagnostic<? extends JavaFileObject> r, CompilationUnit compilationUnit) {
		this.compilationUnit = compilationUnit;
		message = r.getMessage(null);
		lineNumber = r.getLineNumber();
		columnNumber = r.getColumnNumber();
		String[] lines = compilationUnit.getClassContent().split("\\n");
		errorLine = lines[(int) (lineNumber - 1)];
	}

	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public long getLineNumber() {
		return lineNumber;
	}

	@Override
	public long getColumnNumber() {
		return columnNumber;
	}

	@Override
	public String getCodeLineContainingTheError() {
		return errorLine;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(compilationUnit.getClassName());
		sb.append(".java:");
		sb.append(getLineNumber());
		sb.append(":error:");
		sb.append(getMessage());
		sb.append(System.lineSeparator());
		sb.append(getCodeLineContainingTheError());
		sb.append(System.lineSeparator());
		sb.append(getMarker());
		return sb.toString();
	}

	private StringBuilder getMarker() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < columnNumber - 1; i++) {
			sb.append(" ");
		}
		sb.append("^");
		return sb;
	}

}
