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
class TestsPerClassCriteriaTest {

	private static Cluster alphaCluster;
	private static Cluster betaCluster;
	private static Cluster gammaCluster;
	private static Cluster deltaCluster;
	private static Cluster alphaBetaCluster;
	private static Cluster gammaDeltaCluster;

	@BeforeEach
	void setup() {
		TestCase alpha = new TestCase("alpha", Arrays.asList(), Arrays.asList());
		TestCase beta = new TestCase("beta", Arrays.asList(), Arrays.asList());
		TestCase gamma = new TestCase("gamma", Arrays.asList(), Arrays.asList());
		TestCase delta = new TestCase("delta", Arrays.asList(), Arrays.asList());
		this.alphaCluster = new Cluster(alpha);
		this.betaCluster = new Cluster(beta);
		this.gammaCluster = new Cluster(gamma);
		this.deltaCluster = new Cluster(delta);
		this.alphaBetaCluster = this.alphaCluster.merge(this.betaCluster);
		this.gammaDeltaCluster = this.gammaCluster.merge(this.deltaCluster);
	}

	@Test
	void nextLevelIsOneAndMaximumIsOne() throws Exception {
		ThresholdCriteria criteria = new TestsPerClassCriteria(1);
		assertTrue(criteria.shoudlStop(1, new Combination(this.alphaCluster, this.betaCluster), BigDecimal.ONE));
	}

	@Test
	void nextLevelIsOneAndMaximuIsTwo() throws Exception {
		ThresholdCriteria criteria = new TestsPerClassCriteria(2);
		assertFalse(criteria.shoudlStop(1, new Combination(this.alphaCluster, this.betaCluster), BigDecimal.ONE));
	}

	@Test
	void nextLevelIsTwoAndMaximumIsTwo() throws Exception {
		ThresholdCriteria criteria = new TestsPerClassCriteria(2);
		assertTrue(criteria.shoudlStop(2, new Combination(this.alphaBetaCluster, this.gammaCluster), BigDecimal.ONE));
	}

	@Test
	void nextLevelIsTwoAndMaximumIsThree() throws Exception {
		ThresholdCriteria criteria = new TestsPerClassCriteria(3);
		assertFalse(criteria.shoudlStop(2, new Combination(this.alphaBetaCluster, this.gammaCluster), BigDecimal.ONE));
	}

	@Test
	void nextLevelIsThreeAndMaximumIsThree() throws Exception {
		ThresholdCriteria criteria = new TestsPerClassCriteria(3);
		assertTrue(criteria.shoudlStop(3, new Combination(this.alphaBetaCluster, this.gammaDeltaCluster), BigDecimal.ONE));
	}

	@Test
	void nextLevelIsThreeAndMaximumIsFour() throws Exception {
		ThresholdCriteria criteria = new TestsPerClassCriteria(4);
		assertFalse(criteria.shoudlStop(3, new Combination(this.alphaBetaCluster, this.gammaDeltaCluster), BigDecimal.ONE));
	}

	@Test
	void lessThanOneCriteria() throws Exception {
		assertThrows(InvalidThresholdException.class, () -> {
			new TestsPerClassCriteria(0);
		});
	}

}
