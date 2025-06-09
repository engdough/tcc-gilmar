package src.br.ufsc.ine.leb.roza.clustering;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;

import src.br.ufsc.ine.leb.roza.Cluster;
import src.br.ufsc.ine.leb.roza.SimilarityReport;
import src.br.ufsc.ine.leb.roza.SimilarityReportBuilder;
import src.br.ufsc.ine.leb.roza.TestCase;
import src.br.ufsc.ine.leb.roza.exceptions.TiebreakException;
import src.br.ufsc.ine.leb.roza.utils.CollectionUtils;
import sunit.runner.SunitRunner;

@ExtendWith(SunitRunner.class)
@TestMethodOrder(SunitRunner.class)
class ClusterJoinerWithAverageLinkageTest {

	private static BigDecimal dotTwo;
	private static BigDecimal dotThree;
	private static BigDecimal dotFour;
	private static BigDecimal dotFive;
	private static BigDecimal dotFiveFive;
	private static BigDecimal dotSix;
	private static BigDecimal dotSeven;
	private static BigDecimal dotEight;
	private static BigDecimal dotNine;
	private static TestCase alpha;
	private static TestCase beta;
	private static TestCase gamma;
	private static TestCase delta;
	private static Cluster alphaCluster;
	private static Cluster betaCluster;
	private static Cluster gammaCluster;
	private static Cluster deltaCluster;
	private static Cluster alphaGammaCluster;
	private static CollectionUtils collectionUtils;
	private static InsecureReferee referee;

	@BeforeEach
	void setup() {
		this.dotTwo = new BigDecimal("0.2");
		this.dotThree = new BigDecimal("0.3");
		this.dotFour = new BigDecimal("0.4");
		this.dotFive = new BigDecimal("0.5");
		this.dotFiveFive = new BigDecimal("0.55");
		this.dotSix = new BigDecimal("0.6");
		this.dotSeven = new BigDecimal("0.7");
		this.dotEight = new BigDecimal("0.8");
		this.dotNine = new BigDecimal("0.9");
		this.alpha = new TestCase("alpha", Arrays.asList(), Arrays.asList());
		this.beta = new TestCase("beta", Arrays.asList(), Arrays.asList());
		this.gamma = new TestCase("gamma", Arrays.asList(), Arrays.asList());
		this.delta = new TestCase("delta", Arrays.asList(), Arrays.asList());
		this.alphaCluster = new Cluster(this.alpha);
		this.betaCluster = new Cluster(this.beta);
		this.gammaCluster = new Cluster(this.gamma);
		this.deltaCluster = new Cluster(this.delta);
		this.alphaGammaCluster = this.alphaCluster.merge(this.gammaCluster);
		this.collectionUtils = new CollectionUtils();
		this.referee = new InsecureReferee();
	}

	@Test
	void linkSingleElementClusters() {
		SimilarityReportBuilder builder = new SimilarityReportBuilder(true);
		builder.add(this.alpha, this.beta, this.dotTwo).add(this.alpha, this.gamma, this.dotEight).add(this.alpha, this.delta, this.dotSeven);
		builder.add(this.beta, this.gamma, this.dotFive).add(this.beta, this.delta, this.dotSix);
		builder.add(this.gamma, this.delta, this.dotThree);
		SimilarityReport report = builder.build();
		ClustersToMerge clusters = new ClustersToMerge(this.collectionUtils.set(this.alphaGammaCluster, this.betaCluster, this.deltaCluster));
		WinnerCombination winner = new ClusterJoiner(new AverageLinkage(report), this.referee).join(clusters);
		Combination combination = winner.getCombination();
		Assertions.assertEquals(new Combination(this.betaCluster, this.deltaCluster), combination);
		Assertions.assertEquals(new Combination(this.deltaCluster, this.betaCluster), combination);
		Assertions.assertEquals(this.dotSix, winner.getEvaluation());
	}

	@Test
	void linkSingleElementClusterWithNonSingleElementCluster() {
		SimilarityReportBuilder builder = new SimilarityReportBuilder(true);
		builder.add(this.alpha, this.beta, this.dotSeven).add(this.alpha, this.gamma, this.dotEight).add(this.alpha, this.delta, this.dotThree);
		builder.add(this.beta, this.gamma, this.dotFour).add(this.beta, this.delta, this.dotTwo);
		builder.add(this.gamma, this.delta, this.dotFive);
		SimilarityReport report = builder.build();
		ClustersToMerge clusters = new ClustersToMerge(this.collectionUtils.set(this.alphaGammaCluster, this.betaCluster, this.deltaCluster));
		WinnerCombination winner = new ClusterJoiner(new AverageLinkage(report), this.referee).join(clusters);
		Combination combination = winner.getCombination();
		Assertions.assertEquals(new Combination(this.alphaGammaCluster, this.betaCluster), combination);
		Assertions.assertEquals(new Combination(this.betaCluster, this.alphaGammaCluster), combination);
		Assertions.assertEquals(this.dotFiveFive, winner.getEvaluation());
	}

	@Test
	void nonSymmetricAlphaBeta() {
		SimilarityReportBuilder builder = new SimilarityReportBuilder(false);
		builder.add(this.alpha, this.beta, this.dotFive).add(this.alpha, this.gamma, this.dotSix);
		builder.add(this.beta, this.alpha, this.dotSeven).add(this.beta, this.gamma, this.dotSix);
		builder.add(this.gamma, this.alpha, this.dotSix).add(this.gamma, this.beta, this.dotSix);
		SimilarityReport report = builder.build();
		ClustersToMerge clusters = new ClustersToMerge(this.collectionUtils.set(this.alphaCluster, this.betaCluster, this.gammaCluster));
		WinnerCombination winner = new ClusterJoiner(new AverageLinkage(report), this.referee).join(clusters);
		Combination combination = winner.getCombination();
		Assertions.assertEquals(new Combination(this.betaCluster, this.alphaCluster), combination);
		Assertions.assertEquals(new Combination(this.alphaCluster, this.betaCluster), combination);
		Assertions.assertEquals(this.dotSeven, winner.getEvaluation());
	}

	@Test
	void nonSymmetricBetaAlpha() {
		SimilarityReportBuilder builder = new SimilarityReportBuilder(false);
		builder.add(this.alpha, this.beta, this.dotSeven).add(this.alpha, this.gamma, this.dotSix);
		builder.add(this.beta, this.alpha, this.dotFive).add(this.beta, this.gamma, this.dotSix);
		builder.add(this.gamma, this.alpha, this.dotSix).add(this.gamma, this.beta, this.dotSix);
		SimilarityReport report = builder.build();
		ClustersToMerge clusters = new ClustersToMerge(this.collectionUtils.set(this.alphaCluster, this.betaCluster, this.gammaCluster));
		WinnerCombination winner = new ClusterJoiner(new AverageLinkage(report), this.referee).join(clusters);
		Combination combination = winner.getCombination();
		Assertions.assertEquals(new Combination(this.betaCluster, this.alphaCluster), combination);
		Assertions.assertEquals(new Combination(this.alphaCluster, this.betaCluster), combination);
		Assertions.assertEquals(this.dotSeven, winner.getEvaluation());
	}

	@Test
	void multiplePossibilities() {
		SimilarityReportBuilder builder = new SimilarityReportBuilder(false);
		builder.add(this.alpha, this.beta, dotFive).add(this.alpha, this.gamma, this.dotFive);
		builder.add(this.beta, this.alpha, dotFive).add(this.beta, this.gamma, this.dotNine);
		builder.add(this.gamma, this.alpha, dotNine).add(this.gamma, this.beta, this.dotFive);
		SimilarityReport report = builder.build();
		ClustersToMerge clusters = new ClustersToMerge(this.collectionUtils.set(this.alphaCluster, this.betaCluster, this.gammaCluster));
		TiebreakException exception = assertThrows(TiebreakException.class, () -> {
			new ClusterJoiner(new AverageLinkage(report), this.referee).join(clusters);
		});
		Assertions.assertEquals(2, exception.getTies().size());
		assertTrue(exception.getTies().contains(new Combination(this.alphaCluster, this.gammaCluster)));
		assertTrue(exception.getTies().contains(new Combination(this.betaCluster, this.gammaCluster)));
	}

}
