package vk.core.api;

import vk.core.internal.InternalCompiler;

public class CompilerFactory {

	private CompilerFactory() {
		// not intended for instantiation
	}
	
	public static JavaStringCompiler getCompiler(CompilationUnit... compilationUnits) {
		return new InternalCompiler(compilationUnits);
	}

}
