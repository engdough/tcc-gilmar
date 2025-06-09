package src.br.ufsc.ine.leb.roza.measurement.configuration.deckard;

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
class DeckardStringConfigurationTest {

	private static DeckardStringConfiguration configuration;
	private static List<String> arguments;

	@BeforeEach
	void setup() {
		this.configuration = new DeckardStringConfiguration("FILE_PATTERN", "*.java", "Input file name pattern");
		this.arguments = new LinkedList<>();
	}

	@Test
	void nameAndDescription() throws Exception {
		assertEquals("FILE_PATTERN", this.configuration.getName());
		assertEquals("Input file name pattern", this.configuration.getDescription());
	}

	@Test
	void create() throws Exception {
		this.configuration.addArgument(this.arguments);
		assertEquals("*.java", this.configuration.getValue());
		assertEquals(1, this.arguments.size());
		assertEquals("export FILE_PATTERN=*.java", this.arguments.get(0));
	}

	@Test
	void change() throws Exception {
		this.configuration.setValue("*.c");
		this.configuration.addArgument(this.arguments);
		assertEquals("*.c", this.configuration.getValue());
		assertEquals(1, this.arguments.size());
		assertEquals("export FILE_PATTERN=*.c", this.arguments.get(0));
	}

}
