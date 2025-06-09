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
class SimianStringConfigurationTest {

	private static SimianStringConfiguration configuration;
	private static List<String> arguments;

	@BeforeEach
	void setup() {
		this.configuration = new SimianStringConfiguration("language", "java", "Assumes all files are in the specified language");
		this.arguments = new LinkedList<>();
	}

	@Test
	void nameAndDescription() throws Exception {
		assertEquals("language", this.configuration.getName());
		assertEquals("Assumes all files are in the specified language", this.configuration.getDescription());
	}

	@Test
	void create() throws Exception {
		this.configuration.addArgument(this.arguments);
		assertEquals("java", this.configuration.getValue());
		assertEquals(1, this.arguments.size());
		assertEquals("-language=java", this.arguments.get(0));
	}

	@Test
	void change() throws Exception {
		this.configuration.setValue("ruby");
		this.configuration.addArgument(this.arguments);
		assertEquals("ruby", this.configuration.getValue());
		assertEquals(1, this.arguments.size());
		assertEquals("-language=ruby", this.arguments.get(0));
	}

}
