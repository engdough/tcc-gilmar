package src.br.ufsc.ine.leb.roza.measurement.configuration.simian;

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
class SimianBooleanConfigurationTest {

	private static SimianBooleanConfiguration configurationTrue;
	private static SimianBooleanConfiguration configurationFalse;
	private static List<String> arguments;

	@BeforeEach
	void setup() {
		this.configurationTrue = new SimianBooleanConfiguration("ignoreCurlyBraces", true, "Curly braces are ignored");
		this.configurationFalse = new SimianBooleanConfiguration("ignoreCurlyBraces", false, "Curly braces are ignored");
		this.arguments = new LinkedList<>();
	}

	@Test
	void nameAndDescription() throws Exception {
		assertEquals("ignoreCurlyBraces", this.configurationTrue.getName());
		assertEquals("Curly braces are ignored", this.configurationTrue.getDescription());
		assertEquals("ignoreCurlyBraces", this.configurationFalse.getName());
		assertEquals("Curly braces are ignored", this.configurationFalse.getDescription());
	}

	@Test
	void createTrue() throws Exception {
		this.configurationTrue.addArgument(this.arguments);
		Assertions.assertTrue(this.configurationTrue.getValue());
		assertEquals(1, this.arguments.size());
		assertEquals("-ignoreCurlyBraces+", this.arguments.get(0));
	}

	@Test
	void createFalse() throws Exception {
		this.configurationFalse.addArgument(this.arguments);
		Assertions.assertFalse(this.configurationFalse.getValue());
		assertEquals(1, this.arguments.size());
		assertEquals("-ignoreCurlyBraces-", this.arguments.get(0));
	}

	@Test
	void changeTrueToFalse() throws Exception {
		this.configurationTrue.unset();
		this.configurationTrue.addArgument(this.arguments);
		Assertions.assertFalse(this.configurationTrue.getValue());
		assertEquals(1, this.arguments.size());
		assertEquals("-ignoreCurlyBraces-", this.arguments.get(0));
	}

	@Test
	void changeFalseToTrue() throws Exception {
		this.configurationFalse.set();
		this.configurationFalse.addArgument(this.arguments);
		Assertions.assertTrue(this.configurationFalse.getValue());
		assertEquals(1, this.arguments.size());
		assertEquals("-ignoreCurlyBraces+", this.arguments.get(0));
	}

}
