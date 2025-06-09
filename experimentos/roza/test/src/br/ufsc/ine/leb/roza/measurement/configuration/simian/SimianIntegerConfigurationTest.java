package src.br.ufsc.ine.leb.roza.measurement.configuration.simian;

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
class SimianIntegerConfigurationTest {

	private static SimianIntegerConfiguration configuration;
	private static List<String> arguments;

	@BeforeEach
	void setup() {
		this.configuration = new SimianIntegerConfiguration("threshold", 6, "Matches will contain at least the specified number of lines");
		this.arguments = new LinkedList<>();
	}

	@Test
	void nameAndDescription() throws Exception {
		assertEquals("threshold", this.configuration.getName());
		assertEquals("Matches will contain at least the specified number of lines", this.configuration.getDescription());
	}

	@Test
	void create() throws Exception {
		this.configuration.addArgument(this.arguments);
		assertEquals(6, this.configuration.getValue().intValue());
		assertEquals(1, this.arguments.size());
		assertEquals("-threshold=6", this.arguments.get(0));
	}

	@Test
	void change() throws Exception {
		this.configuration.setValue(2);
		this.configuration.addArgument(arguments);
		assertEquals(2, configuration.getValue().intValue());
		assertEquals(1, arguments.size());
		assertEquals("-threshold=2", arguments.get(0));
	}

}
