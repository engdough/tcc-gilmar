package src.br.ufsc.ine.leb.roza.clustering;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;

import src.br.ufsc.ine.leb.roza.Cluster;
import src.br.ufsc.ine.leb.roza.TestCase;
import src.br.ufsc.ine.leb.roza.exceptions.InvalidThresholdException;
import sunit.runner.SunitRunner;

@ExtendWith(SunitRunner.class)
@TestMethodOrder(SunitRunner.class)
public class ComposedCriteriaTest {

	private static Cluster alphaCluster;
	private static Cluster betaCluster;

	@BeforeEach
	public void seutp() {
		TestCase alpha = new TestCase("alpha", Arrays.asList(), Arrays.asList());
		TestCase beta = new TestCase("beta", Arrays.asList(), Arrays.asList());
		this.alphaCluster = new Cluster(alpha);
		this.betaCluster = new Cluster(beta);
	}

	@Test
	public void shouldNotBeEmpty() throws Exception {
		assertThrows(InvalidThresholdException.class, () -> {
			new ComposedCriteria();
		});
	}

	@Test
	public void shouldHaveMoreThanOneElement() throws Exception {
		assertThrows(InvalidThresholdException.class, () -> {
			new ComposedCriteria(new LevelBasedCriteria(1));
		});
	}

	@Test
	void shouldNotStopInAny() throws Exception {
		ThresholdCriteria threshold = new ComposedCriteria(new LevelBasedCriteria(1), new TestsPerClassCriteria(2));
		assertFalse(threshold.shoudlStop(1, new Combination(this.alphaCluster, this.betaCluster), BigDecimal.ONE));
	}

	@Test
	void shouldStopInFirstLevelBased() throws Exception {
		ThresholdCriteria threshold = new ComposedCriteria(new LevelBasedCriteria(0), new TestsPerClassCriteria(2));
		assertTrue(threshold.shoudlStop(1, new Combination(this.alphaCluster, this.betaCluster), BigDecimal.ONE));
	}

	@Test
	void shouldStopInSecondTestsPerClass() throws Exception {
		ThresholdCriteria threshold = new ComposedCriteria(new LevelBasedCriteria(1), new TestsPerClassCriteria(1));
		assertTrue(threshold.shoudlStop(1, new Combination(this.alphaCluster, this.betaCluster), BigDecimal.ONE));
	}

	@Test
	void shouldStopInFirstTestsPerClassBased() throws Exception {
		ThresholdCriteria threshold = new ComposedCriteria(new TestsPerClassCriteria(1), new LevelBasedCriteria(1));
		assertTrue(threshold.shoudlStop(1, new Combination(this.alphaCluster, this.betaCluster), BigDecimal.ONE));
	}

	@Test
	void shouldStopInSecondLevelBased() throws Exception {
		ThresholdCriteria threshold = new ComposedCriteria(new TestsPerClassCriteria(2), new LevelBasedCriteria(0));
		assertTrue(threshold.shoudlStop(1, new Combination(this.alphaCluster, this.betaCluster), BigDecimal.ONE));
	}

	@Test
	void shouldStopInBoth() throws Exception {
		ThresholdCriteria threshold = new ComposedCriteria(new TestsPerClassCriteria(1), new LevelBasedCriteria(0));
		assertTrue(threshold.shoudlStop(1, new Combination(this.alphaCluster, this.betaCluster), BigDecimal.ONE));
	}

}
