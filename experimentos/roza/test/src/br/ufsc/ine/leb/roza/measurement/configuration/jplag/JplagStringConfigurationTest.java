package src.br.ufsc.ine.leb.roza.measurement.configuration.jplag;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import sunit.runner.SunitRunner;

@ExtendWith(SunitRunner.class)
@TestMethodOrder(SunitRunner.class)
class JplagStringConfigurationTest {

	private static JplagStringConfiguration configuration;
	private static List<String> arguments;

	@BeforeEach
	void setup() {
		this.configuration = new JplagStringConfiguration("l", "java17", "Language");
		this.arguments = new LinkedList<>();
	}

	@Test
	void nameAndDescription() throws Exception {
		assertEquals("l", this.configuration.getName());
		assertEquals("Language", this.configuration.getDescription());
	}

	@Test
	void create() throws Exception {
		this.configuration.addArgument(this.arguments);
		assertEquals("java17", this.configuration.getValue());
		assertEquals(2, this.arguments.size());
		assertEquals("-l", this.arguments.get(0));
		assertEquals("java17", this.arguments.get(1));
	}

	@Test
	void change() throws Exception {
		this.configuration.setValue("python3");
		this.configuration.addArgument(this.arguments);
		assertEquals("python3", this.configuration.getValue());
		assertEquals(2, this.arguments.size());
		assertEquals("-l", this.arguments.get(0));
		assertEquals("python3", this.arguments.get(1));
	}

}
