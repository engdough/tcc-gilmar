package src.br.ufsc.ine.leb.roza.measurement.configuration.jplag;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;

import src.br.ufsc.ine.leb.roza.exceptions.InvalidConfigurationException;
import sunit.runner.SunitRunner;

@ExtendWith(SunitRunner.class)
@TestMethodOrder(SunitRunner.class)
class JplagConfigurationsTest {

	private static JplagConfigurations configurations;

	@BeforeEach
	void setup() {
		this.configurations = new JplagConfigurations();
	}

	@Test
	void configurations() throws Exception {
		assertEquals(11, this.configurations.getAll().size());

		assertEquals("t", this.configurations.getAll().get(0).getName());
		assertEquals("Tune the sensitivity of the comparison", this.configurations.getAll().get(0).getDescription());

		assertEquals("l", this.configurations.getAll().get(1).getName());
		assertEquals("Language", this.configurations.getAll().get(1).getDescription());

		assertEquals("m", this.configurations.getAll().get(2).getName());
		assertEquals("All matches with more than % similarity will be saved", this.configurations.getAll().get(2).getDescription());

		assertEquals("vq", this.configurations.getAll().get(3).getName());
		assertEquals("No output", this.configurations.getAll().get(3).getDescription());

		assertEquals("vl", this.configurations.getAll().get(4).getName());
		assertEquals("Detailed output", this.configurations.getAll().get(4).getDescription());

		assertEquals("vp", this.configurations.getAll().get(5).getName());
		assertEquals("Parser messages output", this.configurations.getAll().get(5).getDescription());

		assertEquals("vd", this.configurations.getAll().get(6).getName());
		assertEquals("Details about each submission output", this.configurations.getAll().get(6).getDescription());

		assertEquals("d", this.configurations.getAll().get(7).getName());
		assertEquals("Non-parsable files will be stored", this.configurations.getAll().get(7).getDescription());

		assertEquals("o", this.configurations.getAll().get(8).getName());
		assertEquals("Output file of the parser log", this.configurations.getAll().get(8).getDescription());

		assertEquals("r", this.configurations.getAll().get(9).getName());
		assertEquals("Name of directory in which the web pages will be stored", this.configurations.getAll().get(9).getDescription());

		assertEquals("s", this.configurations.getAll().get(10).getName());
		assertEquals("Look at files in subdirs too", this.configurations.getAll().get(10).getDescription());
	}

	@Test
	void defaultValues() throws Exception {
		assertEquals(13, this.configurations.getAllAsArguments().size());

		assertEquals("-t", this.configurations.getAllAsArguments().get(0));
		assertEquals("1", this.configurations.getAllAsArguments().get(1));

		assertEquals("-l", this.configurations.getAllAsArguments().get(2));
		assertEquals("java17", this.configurations.getAllAsArguments().get(3));

		assertEquals("-m", this.configurations.getAllAsArguments().get(4));
		assertEquals("0%", this.configurations.getAllAsArguments().get(5));

		assertEquals("-vl", this.configurations.getAllAsArguments().get(6));

		assertEquals("-o", this.configurations.getAllAsArguments().get(7));
		assertEquals("main/exec/measurer/log.txt", this.configurations.getAllAsArguments().get(8));

		assertEquals("-r", this.configurations.getAllAsArguments().get(9));
		assertEquals("main/exec/measurer", this.configurations.getAllAsArguments().get(10));

		assertEquals("-s", this.configurations.getAllAsArguments().get(11));
		assertEquals("main/exec/materializer", this.configurations.getAllAsArguments().get(12));
	}

	@Test
	void changeValues() throws Exception {
		this.configurations.sensitivity(2);

		assertEquals(13, this.configurations.getAllAsArguments().size());

		assertEquals("-t", this.configurations.getAllAsArguments().get(0));
		assertEquals("2", this.configurations.getAllAsArguments().get(1));

		assertEquals("-l", this.configurations.getAllAsArguments().get(2));
		assertEquals("java17", this.configurations.getAllAsArguments().get(3));

		assertEquals("-m", this.configurations.getAllAsArguments().get(4));
		assertEquals("0%", this.configurations.getAllAsArguments().get(5));

		assertEquals("-vl", this.configurations.getAllAsArguments().get(6));

		assertEquals("-o", this.configurations.getAllAsArguments().get(7));
		assertEquals("main/exec/measurer/log.txt", this.configurations.getAllAsArguments().get(8));

		assertEquals("-r", this.configurations.getAllAsArguments().get(9));
		assertEquals("main/exec/measurer", this.configurations.getAllAsArguments().get(10));

		assertEquals("-s", this.configurations.getAllAsArguments().get(11));
		assertEquals("main/exec/materializer", this.configurations.getAllAsArguments().get(12));
	}

	@Test
	void sensitivityShouldBeLargerThanZero() throws Exception {
		assertThrows(InvalidConfigurationException.class, () -> {
			this.configurations.sensitivity(-1);
		});
		assertThrows(InvalidConfigurationException.class, () -> {
			this.configurations.sensitivity(0);
		});
		assertThrows(InvalidConfigurationException.class, () -> {
			this.configurations.sensitivity(null);
		});
	}

	@Test
	void getConfigurations() throws Exception {
		assertEquals("main/exec/measurer", this.configurations.results());
	}

}
