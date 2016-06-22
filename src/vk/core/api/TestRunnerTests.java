package vk.core.api;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestRunnerTests {

	CompilationUnit validClassBar = new CompilationUnit("Bar", "public class Bar { \n"
			+ " public static int square(int n) { \n"
			+ "    return n * n; \n"
			+ " }\n"
			+ " public static int plusThree(int n) { \n"
			+ "    return 3 + n; \n"
			+ " }\n"
			+ "}", false);

	CompilationUnit succeedingTestClassWithSingleTestForBar = new CompilationUnit("BarTest",
			"import static org.junit.Assert.*;\n"
					+ "import org.junit.Test;\n"
					+ "public class BarTest { \n"
					+ "   @Test\n"
					+ "   public void squareOf4_shouldReturn16() { \n "
					+ "       assertEquals(16, Bar.square(4)); \n"
					+ "   }\n "
					+ "}",
			true);

	CompilationUnit succeedingTestClassWithTwoTestsForBar = new CompilationUnit("BarTest",
			"import static org.junit.Assert.*;\n"
					+ "import org.junit.Test;\n"
					+ "public class BarTest { \n"
					+ "   @Test\n"
					+ "   public void squareOf4_shouldReturn16() { \n "
					+ "       assertEquals(16, Bar.square(4)); \n"
					+ "   }\n "
					+ "   @Test\n"
					+ "   public void squareOf6_shouldReturn36() { \n "
					+ "       assertEquals(36, Bar.square(6)); \n"
					+ "   }\n "
					+ "}",
			true);

	CompilationUnit failingTestClassWithTwoTestsForBarOneOfThemFailing = new CompilationUnit("BarTest",
			"import static org.junit.Assert.*;\n"
					+ "import org.junit.Test;\n"
					+ "public class BarTest { \n"
					+ "   @Test\n"
					+ "   public void squareOf2_shouldReturn16() { \n "
					+ "       assertEquals(16, Bar.square(2)); \n"
					+ "   }\n "
					+ "   @Test\n"
					+ "   public void squareOf6_shouldReturn36() { \n "
					+ "       assertEquals(36, Bar.square(6)); \n"
					+ "   }\n "
					+ "}",
			true);

	CompilationUnit failingTestClassWithTwoTestsForBarBothFailing = new CompilationUnit("BarTest",
			"import static org.junit.Assert.*;\n"
					+ "import org.junit.Test;\n"
					+ "public class BarTest { \n"
					+ "   @Test\n"
					+ "   public void squareOf2_shouldReturn16() { \n "
					+ "       assertEquals(16, Bar.square(2)); \n"
					+ "   }\n "
					+ "   @Test\n"
					+ "   public void squareOf6_shouldReturn36() { \n "
					+ "       assertEquals(36, Bar.square(-2)); \n"
					+ "   }\n "
					+ "}",
			true);

	CompilationUnit failingTestClassWithThreeTestsForBarOneIgnoreOneFail = new CompilationUnit("BarTest",
			"import static org.junit.Assert.*;\n"
					+ "import org.junit.*;\n"
					+ "public class BarTest { \n"
					+ "   @Test\n"
					+ "   public void squareOf2_shouldReturn16() { \n "
					+ "       assertEquals(16, Bar.square(4)); \n"
					+ "   }\n "
					+ "   @Test\n"
					+ "   public void squareOf6_shouldReturn36() { \n "
					+ "       assertEquals(36, Bar.square(-2)); \n"
					+ "   }\n "
					+ "   @Ignore\n"
					+ "   @Test\n"
					+ "   public void squareOf7_shouldReturn36() { \n "
					+ "       assertEquals(36, Bar.square(-8)); \n"
					+ "   }\n "
					+ "}",
			true);

	@Test
	public void runningValidTestOneTest_shouldNotResultInFailure() {
		JavaStringCompiler compiler = CompilerFactory.getCompiler(validClassBar,
				succeedingTestClassWithSingleTestForBar);
		compiler.compileAndRunTests();
		CompilerResult compilerResult = compiler.getCompilerResult();
		assertFalse(TestHelpers.getErrorMessages(compiler, compilerResult),
				compilerResult.hasCompileErrors());
		TestResult testResult = compiler.getTestResult();
		assertEquals(1, testResult.getNumberOfSuccessfulTests());
		assertEquals(0, testResult.getNumberOfFailedTests());
		assertEquals(0, testResult.getNumberOfIgnoredTests());
	}

	@Test
	public void runningValidTestTwoTests_shouldNotResultInFailure() {
		JavaStringCompiler compiler = CompilerFactory.getCompiler(validClassBar,
				succeedingTestClassWithTwoTestsForBar);
		compiler.compileAndRunTests();
		CompilerResult compilerResult = compiler.getCompilerResult();
		assertFalse(TestHelpers.getErrorMessages(compiler, compilerResult),
				compilerResult.hasCompileErrors());
		TestResult testResult = compiler.getTestResult();
		assertEquals(2, testResult.getNumberOfSuccessfulTests());
		assertEquals(0, testResult.getNumberOfFailedTests());
		assertEquals(0, testResult.getNumberOfIgnoredTests());
	}

	@Test
	public void runningFailingTestTwoTestsOneFail_shouldResultInFailure() {
		JavaStringCompiler compiler = CompilerFactory.getCompiler(validClassBar,
				failingTestClassWithTwoTestsForBarOneOfThemFailing);
		compiler.compileAndRunTests();
		CompilerResult compilerResult = compiler.getCompilerResult();
		assertFalse(TestHelpers.getErrorMessages(compiler, compilerResult),
				compilerResult.hasCompileErrors());
		TestResult testResult = compiler.getTestResult();
		assertEquals(1, testResult.getNumberOfSuccessfulTests());
		assertEquals(1, testResult.getNumberOfFailedTests());
		assertEquals(0, testResult.getNumberOfIgnoredTests());
	}

	@Test
	public void runningFailingTestTwoTestsBothFail_shouldResultInFailure() {
		JavaStringCompiler compiler = CompilerFactory.getCompiler(validClassBar,
				failingTestClassWithTwoTestsForBarBothFailing);
		compiler.compileAndRunTests();
		CompilerResult compilerResult = compiler.getCompilerResult();
		assertFalse(TestHelpers.getErrorMessages(compiler, compilerResult),
				compilerResult.hasCompileErrors());
		TestResult testResult = compiler.getTestResult();
		assertEquals(0, testResult.getNumberOfSuccessfulTests());
		assertEquals(2, testResult.getNumberOfFailedTests());
		assertEquals(0, testResult.getNumberOfIgnoredTests());
	}

	@Test
	public void runningFailingTestThreeTestsOneIgnoreOneFail_shouldResultInFailure() {
		JavaStringCompiler compiler = CompilerFactory.getCompiler(validClassBar,
				failingTestClassWithThreeTestsForBarOneIgnoreOneFail);
		compiler.compileAndRunTests();
		CompilerResult compilerResult = compiler.getCompilerResult();
		assertFalse(TestHelpers.getErrorMessages(compiler, compilerResult),
				compilerResult.hasCompileErrors());
		TestResult testResult = compiler.getTestResult();
		assertEquals(1, testResult.getNumberOfSuccessfulTests());
		assertEquals(1, testResult.getNumberOfFailedTests());
		assertEquals(1, testResult.getNumberOfIgnoredTests());
	}

}
