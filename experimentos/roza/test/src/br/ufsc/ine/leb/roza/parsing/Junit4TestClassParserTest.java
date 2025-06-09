package src.br.ufsc.ine.leb.roza.parsing;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;

import src.br.ufsc.ine.leb.roza.Field;
import src.br.ufsc.ine.leb.roza.Statement;
import src.br.ufsc.ine.leb.roza.TestClass;
import src.br.ufsc.ine.leb.roza.TextFile;
import sunit.runner.SunitRunner;

@ExtendWith(SunitRunner.class)
@TestMethodOrder(SunitRunner.class)
class Junit4TestClassParserTest {

	private static TestClassParser parser;
	private static TextFile oneMethod;
	private static TextFile oneTestMethod;

	@BeforeEach
	void setup() {
		this.parser = new Junit4TestClassParser();
		this.oneMethod = new TextFile("OneMethod.java", "public class OneMethod { public void example() { System.out.println(0); } }");
		this.oneTestMethod = new TextFile("OneTestMethod.java", "public class OneTestMethod { @Test public void example() { assertEquals(0, 0); } }");
	}

	@Test
	void withoutFiles() throws Exception {
		Assertions.assertEquals(0, this.parser.parse(Arrays.asList()).size());
	}

	@Test
	void oneTestMethod() throws Exception {
		List<TestClass> testClasses = this.parser.parse(Arrays.asList(this.oneTestMethod));
		Assertions.assertEquals(1, testClasses.size());
		Assertions.assertEquals("OneTestMethod", testClasses.get(0).getName());
		Assertions.assertEquals(0, testClasses.get(0).getFields().size());
		Assertions.assertEquals(0, testClasses.get(0).getSetupMethods().size());
		Assertions.assertEquals(1, testClasses.get(0).getTestMethods().size());
		Assertions.assertEquals("example", testClasses.get(0).getTestMethods().get(0).getName());
		Assertions.assertEquals(1, testClasses.get(0).getTestMethods().get(0).getStatements().size());
		Assertions.assertEquals("assertEquals(0, 0);", testClasses.get(0).getTestMethods().get(0).getStatements().get(0).getText());
	}

	@Test
	void oneTestMethodOneMethod() throws Exception {
		TextFile oneTestMethodOneMethod = new TextFile("OneTestMethodOneMethod.java", "public class OneTestMethodOneMethod { public void example1() { System.out.println(0); } @Test public void example2() { assertEquals(2, 2); } }") {};
		List<TestClass> testClasses = this.parser.parse(Arrays.asList(oneTestMethodOneMethod));
		Assertions.assertEquals(1, testClasses.size());
		Assertions.assertEquals("OneTestMethodOneMethod", testClasses.get(0).getName());
		Assertions.assertEquals(0, testClasses.get(0).getFields().size());
		Assertions.assertEquals(0, testClasses.get(0).getSetupMethods().size());
		Assertions.assertEquals(1, testClasses.get(0).getTestMethods().size());
		Assertions.assertEquals("example2", testClasses.get(0).getTestMethods().get(0).getName());
		Assertions.assertEquals(1, testClasses.get(0).getTestMethods().get(0).getStatements().size());
		Assertions.assertEquals("assertEquals(2, 2);", testClasses.get(0).getTestMethods().get(0).getStatements().get(0).getText());
	}

	@Test
	void twoTestMethods() throws Exception {
		TextFile twoTestMethods = new TextFile("TwoTestMethods.java", "public class TwoTestMethods { @Test public void example1() { assertEquals(1, 1); } @Test public void example2() { assertEquals(2, 2); } }") {};
		List<TestClass> testClasses = this.parser.parse(Arrays.asList(twoTestMethods));
		Assertions.assertEquals(1, testClasses.size());
		Assertions.assertEquals("TwoTestMethods", testClasses.get(0).getName());
		Assertions.assertEquals(0, testClasses.get(0).getFields().size());
		Assertions.assertEquals(0, testClasses.get(0).getSetupMethods().size());
		Assertions.assertEquals(2, testClasses.get(0).getTestMethods().size());
		Assertions.assertEquals("example1", testClasses.get(0).getTestMethods().get(0).getName());
		Assertions.assertEquals(1, testClasses.get(0).getTestMethods().get(0).getStatements().size());
		Assertions.assertEquals("assertEquals(1, 1);", testClasses.get(0).getTestMethods().get(0).getStatements().get(0).getText());
		Assertions.assertEquals("example2", testClasses.get(0).getTestMethods().get(1).getName());
		Assertions.assertEquals(1, testClasses.get(0).getTestMethods().get(1).getStatements().size());
		Assertions.assertEquals("assertEquals(2, 2);", testClasses.get(0).getTestMethods().get(1).getStatements().get(0).getText());
	}

	@Test
	void oneSetupMethodOneTestMethod() throws Exception {
		TextFile oneSetupMethodOneTestMethod = new TextFile("OneSetupMethodOneTestMethod.java", "public class OneSetupMethodOneTestMethod { @BeforeAll public void setup() { System.out.println(0); System.out.println(1); } @Test public void example() { assertEquals(0, 0); assertEquals(1, 1); } }") {};
		List<TestClass> testClasses = this.parser.parse(Arrays.asList(oneSetupMethodOneTestMethod));
		Assertions.assertEquals(1, testClasses.size());
		Assertions.assertEquals("OneSetupMethodOneTestMethod", testClasses.get(0).getName());
		Assertions.assertEquals(0, testClasses.get(0).getFields().size());
		Assertions.assertEquals(1, testClasses.get(0).getSetupMethods().size());
		Assertions.assertEquals("setup", testClasses.get(0).getSetupMethods().get(0).getName());
		Assertions.assertEquals(2, testClasses.get(0).getSetupMethods().get(0).getStatements().size());
		Assertions.assertEquals("System.out.println(0);", testClasses.get(0).getSetupMethods().get(0).getStatements().get(0).getText());
		Assertions.assertEquals("System.out.println(1);", testClasses.get(0).getSetupMethods().get(0).getStatements().get(1).getText());
		Assertions.assertEquals(1, testClasses.get(0).getTestMethods().size());
		Assertions.assertEquals("example", testClasses.get(0).getTestMethods().get(0).getName());
		Assertions.assertEquals(2, testClasses.get(0).getTestMethods().get(0).getStatements().size());
		Assertions.assertEquals("assertEquals(0, 0);", testClasses.get(0).getTestMethods().get(0).getStatements().get(0).getText());
		Assertions.assertEquals("assertEquals(1, 1);", testClasses.get(0).getTestMethods().get(0).getStatements().get(1).getText());
	}

	@Test
	void oneFieldOneSetupMethodOneTestMethod() throws Exception {
		TextFile oneFieldOneSetupMethodOneTestMethod = new TextFile("OneFieldOneSetupMethodOneTestMethod.java", "public class OneFieldOneSetupMethodOneTestMethod { private Sut sut; @BeforeAll public void setup() { sut = new Sut(); sut.save(0); } @Test public void example() { assertEquals(0, 0); assertEquals(1, 1); } }") {};
		List<TestClass> testClasses = this.parser.parse(Arrays.asList(oneFieldOneSetupMethodOneTestMethod));
		Assertions.assertEquals(1, testClasses.size());
		Assertions.assertEquals("OneFieldOneSetupMethodOneTestMethod", testClasses.get(0).getName());
		Assertions.assertEquals(1, testClasses.get(0).getFields().size());
		Assertions.assertEquals("Sut", testClasses.get(0).getFields().get(0).getType());
		Assertions.assertEquals("sut", testClasses.get(0).getFields().get(0).getName());
		Assertions.assertEquals(1, testClasses.get(0).getSetupMethods().size());
		Assertions.assertEquals("setup", testClasses.get(0).getSetupMethods().get(0).getName());
		Assertions.assertEquals(2, testClasses.get(0).getSetupMethods().get(0).getStatements().size());
		Assertions.assertEquals("sut = new Sut();", testClasses.get(0).getSetupMethods().get(0).getStatements().get(0).getText());
		Assertions.assertEquals("sut.save(0);", testClasses.get(0).getSetupMethods().get(0).getStatements().get(1).getText());
		Assertions.assertEquals(1, testClasses.get(0).getTestMethods().size());
		Assertions.assertEquals("example", testClasses.get(0).getTestMethods().get(0).getName());
		Assertions.assertEquals(2, testClasses.get(0).getTestMethods().get(0).getStatements().size());
		Assertions.assertEquals("assertEquals(0, 0);", testClasses.get(0).getTestMethods().get(0).getStatements().get(0).getText());
		Assertions.assertEquals("assertEquals(1, 1);", testClasses.get(0).getTestMethods().get(0).getStatements().get(1).getText());
	}

	@Test
	void oneMethod() throws Exception {
		List<TestClass> testClasses = this.parser.parse(Arrays.asList(this.oneMethod));
		Assertions.assertEquals(0, testClasses.size());
	}

	@Test
	void oneJavaClassOneTestClass() throws Exception {
		List<TestClass> testClasses = this.parser.parse(Arrays.asList(this.oneMethod, this.oneTestMethod));
		Assertions.assertEquals(1, testClasses.size());
		Assertions.assertEquals("OneTestMethod", testClasses.get(0).getName());
		Assertions.assertEquals(0, testClasses.get(0).getFields().size());
		Assertions.assertEquals(0, testClasses.get(0).getSetupMethods().size());
		Assertions.assertEquals(1, testClasses.get(0).getTestMethods().size());
		Assertions.assertEquals("example", testClasses.get(0).getTestMethods().get(0).getName());
		Assertions.assertEquals(1, testClasses.get(0).getTestMethods().get(0).getStatements().size());
		Assertions.assertEquals("assertEquals(0, 0);", testClasses.get(0).getTestMethods().get(0).getStatements().get(0).getText());
	}

	@Test
	void oneTestMethodWithWhile() throws Exception {
		TextFile oneTestMethoWithWhile = new TextFile("OneTestMethodWithWhile.java", "public class OneTestMethodWithWhile { @Test public void example() { while (1 == 0) { System.out.println(0); } assertEquals(0, 0); } }");
		List<TestClass> testClasses = this.parser.parse(Arrays.asList(oneTestMethoWithWhile));
		Assertions.assertEquals(1, testClasses.size());
		Assertions.assertEquals("OneTestMethodWithWhile", testClasses.get(0).getName());
		Assertions.assertEquals(0, testClasses.get(0).getFields().size());
		Assertions.assertEquals(0, testClasses.get(0).getSetupMethods().size());
		Assertions.assertEquals(1, testClasses.get(0).getTestMethods().size());
		Assertions.assertEquals("example", testClasses.get(0).getTestMethods().get(0).getName());
		Assertions.assertEquals(2, testClasses.get(0).getTestMethods().get(0).getStatements().size());
		Assertions.assertEquals("while (1 == 0) { System.out.println(0); }", testClasses.get(0).getTestMethods().get(0).getStatements().get(0).getText());
		Assertions.assertEquals("assertEquals(0, 0);", testClasses.get(0).getTestMethods().get(0).getStatements().get(1).getText());
	}

	@Test
	void multipleFiedlsAndVariables() throws Exception {
		TextFile oneTestMethoWithWhile = new TextFile("MultipleFieldsAndVariables.java", "public class MultipleFieldsAndVariables {"
				+ "Integer firstField = 15;"
				+ "List<Integer> secondField = new ArrayList<>();"
				+ "Integer thirdField = 35, fourthField = 45;"
				+ "Integer fifthField;"
				+ "@Test public void example() {"
				+ "Integer firstFixture = 10;"
				+ "List<Integer> secondFixture = new ArrayList<>();"
				+ "Integer thirdFixture = 30, fourthFixture = 40;"
				+ "Integer fifthFixture;"
				+ "fourthField = 40;"
				+ "}"
				+ "}");
		List<TestClass> testClasses = this.parser.parse(Arrays.asList(oneTestMethoWithWhile));
		Assertions.assertEquals(1, testClasses.size());
		Assertions.assertEquals("MultipleFieldsAndVariables", testClasses.get(0).getName());
		Assertions.assertEquals(5, testClasses.get(0).getFields().size());
		Assertions.assertEquals(new Field("Integer", "firstField", new Statement("15;")), testClasses.get(0).getFields().get(0));
		Assertions.assertEquals(new Field("List<Integer>", "secondField", new Statement("new ArrayList<>();")), testClasses.get(0).getFields().get(1));
		Assertions.assertEquals(new Field("Integer", "thirdField", new Statement("35;")), testClasses.get(0).getFields().get(2));
		Assertions.assertEquals(new Field("Integer", "fourthField", new Statement("45;")), testClasses.get(0).getFields().get(3));
		Assertions.assertEquals(new Field("Integer", "fifthField"), testClasses.get(0).getFields().get(4));
		Assertions.assertEquals(0, testClasses.get(0).getSetupMethods().size());
		Assertions.assertEquals(1, testClasses.get(0).getTestMethods().size());
		Assertions.assertEquals("example", testClasses.get(0).getTestMethods().get(0).getName());
		Assertions.assertEquals(6, testClasses.get(0).getTestMethods().get(0).getStatements().size());
		Assertions.assertEquals("Integer firstFixture = 10;", testClasses.get(0).getTestMethods().get(0).getStatements().get(0).getText());
		Assertions.assertEquals("List<Integer> secondFixture = new ArrayList<>();", testClasses.get(0).getTestMethods().get(0).getStatements().get(1).getText());
		Assertions.assertEquals("Integer thirdFixture = 30;", testClasses.get(0).getTestMethods().get(0).getStatements().get(2).getText());
		Assertions.assertEquals("Integer fourthFixture = 40;", testClasses.get(0).getTestMethods().get(0).getStatements().get(3).getText());
		Assertions.assertEquals("Integer fifthFixture;", testClasses.get(0).getTestMethods().get(0).getStatements().get(4).getText());
		Assertions.assertEquals("fourthField = 40;", testClasses.get(0).getTestMethods().get(0).getStatements().get(5).getText());
	}

}
