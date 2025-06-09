package src.br.ufsc.ine.leb.roza.measurement.matrix.simian;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;

import src.br.ufsc.ine.leb.roza.TestCase;
import src.br.ufsc.ine.leb.roza.TestCaseMaterialization;
import src.br.ufsc.ine.leb.roza.measurement.matrix.MatrixElementToKeyConverter;
import sunit.runner.SunitRunner;

@ExtendWith(SunitRunner.class)
@TestMethodOrder(SunitRunner.class)
class SimianElementToKeyConverterTest {

	@Test
	void testCaseMaterializationToString() throws Exception {
		TestCase testCase = new TestCase("test", Arrays.asList(), Arrays.asList());
		TestCaseMaterialization materialization = new TestCaseMaterialization(new File("Materialization.java"), 10, testCase);
		MatrixElementToKeyConverter<TestCaseMaterialization, String> converter = new SimianMatrixElementToKeyConverter();
		assertEquals(materialization.getAbsoluteFilePath(), converter.convert(materialization));
	}

}
