package src.br.ufsc.ine.leb.roza.measurement.configuration.jplag;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import sunit.runner.SunitRunner;

@ExtendWith(SunitRunner.class)
@TestMethodOrder(SunitRunner.class)
class JplagBooleanConfigurationTest {

	private static JplagBooleanConfiguration configurationTrue;
	private static JplagBooleanConfiguration configurationFalse;
	private static List<String> arguments;

	@BeforeEach
	void setup() {
		this.configurationTrue = new JplagBooleanConfiguration("d", true, "Non-parsable files will be stored");
		this.configurationFalse = new JplagBooleanConfiguration("d", false, "Non-parsable files will be stored");
		this.arguments = new LinkedList<>();
	}

	@Test
	void nameAndDescription() throws Exception {
		assertEquals("d", this.configurationTrue.getName());
		assertEquals("Non-parsable files will be stored", this.configurationTrue.getDescription());
		assertEquals("d", this.configurationFalse.getName());
		assertEquals("Non-parsable files will be stored", this.configurationFalse.getDescription());
	}

	@Test
	void createTrue() throws Exception {
		this.configurationTrue.addArgument(this.arguments);
		Assertions.assertTrue(this.configurationTrue.getValue());
		assertEquals(1, this.arguments.size());
		assertEquals("-d", this.arguments.get(0));
	}

	@Test
	void createFalse() throws Exception {
		this.configurationFalse.addArgument(this.arguments);
		Assertions.assertFalse(this.configurationFalse.getValue());
		assertEquals(0, this.arguments.size());
	}

	@Test
	void changeTrueToFalse() throws Exception {
		this.configurationTrue.unset();
		this.configurationTrue.addArgument(this.arguments);
		Assertions.assertFalse(this.configurationTrue.getValue());
		assertEquals(0, this.arguments.size());
	}

	@Test
	void changeFalseToTrue() throws Exception {
		this.configurationFalse.set();
		this.configurationFalse.addArgument(this.arguments);
		Assertions.assertTrue(this.configurationFalse.getValue());
		assertEquals(1, this.arguments.size());
		assertEquals("-d", this.arguments.get(0));
	}

}
