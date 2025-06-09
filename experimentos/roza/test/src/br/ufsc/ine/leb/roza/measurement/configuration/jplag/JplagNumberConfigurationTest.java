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
class JplagNumberConfigurationTest {

	private static JplagIntegerConfiguration configuration;
	private static List<String> arguments;

	@BeforeEach
	void setup() {
		this.configuration = new JplagIntegerConfiguration("t", 1, "Tune the sensitivity of the comparison");
		this.arguments = new LinkedList<>();
	}

	@Test
	void nameAndDescription() throws Exception {
		assertEquals("t", this.configuration.getName());
		assertEquals("Tune the sensitivity of the comparison", this.configuration.getDescription());
	}

	@Test
	void create() throws Exception {
		this.configuration.addArgument(this.arguments);
		assertEquals(1, this.configuration.getValue().intValue());
		assertEquals(2, this.arguments.size());
		assertEquals("-t", this.arguments.get(0));
		assertEquals("1", this.arguments.get(1));
	}

	@Test
	void change() throws Exception {
		this.configuration.setValue(2);
		this.configuration.addArgument(this.arguments);
		assertEquals(2, this.configuration.getValue().intValue());
		assertEquals(2, this.arguments.size());
		assertEquals("-t", this.arguments.get(0));
		assertEquals("2", this.arguments.get(1));
	}

}
