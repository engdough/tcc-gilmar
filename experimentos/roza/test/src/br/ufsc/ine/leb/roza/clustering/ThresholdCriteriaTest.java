package src.br.ufsc.ine.leb.roza.clustering;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;

import src.br.ufsc.ine.leb.roza.Cluster;
import src.br.ufsc.ine.leb.roza.TestCase;
import sunit.runner.SunitRunner;

@ExtendWith(SunitRunner.class)
@TestMethodOrder(SunitRunner.class)
class ThresholdCriteriaTest {

	private static Combination combination;
	private static Integer level;

	@BeforeEach
	void seutp() {
		TestCase alpha = new TestCase("alpha", Arrays.asList(), Arrays.asList());
		TestCase beta = new TestCase("beta", Arrays.asList(), Arrays.asList());
		Cluster alphaCluster = new Cluster(alpha);
		Cluster betaCluster = new Cluster(beta);
		this.combination = new Combination(alphaCluster, betaCluster);
		this.level = 1;
	}

	@Test
	void alwaysStopCriteria() throws Exception {
		ThresholdCriteria threshold = new AlwaysStopCriteria();
		assertTrue(threshold.shoudlStop(this.level, this.combination, BigDecimal.ONE));
	}

	@Test
	void neverStopCriteria() throws Exception {
		ThresholdCriteria threshold = new NeverStopCriteria();
		assertFalse(threshold.shoudlStop(this.level, this.combination, BigDecimal.ONE));
	}

}
