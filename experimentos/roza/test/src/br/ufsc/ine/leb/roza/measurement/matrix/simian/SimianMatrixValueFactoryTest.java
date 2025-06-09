package src.br.ufsc.ine.leb.roza.measurement.matrix.simian;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.math.BigDecimal;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;

import src.br.ufsc.ine.leb.roza.TestCase;
import src.br.ufsc.ine.leb.roza.TestCaseMaterialization;
import src.br.ufsc.ine.leb.roza.measurement.intersector.Intersector;
import src.br.ufsc.ine.leb.roza.measurement.matrix.MatrixValueFactory;
import sunit.runner.SunitRunner;

@ExtendWith(SunitRunner.class)
@TestMethodOrder(SunitRunner.class)
class SimianMatrixValueFactoryTest {

	private static MatrixValueFactory<TestCaseMaterialization, Intersector> factory;

	@BeforeEach
	void setup() {
		this.factory = new SimianMatrixValueFactory();
	}

	@Test
	void same() throws Exception {
		TestCase testCase = new TestCase("test", Arrays.asList(), Arrays.asList());
		TestCaseMaterialization materialization = new TestCaseMaterialization(new File("Materialization.java"), 10, testCase);
		assertEquals(BigDecimal.ONE, this.factory.create(materialization, materialization).evaluate());
	}

	@Test
	void notSame() throws Exception {
		TestCase testCaseA = new TestCase("testA", Arrays.asList(), Arrays.asList());
		TestCase testCaseB = new TestCase("testB", Arrays.asList(), Arrays.asList());
		TestCaseMaterialization materializationA = new TestCaseMaterialization(new File("MaterializationA.java"), 10, testCaseA);
		TestCaseMaterialization materializationB = new TestCaseMaterialization(new File("MaterializationB.java"), 10, testCaseB);
		assertEquals(BigDecimal.ZERO, this.factory.create(materializationA, materializationB).evaluate());
		assertEquals(BigDecimal.ZERO, this.factory.create(materializationB, materializationA).evaluate());
	}

}
