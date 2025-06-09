package src.br.ufsc.ine.leb.roza.writing;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;

import src.br.ufsc.ine.leb.roza.Field;
import src.br.ufsc.ine.leb.roza.SetupMethod;
import src.br.ufsc.ine.leb.roza.Statement;
import src.br.ufsc.ine.leb.roza.TestClass;
import src.br.ufsc.ine.leb.roza.TestMethod;
import src.br.ufsc.ine.leb.roza.utils.CodeStringBuilder;
import src.br.ufsc.ine.leb.roza.utils.FileUtils;
import src.br.ufsc.ine.leb.roza.utils.FolderUtils;
import sunit.runner.SunitRunner;

@ExtendWith(SunitRunner.class)
@TestMethodOrder(SunitRunner.class)
public class JUnit4TestClassWriterTest {

	private static FolderUtils folderUtils;
	private static TestClassWriter writer;
	private static FileUtils fileUtils;

	@BeforeEach
	void setup() {
		this.fileUtils = new FileUtils();
		this.folderUtils = new FolderUtils("main/exec/writer");
		this.folderUtils.createEmptyFolder();
		this.writer = new Junit4TestClassWriter("main/exec/writer");
	}

	@Test
	void withoutClasses() throws Exception {
		this.writer.write(Arrays.asList());
		Assertions.assertEquals(0, this.folderUtils.listFilesRecursively().size());
	}

	@Test
	void emptyTestMethod() throws Exception {
		TestMethod test = new TestMethod("alpha", Arrays.asList());
		TestClass alpha = new TestClass("Alpha", Arrays.asList(), Arrays.asList(), Arrays.asList(test));
		this.writer.write(Arrays.asList(alpha));

		CodeStringBuilder refactored = new CodeStringBuilder();
		refactored.add("import org.junit.jupiter.api.Test;");
		refactored.add();
		refactored.add("public class Alpha {");
		refactored.add();
		refactored.tab().add("@Test()");
		refactored.tab().add("public void alpha() {");
		refactored.tab().add("}");
		refactored.add("}");

		List<File> written = this.folderUtils.listFilesRecursively();
		Assertions.assertEquals(1, written.size());
		Assertions.assertEquals("Alpha.java", written.get(0).getName());
		Assertions.assertEquals(refactored.toString(), this.fileUtils.readContetAsString(written.get(0)));
	}

	@Test
	void testMethodWithStatements() throws Exception {
		Statement fixture = new Statement("int a = 10;");
		Statement assertion = new Statement("assertEquals(10, a);");
		TestMethod test = new TestMethod("alpha", Arrays.asList(fixture, assertion));
		TestClass alpha = new TestClass("Alpha", Arrays.asList(), Arrays.asList(), Arrays.asList(test));
		this.writer.write(Arrays.asList(alpha));

		CodeStringBuilder refactored = new CodeStringBuilder();
		refactored.add("import org.junit.jupiter.api.Test;");
		refactored.add();
		refactored.add("public class Alpha {");
		refactored.add();
		refactored.tab().add("@Test()");
		refactored.tab().add("public void alpha() {");
		refactored.tab().tab().add("int a = 10;");
		refactored.tab().tab().add("assertEquals(10, a);");
		refactored.tab().add("}");
		refactored.add("}");

		List<File> written = this.folderUtils.listFilesRecursively();
		Assertions.assertEquals(1, written.size());
		Assertions.assertEquals("Alpha.java", written.get(0).getName());
		Assertions.assertEquals(refactored.toString(), this.fileUtils.readContetAsString(written.get(0)));
	}

	@Test
	void emptySetupMethod() throws Exception {
		SetupMethod setup = new SetupMethod("setup", Arrays.asList());
		TestMethod test = new TestMethod("alpha", Arrays.asList());
		TestClass alpha = new TestClass("Alpha", Arrays.asList(), Arrays.asList(setup), Arrays.asList(test));
		this.writer.write(Arrays.asList(alpha));

		CodeStringBuilder refactored = new CodeStringBuilder();
		refactored.add("import org.junit.jupiter.api.BeforeAll;");
		refactored.add("import org.junit.jupiter.api.Test;");
		refactored.add();
		refactored.add("public class Alpha {");
		refactored.add();
		refactored.tab().add("@BeforeAll()");
		refactored.tab().add("public void setup() {");
		refactored.tab().add("}");
		refactored.add();
		refactored.tab().add("@Test()");
		refactored.tab().add("public void alpha() {");
		refactored.tab().add("}");
		refactored.add("}");

		List<File> written = this.folderUtils.listFilesRecursively();
		Assertions.assertEquals(1, written.size());
		Assertions.assertEquals("Alpha.java", written.get(0).getName());
		Assertions.assertEquals(refactored.toString(), this.fileUtils.readContetAsString(written.get(0)));
	}

	@Test
	void setupMethodWithFixture() throws Exception {
		Field field = new Field("int", "a");
		Statement fixture = new Statement("a = 10;");
		Statement assertion = new Statement("assertEquals(10, a);");
		SetupMethod setup = new SetupMethod("setup", Arrays.asList(fixture));
		TestMethod test = new TestMethod("alpha", Arrays.asList(assertion));
		TestClass alpha = new TestClass("Alpha", Arrays.asList(field), Arrays.asList(setup), Arrays.asList(test));
		this.writer.write(Arrays.asList(alpha));

		CodeStringBuilder refactored = new CodeStringBuilder();
		refactored.add("import org.junit.jupiter.api.BeforeAll;");
		refactored.add("import org.junit.jupiter.api.Test;");
		refactored.add();
		refactored.add("public class Alpha {");
		refactored.add();
		refactored.tab().add("private int a;");
		refactored.add();
		refactored.tab().add("@BeforeAll()");
		refactored.tab().add("public void setup() {");
		refactored.tab().tab().add("a = 10;");
		refactored.tab().add("}");
		refactored.add();
		refactored.tab().add("@Test()");
		refactored.tab().add("public void alpha() {");
		refactored.tab().tab().add("assertEquals(10, a);");
		refactored.tab().add("}");
		refactored.add("}");

		List<File> written = this.folderUtils.listFilesRecursively();
		Assertions.assertEquals(1, written.size());
		Assertions.assertEquals("Alpha.java", written.get(0).getName());
		Assertions.assertEquals(refactored.toString(), this.fileUtils.readContetAsString(written.get(0)));
	}

	@Test
	void fieldInitialization() throws Exception {
		Field field = new Field("int", "a", new Statement("10"));
		Statement assertion = new Statement("assertEquals(10, a);");
		TestMethod test = new TestMethod("alpha", Arrays.asList(assertion));
		TestClass alpha = new TestClass("Alpha", Arrays.asList(field), Arrays.asList(), Arrays.asList(test));
		this.writer.write(Arrays.asList(alpha));

		CodeStringBuilder refactored = new CodeStringBuilder();
		refactored.add("import org.junit.jupiter.api.Test;");
		refactored.add();
		refactored.add("public class Alpha {");
		refactored.add();
		refactored.tab().add("private int a = 10;");
		refactored.add();
		refactored.tab().add("@Test()");
		refactored.tab().add("public void alpha() {");
		refactored.tab().tab().add("assertEquals(10, a);");
		refactored.tab().add("}");
		refactored.add("}");

		List<File> written = this.folderUtils.listFilesRecursively();
		Assertions.assertEquals(1, written.size());
		Assertions.assertEquals("Alpha.java", written.get(0).getName());
		Assertions.assertEquals(refactored.toString(), this.fileUtils.readContetAsString(written.get(0)));
	}

	@Test
	void twoTestClassesOneWithTwoSetupMethodsAndTwoTestMethodsAndOtherWithOneEmptyTestMethod() throws Exception {
		Field filedAlphaOne = new Field("int", "a");
		Field filedAlphaTwo = new Field("int", "b");
		Statement fixtureAlphaOne = new Statement("a = 10;");
		Statement fixtureAlphaTwo = new Statement("b = 20;");
		Statement assertionAlphaOne = new Statement("assertEquals(10, a);");
		Statement assertionAlphaTwo = new Statement("assertEquals(20, b);");
		SetupMethod setupAlphaOne = new SetupMethod("setupAlphaOne", Arrays.asList(fixtureAlphaOne));
		SetupMethod setupAlphaTwo = new SetupMethod("setupAlphaTwo", Arrays.asList(fixtureAlphaTwo));
		TestMethod testAlphaOne = new TestMethod("testAlphaOne", Arrays.asList(assertionAlphaOne));
		TestMethod testAlphaTwo = new TestMethod("testAlphaTwo", Arrays.asList(assertionAlphaTwo));
		TestClass alpha = new TestClass("Alpha", Arrays.asList(filedAlphaOne, filedAlphaTwo), Arrays.asList(setupAlphaOne, setupAlphaTwo), Arrays.asList(testAlphaOne, testAlphaTwo));

		TestMethod testBeta = new TestMethod("testBeta", Arrays.asList());
		TestClass beta = new TestClass("Beta", Arrays.asList(), Arrays.asList(), Arrays.asList(testBeta));

		this.writer.write(Arrays.asList(alpha, beta));

		CodeStringBuilder refactoredAlpha = new CodeStringBuilder();
		refactoredAlpha.add("import org.junit.jupiter.api.BeforeAll;");
		refactoredAlpha.add("import org.junit.jupiter.api.Test;");
		refactoredAlpha.add();
		refactoredAlpha.add("public class Alpha {");
		refactoredAlpha.add();
		refactoredAlpha.tab().add("private int a;");
		refactoredAlpha.add();
		refactoredAlpha.tab().add("private int b;");
		refactoredAlpha.add();
		refactoredAlpha.tab().add("@BeforeAll()");
		refactoredAlpha.tab().add("public void setupAlphaOne() {");
		refactoredAlpha.tab().tab().add("a = 10;");
		refactoredAlpha.tab().add("}");
		refactoredAlpha.add();
		refactoredAlpha.tab().add("@BeforeAll()");
		refactoredAlpha.tab().add("public void setupAlphaTwo() {");
		refactoredAlpha.tab().tab().add("b = 20;");
		refactoredAlpha.tab().add("}");
		refactoredAlpha.add();
		refactoredAlpha.tab().add("@Test()");
		refactoredAlpha.tab().add("public void testAlphaOne() {");
		refactoredAlpha.tab().tab().add("assertEquals(10, a);");
		refactoredAlpha.tab().add("}");
		refactoredAlpha.add();
		refactoredAlpha.tab().add("@Test()");
		refactoredAlpha.tab().add("public void testAlphaTwo() {");
		refactoredAlpha.tab().tab().add("assertEquals(20, b);");
		refactoredAlpha.tab().add("}");
		refactoredAlpha.add("}");

		CodeStringBuilder refactoredBeta = new CodeStringBuilder();
		refactoredBeta.add("import org.junit.jupiter.api.Test;");
		refactoredBeta.add();
		refactoredBeta.add("public class Beta {");
		refactoredBeta.add();
		refactoredBeta.tab().add("@Test()");
		refactoredBeta.tab().add("public void testBeta() {");
		refactoredBeta.tab().add("}");
		refactoredBeta.add("}");

		List<File> written = this.folderUtils.listFilesRecursively();
		Assertions.assertEquals(2, written.size());
		Assertions.assertEquals("Alpha.java", written.get(0).getName());
		Assertions.assertEquals(refactoredAlpha.toString(), this.fileUtils.readContetAsString(written.get(0)));
		Assertions.assertEquals("Beta.java", written.get(1).getName());
		Assertions.assertEquals(refactoredBeta.toString(), this.fileUtils.readContetAsString(written.get(1)));
	}

}
