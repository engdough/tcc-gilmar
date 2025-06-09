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
class DeckardDoubleConfigurationTest {

	private static DeckardDoubleConfiguration configuration;
	private static List<String> arguments;

	@BeforeEach
	void setup() {
		this.configuration = new DeckardDoubleConfiguration("SIMILARITY", 1.0, "Similarity thresold based on editing distance");
		this.arguments = new LinkedList<>();
	}

	@Test
	void nameAndDescription() throws Exception {
		assertEquals("SIMILARITY", this.configuration.getName());
		assertEquals("Similarity thresold based on editing distance", this.configuration.getDescription());
	}

	@Test
	void create() throws Exception {
		this.configuration.addArgument(this.arguments);
		assertEquals(1, this.configuration.getValue().doubleValue());
		assertEquals(1, this.arguments.size());
		assertEquals("export SIMILARITY=1.0", this.arguments.get(0));
	}

	@Test
	void change() throws Exception {
		this.configuration.setValue(0.5);
		this.configuration.addArgument(this.arguments);
		assertEquals(0.5, this.configuration.getValue().doubleValue());
		assertEquals(1, this.arguments.size());
		assertEquals("export SIMILARITY=0.5", this.arguments.get(0));
	}

}
