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
class LevelBasedCriteriaTest {

	private static Cluster alphaCluster;
	private static Cluster betaCluster;
	private static Cluster gammaCluster;
	private static Cluster alphaBetaCluster;

	@BeforeEach
	void seutp() {
		TestCase alpha = new TestCase("alpha", Arrays.asList(), Arrays.asList());
		TestCase beta = new TestCase("beta", Arrays.asList(), Arrays.asList());
		TestCase gamma = new TestCase("gamma", Arrays.asList(), Arrays.asList());
		this.alphaCluster = new Cluster(alpha);
		this.betaCluster = new Cluster(beta);
		this.gammaCluster = new Cluster(gamma);
		this.alphaBetaCluster = this.alphaCluster.merge(this.betaCluster);
	}

	@Test
	void nextLevelIsOneAndMaximumLevelIsZero() throws Exception {
		ThresholdCriteria threshold = new LevelBasedCriteria(0);
		assertTrue(threshold.shoudlStop(1, new Combination(this.alphaCluster, this.betaCluster), BigDecimal.ONE));
	}

	@Test
	void nextLevelIsOneAndMaximumLevelIsOne() throws Exception {
		ThresholdCriteria threshold = new LevelBasedCriteria(1);
		assertFalse(threshold.shoudlStop(1, new Combination(this.alphaCluster, this.betaCluster), BigDecimal.ONE));
	}

	@Test
	void nextLevelIsTwoAndMaximumLevelIsOne() throws Exception {
		ThresholdCriteria threshold = new LevelBasedCriteria(1);
		assertTrue(threshold.shoudlStop(2, new Combination(this.alphaBetaCluster, this.gammaCluster), BigDecimal.ONE));
	}

	@Test
	void nextLevelIsTwoAndMaximumLevelIsTwo() throws Exception {
		ThresholdCriteria threshold = new LevelBasedCriteria(2);
		assertFalse(threshold.shoudlStop(2, new Combination(this.alphaBetaCluster, this.gammaCluster), BigDecimal.ONE));
	}

	@Test
	void cantStopAtNegativeLevel() throws Exception {
		assertThrows(InvalidThresholdException.class, () -> {
			new LevelBasedCriteria(-1);
		});
	}

}
