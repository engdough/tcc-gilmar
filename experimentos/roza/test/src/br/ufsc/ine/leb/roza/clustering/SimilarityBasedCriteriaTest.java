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
class SimilarityBasedCriteriaTest {

	private static BigDecimal dotOne;
	private static BigDecimal dotFour;
	private static BigDecimal dotFive;
	private static BigDecimal dotSix;
	private static BigDecimal dotNine;
	private static Combination combination;
	private static Integer level;

	@BeforeEach
	void setup() {
		TestCase alpha = new TestCase("alpha", Arrays.asList(), Arrays.asList());
		TestCase beta = new TestCase("beta", Arrays.asList(), Arrays.asList());
		Cluster alphaCluster = new Cluster(alpha);
		Cluster betaCluster = new Cluster(beta);
		this.combination = new Combination(alphaCluster, betaCluster);
		this.dotOne = new BigDecimal("0.1");
		this.dotFour = new BigDecimal("0.4");
		this.dotFive = new BigDecimal("0.5");
		this.dotSix = new BigDecimal("0.6");
		this.dotNine = new BigDecimal("0.9");
		this.level = 1;
	}

	@Test
	void zeroSimilarity() throws Exception {
		assertTrue(new SimilarityBasedCriteria(BigDecimal.ZERO).shoudlStop(this.level, this.combination, BigDecimal.ZERO));
		assertTrue(new SimilarityBasedCriteria(this.dotOne).shoudlStop(this.level, this.combination, BigDecimal.ZERO));
	}

	@Test
	void oneSimilarity() throws Exception {
		assertFalse(new SimilarityBasedCriteria(this.dotNine).shoudlStop(this.level, this.combination, BigDecimal.ONE));
		assertTrue(new SimilarityBasedCriteria(BigDecimal.ONE).shoudlStop(this.level, this.combination, BigDecimal.ONE));
	}

	@Test
	void zeroPointFiveSimilarity() throws Exception {
		assertFalse(new SimilarityBasedCriteria(this.dotFour).shoudlStop(this.level, this.combination, this.dotFive));
		assertTrue(new SimilarityBasedCriteria(this.dotFive).shoudlStop(this.level, this.combination, this.dotFive));
		assertTrue(new SimilarityBasedCriteria(this.dotSix).shoudlStop(this.level, this.combination, this.dotFive));
	}

	@Test
	void outOfRangeThresholds() throws Exception {
		assertThrows(InvalidThresholdException.class, () -> {
			new SimilarityBasedCriteria(new BigDecimal("-0.1"));
		});
		assertThrows(InvalidThresholdException.class, () -> {
			new SimilarityBasedCriteria(new BigDecimal("1.1"));
		});
	}

}
