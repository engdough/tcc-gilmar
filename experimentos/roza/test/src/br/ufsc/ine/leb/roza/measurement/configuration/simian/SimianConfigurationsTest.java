package src.br.ufsc.ine.leb.roza.measurement.configuration.simian;

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
class SimianConfigurationsTest {

	private static SimianConfigurations configurations;

	@BeforeEach
	void setup() {
		this.configurations = new SimianConfigurations();
	}

	@Test
	void configurations() throws Exception {
		assertEquals(20, this.configurations.getAll().size());

		assertEquals("threshold", this.configurations.getAll().get(0).getName());
		assertEquals("Matches will contain at least the specified number of lines", this.configurations.getAll().get(0).getDescription());

		assertEquals("ignoreCurlyBraces", this.configurations.getAll().get(1).getName());
		assertEquals("Curly braces are ignored", this.configurations.getAll().get(1).getDescription());

		assertEquals("ignoreIdentifiers", this.configurations.getAll().get(2).getName());
		assertEquals("Completely ignores all identfiers", this.configurations.getAll().get(2).getDescription());

		assertEquals("ignoreStrings", this.configurations.getAll().get(3).getName());
		assertEquals("\"abc\" and \"def\" would both match", this.configurations.getAll().get(3).getDescription());

		assertEquals("ignoreNumbers", this.configurations.getAll().get(4).getName());
		assertEquals("1 and 576 would both match", this.configurations.getAll().get(4).getDescription());

		assertEquals("ignoreCharacters", this.configurations.getAll().get(5).getName());
		assertEquals("'A' and 'Z' would both match", this.configurations.getAll().get(5).getDescription());

		assertEquals("ignoreLiterals", this.configurations.getAll().get(6).getName());
		assertEquals("'A', \"one\" and 27.8 would all match", this.configurations.getAll().get(6).getDescription());

		assertEquals("ignoreVariableNames", this.configurations.getAll().get(7).getName());
		assertEquals("int foo = 1; and int bar = 1 would both match", this.configurations.getAll().get(7).getDescription());

		assertEquals("formatter", this.configurations.getAll().get(8).getName());
		assertEquals("Specifies the format in which processing results will be produced", this.configurations.getAll().get(8).getDescription());

		assertEquals("language", this.configurations.getAll().get(9).getName());
		assertEquals("Assumes all files are in the specified language", this.configurations.getAll().get(9).getDescription());

		assertEquals("failOnDuplication", this.configurations.getAll().get(10).getName());
		assertEquals("Causes the checker to fail the current process if duplication is detected", this.configurations.getAll().get(10).getDescription());

		assertEquals("reportDuplicateText", this.configurations.getAll().get(11).getName());
		assertEquals("Prints the duplicate text in reports", this.configurations.getAll().get(11).getDescription());

		assertEquals("ignoreBlocks", this.configurations.getAll().get(12).getName());
		assertEquals("Ignores all lines between specified start/end markers", this.configurations.getAll().get(12).getDescription());

		assertEquals("ignoreIdentifierCase", this.configurations.getAll().get(13).getName());
		assertEquals("MyVariableName and myvariablename would both match", this.configurations.getAll().get(13).getDescription());

		assertEquals("ignoreStringCase", this.configurations.getAll().get(14).getName());
		assertEquals("\"Hello, World\" and \"HELLO, WORLD\" would both match", this.configurations.getAll().get(14).getDescription());

		assertEquals("ignoreCharacterCase", this.configurations.getAll().get(15).getName());
		assertEquals("'A' and 'a'would both match", this.configurations.getAll().get(15).getDescription());

		assertEquals("ignoreSubtypeNames", this.configurations.getAll().get(16).getName());
		assertEquals("BufferedReader, StringReader and Reader would all match", this.configurations.getAll().get(16).getDescription());

		assertEquals("ignoreModifiers", this.configurations.getAll().get(17).getName());
		assertEquals("public, protected, static, etc", this.configurations.getAll().get(17).getDescription());

		assertEquals("balanceParentheses", this.configurations.getAll().get(18).getName());
		assertEquals("Ensures that expressions inside parenthesis that are split across multiple physical lines are considered as one", this.configurations.getAll().get(18).getDescription());

		assertEquals("balanceSquareBrackets", this.configurations.getAll().get(19).getName());
		assertEquals("Ensures that expressions inside square brackets that are split across multiple physical lines are considered as one", this.configurations.getAll().get(19).getDescription());
	}

	@Test
	void defaultValues() throws Exception {
		assertEquals(20, this.configurations.getAllAsArguments().size());
		assertEquals("-threshold=6", this.configurations.getAllAsArguments().get(0));
		assertEquals("-ignoreCurlyBraces+", this.configurations.getAllAsArguments().get(1));
		assertEquals("-ignoreIdentifiers-", this.configurations.getAllAsArguments().get(2));
		assertEquals("-ignoreStrings-", this.configurations.getAllAsArguments().get(3));
		assertEquals("-ignoreNumbers-", this.configurations.getAllAsArguments().get(4));
		assertEquals("-ignoreCharacters-", this.configurations.getAllAsArguments().get(5));
		assertEquals("-ignoreLiterals-", this.configurations.getAllAsArguments().get(6));
		assertEquals("-ignoreVariableNames-", this.configurations.getAllAsArguments().get(7));
		assertEquals("-formatter=xml", this.configurations.getAllAsArguments().get(8));
		assertEquals("-language=java", this.configurations.getAllAsArguments().get(9));
		assertEquals("-failOnDuplication-", this.configurations.getAllAsArguments().get(10));
		assertEquals("-reportDuplicateText+", this.configurations.getAllAsArguments().get(11));
		assertEquals("-ignoreBlocks=0:0", this.configurations.getAllAsArguments().get(12));
		assertEquals("-ignoreIdentifierCase-", this.configurations.getAllAsArguments().get(13));
		assertEquals("-ignoreStringCase-", this.configurations.getAllAsArguments().get(14));
		assertEquals("-ignoreCharacterCase-", this.configurations.getAllAsArguments().get(15));
		assertEquals("-ignoreSubtypeNames-", this.configurations.getAllAsArguments().get(16));
		assertEquals("-ignoreModifiers+", this.configurations.getAllAsArguments().get(17));
		assertEquals("-balanceParentheses+", this.configurations.getAllAsArguments().get(18));
		assertEquals("-balanceSquareBrackets+", this.configurations.getAllAsArguments().get(19));
	}

	@Test
	void changeValues() throws Exception {
		configurations.threshold(2);
		assertEquals(20, this.configurations.getAllAsArguments().size());
		assertEquals("-threshold=2", this.configurations.getAllAsArguments().get(0));
		assertEquals("-ignoreCurlyBraces+", this.configurations.getAllAsArguments().get(1));
		assertEquals("-ignoreIdentifiers-", this.configurations.getAllAsArguments().get(2));
		assertEquals("-ignoreStrings-", this.configurations.getAllAsArguments().get(3));
		assertEquals("-ignoreNumbers-", this.configurations.getAllAsArguments().get(4));
		assertEquals("-ignoreCharacters-", this.configurations.getAllAsArguments().get(5));
		assertEquals("-ignoreLiterals-", this.configurations.getAllAsArguments().get(6));
		assertEquals("-ignoreVariableNames-", this.configurations.getAllAsArguments().get(7));
		assertEquals("-formatter=xml", this.configurations.getAllAsArguments().get(8));
		assertEquals("-language=java", this.configurations.getAllAsArguments().get(9));
		assertEquals("-failOnDuplication-", this.configurations.getAllAsArguments().get(10));
		assertEquals("-reportDuplicateText+", this.configurations.getAllAsArguments().get(11));
		assertEquals("-ignoreBlocks=0:0", this.configurations.getAllAsArguments().get(12));
		assertEquals("-ignoreIdentifierCase-", this.configurations.getAllAsArguments().get(13));
		assertEquals("-ignoreStringCase-", this.configurations.getAllAsArguments().get(14));
		assertEquals("-ignoreCharacterCase-", this.configurations.getAllAsArguments().get(15));
		assertEquals("-ignoreSubtypeNames-", this.configurations.getAllAsArguments().get(16));
		assertEquals("-ignoreModifiers+", this.configurations.getAllAsArguments().get(17));
		assertEquals("-balanceParentheses+", this.configurations.getAllAsArguments().get(18));
		assertEquals("-balanceSquareBrackets+", this.configurations.getAllAsArguments().get(19));
	}

	@Test
	void threasholdShoudBeLargerThanTwo() throws Exception {
		assertThrows(InvalidConfigurationException.class, () -> {
			this.configurations.threshold(-1);
		});
		assertThrows(InvalidConfigurationException.class, () -> {
			this.configurations.threshold(0);
		});
		assertThrows(InvalidConfigurationException.class, () -> {
			this.configurations.threshold(1);
		});
		assertThrows(InvalidConfigurationException.class, () -> {
			this.configurations.threshold(null);
		});
	}

}
