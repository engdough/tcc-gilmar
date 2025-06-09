package src.br.ufsc.ine.leb.roza.clustering;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;

import src.br.ufsc.ine.leb.roza.Cluster;
import src.br.ufsc.ine.leb.roza.SimilarityReportBuilder;
import src.br.ufsc.ine.leb.roza.TestCase;
import sunit.runner.SunitRunner;

@ExtendWith(SunitRunner.class)
@TestMethodOrder(SunitRunner.class)
class SingleLinkageTest {

	private static BigDecimal dotTwo;
	private static BigDecimal dotThree;
	private static BigDecimal dotFour;
	private static BigDecimal dotFive;
	private static BigDecimal dotSix;
	private static BigDecimal dotSeven;
	private static BigDecimal dotEight;
	private static TestCase alpha;
	private static TestCase beta;
	private static TestCase gamma;
	private static TestCase delta;
	private static Cluster alphaCluster;
	private static Cluster betaCluster;
	private static Cluster gammaCluster;
	private static Cluster deltaCluster;
	private static Cluster alphaBetaCluster;
	private static Cluster gammaDeltaCluster;

	@BeforeEach
	void setup() {
		this.dotTwo = new BigDecimal("0.2");
		this.dotThree = new BigDecimal("0.3");
		this.dotFour = new BigDecimal("0.4");
		this.dotFive = new BigDecimal("0.5");
		this.dotSix = new BigDecimal("0.6");
		this.dotSeven = new BigDecimal("0.7");
		this.dotEight = new BigDecimal("0.8");
		this.alpha = new TestCase("alpha", Arrays.asList(), Arrays.asList());
		this.beta = new TestCase("beta", Arrays.asList(), Arrays.asList());
		this.gamma = new TestCase("gamma", Arrays.asList(), Arrays.asList());
		this.delta = new TestCase("delta", Arrays.asList(), Arrays.asList());
		this.alphaCluster = new Cluster(this.alpha);
		this.betaCluster = new Cluster(this.beta);
		this.gammaCluster = new Cluster(this.gamma);
		this.deltaCluster = new Cluster(this.delta);
		this.alphaBetaCluster = this.alphaCluster.merge(this.betaCluster);
		this.gammaDeltaCluster = this.gammaCluster.merge(this.deltaCluster);
	}

	@Test
	void singleElementClustersWithSymmetricSimilarity() throws Exception {
		SimilarityReportBuilder builder = new SimilarityReportBuilder(true);
		builder.add(this.alpha, this.beta, this.dotFive);
		Linkage linkage = new SingleLinkage(builder.build());
		assertEquals(this.dotFive, linkage.evaluate(this.alphaCluster, this.betaCluster));
	}

	@Test
	void singleAndNonSingleElementClustersWithSymmetricSimilarity() throws Exception {
		SimilarityReportBuilder builder = new SimilarityReportBuilder(true);
		builder.add(this.alpha, this.beta, this.dotEight).add(this.alpha, this.gamma, this.dotTwo);
		builder.add(this.beta, this.gamma, this.dotSix);
		Linkage linkage = new SingleLinkage(builder.build());
		assertEquals(this.dotSix, linkage.evaluate(this.alphaBetaCluster, this.gammaCluster).stripTrailingZeros());
	}

	@Test
	void nonSingleElementClustersWithSymmetricSimilarity() throws Exception {
		SimilarityReportBuilder builder = new SimilarityReportBuilder(true);
		builder.add(this.alpha, this.beta, this.dotEight).add(this.alpha, this.gamma, this.dotTwo).add(this.alpha, this.delta, this.dotFour);
		builder.add(this.beta, this.gamma, this.dotSix).add(this.beta, this.delta, this.dotSeven);
		builder.add(this.gamma, this.delta, this.dotEight);
		Linkage linkage = new SingleLinkage(builder.build());
		assertEquals(this.dotSeven, linkage.evaluate(this.alphaBetaCluster, this.gammaDeltaCluster).stripTrailingZeros());
	}

	@Test
	void singleElementClustersWithAsymmetricSymilarityWithTheSameValues() throws Exception {
		SimilarityReportBuilder builder = new SimilarityReportBuilder(false);
		builder.add(this.alpha, this.beta, this.dotFive);
		builder.add(this.beta, this.alpha, this.dotFive);
		Linkage linkage = new SingleLinkage(builder.build());
		assertEquals(this.dotFive, linkage.evaluate(this.alphaCluster, this.betaCluster));
	}

	@Test
	void singleElementClustersWithAsymmetricSymilarityWithTheDistinctValues() throws Exception {
		SimilarityReportBuilder builder = new SimilarityReportBuilder(false);
		builder.add(this.alpha, this.beta, this.dotTwo);
		builder.add(this.beta, this.alpha, this.dotEight);
		Linkage linkage = new SingleLinkage(builder.build());
		assertEquals(this.dotEight, linkage.evaluate(this.alphaCluster, this.betaCluster));
	}

	@Test
	void singleAndNonSingleElementClustersWithAsymmetricSimilarity() throws Exception {
		SimilarityReportBuilder builder = new SimilarityReportBuilder(false);
		builder.add(this.alpha, this.beta, this.dotEight).add(this.alpha, this.gamma, this.dotTwo);
		builder.add(this.beta, this.alpha, this.dotEight).add(this.beta, this.gamma, this.dotThree);
		builder.add(this.gamma, this.alpha, this.dotFour).add(this.gamma, this.beta, this.dotFive);
		Linkage linkage = new SingleLinkage(builder.build());
		assertEquals(this.dotFive, linkage.evaluate(this.alphaBetaCluster, this.gammaCluster).stripTrailingZeros());
	}

	@Test
	void nonSingleElementClustersWithAsymmetricSimilarity() throws Exception {
		SimilarityReportBuilder builder = new SimilarityReportBuilder(false);
		builder.add(this.alpha, this.beta, this.dotEight).add(this.alpha, this.gamma, this.dotTwo).add(this.alpha, this.delta, this.dotThree);
		builder.add(this.beta, this.alpha, this.dotEight).add(this.beta, this.gamma, this.dotFour).add(this.beta, this.delta, this.dotFive);
		builder.add(this.gamma, this.alpha, this.dotSix).add(this.gamma, this.beta, this.dotSeven).add(this.gamma, this.delta, this.dotEight);
		builder.add(this.delta, this.alpha, this.dotTwo).add(this.delta, this.beta, this.dotThree).add(this.delta, this.gamma, this.dotEight);
		Linkage linkage = new SingleLinkage(builder.build());
		assertEquals(this.dotSeven, linkage.evaluate(this.alphaBetaCluster, this.gammaDeltaCluster).stripTrailingZeros());
	}

}
