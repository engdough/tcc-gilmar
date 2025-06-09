package src.br.ufsc.ine.leb.roza;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import sunit.runner.SunitRunner;

@ExtendWith(SunitRunner.class)
@TestMethodOrder(SunitRunner.class)
class StatementTest {

	@Test
	void create() throws Exception {
		Statement statement = new Statement("assertEquals(0, 0);");
		Assertions.assertEquals("assertEquals(0, 0);", statement.getText());
		Assertions.assertEquals("assertEquals(0, 0);", statement.toString());
	}

	@Test
	void equals() throws Exception {
		Assertions.assertEquals(new Statement("sut(0);"), new Statement("sut(0);"));
		assertNotEquals(new Statement("sut(0);"), new Statement("sut(1);"));
	}

}
