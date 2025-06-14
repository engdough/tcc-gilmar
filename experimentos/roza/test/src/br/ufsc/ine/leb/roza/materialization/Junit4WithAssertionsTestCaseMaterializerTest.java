package src.br.ufsc.ine.leb.roza.materialization;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;

import src.br.ufsc.ine.leb.roza.MaterializationReport;
import src.br.ufsc.ine.leb.roza.Statement;
import src.br.ufsc.ine.leb.roza.TestCase;
import src.br.ufsc.ine.leb.roza.TestCaseMaterialization;
import src.br.ufsc.ine.leb.roza.utils.FileUtils;
import src.br.ufsc.ine.leb.roza.utils.FolderUtils;
import sunit.runner.SunitRunner;

@ExtendWith(SunitRunner.class)
@TestMethodOrder(SunitRunner.class)
class Junit4WithAssertionsTestCaseMaterializerTest {

	private static TestCaseMaterializer materializer;
	private static FileUtils fileUtils;

	@BeforeEach
	void setup() {
		new FolderUtils("main/exec/materializer").createEmptyFolder();
		this.fileUtils = new FileUtils();
		this.materializer = new Junit4WithAssertionsTestCaseMaterializer("main/exec/materializer");
	}

	@Test
	void oneTestCase() throws Exception {
		Statement fixtureStatement = new Statement("sut(0);");
		Statement assertStatement = new Statement("assertEquals(0, 0);");
		TestCase testCase = new TestCase("example", Arrays.asList(fixtureStatement), Arrays.asList(assertStatement));
		MaterializationReport report = this.materializer.materialize(Arrays.asList(testCase));
		List<TestCaseMaterialization> materializations = report.getMaterializations();

		StringBuilder generatedClass = new StringBuilder();
		generatedClass.append("public class TestClass1ExampleTest {\n\n");
		generatedClass.append("\t@Test()\n");
		generatedClass.append("\tpublic void example() {\n");
		generatedClass.append("\t\tsut(0);\n");
		generatedClass.append("\t\tassertEquals(0, 0);\n");
		generatedClass.append("\t}\n");
		generatedClass.append("}\n");

		assertEquals("main/exec/materializer", report.getBaseFolder());
		assertEquals(1, materializations.size());
		assertEquals(testCase, materializations.get(0).getTestCase());
		assertEquals(8, materializations.get(0).getLength().intValue());
		assertEquals("TestClass1ExampleTest.java", materializations.get(0).getFileName());
		assertEquals("main/exec/materializer/TestClass1ExampleTest.java", materializations.get(0).getFilePath());
		assertEquals(generatedClass.toString(), this.fileUtils.readContetAsString(materializations.get(0).getFilePath()));
	}

	@Test
	void twoTetCases() throws Exception {
		Statement fixtureStatement1 = new Statement("sut(1);");
		Statement assertStatement1 = new Statement("assertEquals(1, 1);");
		TestCase testCase1 = new TestCase("example1", Arrays.asList(fixtureStatement1), Arrays.asList(assertStatement1));
		Statement fixtureStatement2 = new Statement("sut(2);");
		Statement assertStatement2 = new Statement("assertEquals(2, 2);");
		TestCase testCase2 = new TestCase("example2", Arrays.asList(fixtureStatement2), Arrays.asList(assertStatement2));
		MaterializationReport report = this.materializer.materialize(Arrays.asList(testCase1, testCase2));
		List<TestCaseMaterialization> materializations = report.getMaterializations();

		StringBuilder generatedClass1 = new StringBuilder();
		generatedClass1.append("public class TestClass1Example1Test {\n\n");
		generatedClass1.append("\t@Test()\n");
		generatedClass1.append("\tpublic void example1() {\n");
		generatedClass1.append("\t\tsut(1);\n");
		generatedClass1.append("\t\tassertEquals(1, 1);\n");
		generatedClass1.append("\t}\n");
		generatedClass1.append("}\n");

		StringBuilder generatedClass2 = new StringBuilder();
		generatedClass2.append("public class TestClass2Example2Test {\n\n");
		generatedClass2.append("\t@Test()\n");
		generatedClass2.append("\tpublic void example2() {\n");
		generatedClass2.append("\t\tsut(2);\n");
		generatedClass2.append("\t\tassertEquals(2, 2);\n");
		generatedClass2.append("\t}\n");
		generatedClass2.append("}\n");

		assertEquals("main/exec/materializer", report.getBaseFolder());
		assertEquals(2, materializations.size());
		assertEquals(8, materializations.get(0).getLength().intValue());
		assertEquals(8, materializations.get(1).getLength().intValue());
		assertEquals(testCase1, materializations.get(0).getTestCase());
		assertEquals(testCase2, materializations.get(1).getTestCase());
		assertEquals("TestClass1Example1Test.java", materializations.get(0).getFileName());
		assertEquals("TestClass2Example2Test.java", materializations.get(1).getFileName());
		assertEquals("main/exec/materializer/TestClass1Example1Test.java", materializations.get(0).getFilePath());
		assertEquals("main/exec/materializer/TestClass2Example2Test.java", materializations.get(1).getFilePath());
		assertEquals(generatedClass1.toString(), this.fileUtils.readContetAsString(materializations.get(0).getFilePath()));
		assertEquals(generatedClass2.toString(), this.fileUtils.readContetAsString(materializations.get(1).getFilePath()));
	}

	@Test
	void twoTetCasesWithTheSameName() throws Exception {
		Statement fixtureStatement1 = new Statement("sut(1);");
		Statement assertStatement1 = new Statement("assertEquals(1, 1);");
		TestCase testCase1 = new TestCase("example", Arrays.asList(fixtureStatement1), Arrays.asList(assertStatement1));
		Statement fixtureStatement2 = new Statement("sut(2);");
		Statement assertStatement2 = new Statement("assertEquals(2, 2);");
		TestCase testCase2 = new TestCase("example", Arrays.asList(fixtureStatement2), Arrays.asList(assertStatement2));
		MaterializationReport report = this.materializer.materialize(Arrays.asList(testCase1, testCase2));
		List<TestCaseMaterialization> materializations = report.getMaterializations();

		StringBuilder generatedClass1 = new StringBuilder();
		generatedClass1.append("public class TestClass1ExampleTest {\n\n");
		generatedClass1.append("\t@Test()\n");
		generatedClass1.append("\tpublic void example() {\n");
		generatedClass1.append("\t\tsut(1);\n");
		generatedClass1.append("\t\tassertEquals(1, 1);\n");
		generatedClass1.append("\t}\n");
		generatedClass1.append("}\n");

		StringBuilder generatedClass2 = new StringBuilder();
		generatedClass2.append("public class TestClass2ExampleTest {\n\n");
		generatedClass2.append("\t@Test()\n");
		generatedClass2.append("\tpublic void example() {\n");
		generatedClass2.append("\t\tsut(2);\n");
		generatedClass2.append("\t\tassertEquals(2, 2);\n");
		generatedClass2.append("\t}\n");
		generatedClass2.append("}\n");

		assertEquals("main/exec/materializer", report.getBaseFolder());
		assertEquals(2, materializations.size());
		assertEquals(8, materializations.get(0).getLength().intValue());
		assertEquals(8, materializations.get(1).getLength().intValue());
		assertEquals(testCase1, materializations.get(0).getTestCase());
		assertEquals(testCase2, materializations.get(1).getTestCase());
		assertEquals("TestClass1ExampleTest.java", materializations.get(0).getFileName());
		assertEquals("TestClass2ExampleTest.java", materializations.get(1).getFileName());
		assertEquals("main/exec/materializer/TestClass1ExampleTest.java", materializations.get(0).getFilePath());
		assertEquals("main/exec/materializer/TestClass2ExampleTest.java", materializations.get(1).getFilePath());
		assertEquals(generatedClass1.toString(), this.fileUtils.readContetAsString(materializations.get(0).getFilePath()));
		assertEquals(generatedClass2.toString(), this.fileUtils.readContetAsString(materializations.get(1).getFilePath()));
	}

}
