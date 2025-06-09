package src.br.ufsc.ine.leb.roza.measurement.configuration.deckard;

import org.junit.jupiter.api.Assertions;

import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import sunit.runner.SunitRunner;

@ExtendWith(SunitRunner.class)
@TestMethodOrder(SunitRunner.class)
public class DeckardIntegerConfigurationTest {

	private static DeckardIntegerConfiguration configuration;
	private static List<String> arguments;

	@BeforeEach
	public void setup() {
		this.configuration = new DeckardIntegerConfiguration("MIN_TOKENS", 1, "Minimum token count to suppress vectors for small sub-trees");
		this.arguments = new LinkedList<>();
	}

	@Test
	public void nameAndDescription() throws Exception {
		Assertions.assertEquals("MIN_TOKENS", this.configuration.getName());
		Assertions.assertEquals("Minimum token count to suppress vectors for small sub-trees", this.configuration.getDescription());
	}

	@Test
	public void create() throws Exception {
		this.configuration.addArgument(this.arguments);
		Assertions.assertEquals(1, this.configuration.getValue().intValue());
		Assertions.assertEquals(1, this.arguments.size());
		Assertions.assertEquals("export MIN_TOKENS=1", this.arguments.get(0));
	}

	@Test
	public void change() throws Exception {
		this.configuration.setValue(Integer.MAX_VALUE);
		this.configuration.addArgument(arguments);
		Assertions.assertEquals(Integer.MAX_VALUE, this.configuration.getValue().intValue());
		Assertions.assertEquals(1, this.arguments.size());
		Assertions.assertEquals("export MIN_TOKENS=inf", this.arguments.get(0));
	}

}
