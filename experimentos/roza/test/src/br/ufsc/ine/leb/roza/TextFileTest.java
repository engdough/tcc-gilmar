package src.br.ufsc.ine.leb.roza;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import sunit.runner.SunitRunner;

@ExtendWith(SunitRunner.class)
@TestMethodOrder(SunitRunner.class)
class TextFileTest {

	@Test
	void create() throws Exception {
		TextFile exampleDotJava = new TextFile("Example.java", "public class Example {}");
		Assertions.assertEquals("Example.java", exampleDotJava.getName());
		Assertions.assertEquals("public class Example {}", exampleDotJava.getContent());
	}

}
