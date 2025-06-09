package src.br.ufsc.ine.leb.roza;

import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import sunit.runner.SunitRunner;

@ExtendWith(SunitRunner.class)
@TestMethodOrder(SunitRunner.class)
class ClusterTest {

	private static TestCase alpha;
	private static TestCase beta;
	private static TestCase gamma;

	@BeforeEach
	void setup() {
		this.alpha = new TestCase("alpha", Arrays.asList(), Arrays.asList());
		this.beta = new TestCase("beta", Arrays.asList(), Arrays.asList());
		this.gamma = new TestCase("gamma", Arrays.asList(), Arrays.asList());
	}

	@Test
	void cluster() throws Exception {
		Cluster alphaCluster = new Cluster(this.alpha);
		Assertions.assertEquals(1, alphaCluster.getTestCases().size());
		Assertions.assertTrue(alphaCluster.getTestCases().contains(this.alpha));
		Assertions.assertEquals(alphaCluster, alphaCluster);
		Assertions.assertEquals(alphaCluster, new Cluster(this.alpha));
		Assertions.assertEquals(alphaCluster.hashCode(), new Cluster(this.alpha).hashCode());
	}

	@Test
	void mergeClusterWithItself() throws Exception {
		Cluster alphaCluster = new Cluster(this.alpha);
		Cluster alphaMergedAlpha = alphaCluster.merge(alphaCluster);
		Assertions.assertEquals(1, alphaMergedAlpha.getTestCases().size());
		Assertions.assertTrue(alphaMergedAlpha.getTestCases().contains(this.alpha));
		Assertions.assertEquals(alphaMergedAlpha, alphaCluster);
		Assertions.assertEquals(alphaMergedAlpha.hashCode(), alphaCluster.hashCode());
	}

	@Test
	void mergeClustersWithTheSameElement() throws Exception {
		Cluster alphaCluster = new Cluster(this.alpha);
		Cluster alphaMergedAlpha = alphaCluster.merge(new Cluster(this.alpha));
		Assertions.assertEquals(1, alphaMergedAlpha.getTestCases().size());
		Assertions.assertTrue(alphaMergedAlpha.getTestCases().contains(this.alpha));
		Assertions.assertEquals(alphaMergedAlpha, alphaCluster);
		Assertions.assertEquals(alphaMergedAlpha.hashCode(), alphaCluster.hashCode());
	}

	@Test
	void mergeClustersWithDistincElements() throws Exception {
		Cluster alphaCluster = new Cluster(this.alpha);
		Cluster betaCluster = new Cluster(this.beta);
		Cluster alphaMergedBeta = alphaCluster.merge(betaCluster);
		Cluster betaMergedAlpha = betaCluster.merge(alphaCluster);

		Assertions.assertEquals(alphaMergedBeta, betaMergedAlpha);
		Assertions.assertEquals(2, alphaMergedBeta.getTestCases().size());
		Assertions.assertTrue(alphaMergedBeta.getTestCases().contains(this.alpha));
		Assertions.assertTrue(alphaMergedBeta.getTestCases().contains(this.beta));

		Assertions.assertEquals(betaMergedAlpha, alphaMergedBeta);
		Assertions.assertEquals(2, alphaMergedBeta.getTestCases().size());
		Assertions.assertTrue(betaMergedAlpha.getTestCases().contains(this.alpha));
		Assertions.assertTrue(betaMergedAlpha.getTestCases().contains(this.beta));
	}

	@Test
	void mergeClustersWithDistincElementsAndDistinctSizes() throws Exception {
		Cluster alphaCluster = new Cluster(this.alpha);
		Cluster betaCluster = new Cluster(this.beta);
		Cluster gammaCluster = new Cluster(this.gamma);
		Cluster alphaBetaCluster = alphaCluster.merge(betaCluster);
		Cluster alphaBetaMergedGamma = alphaBetaCluster.merge(gammaCluster);
		Cluster gammaMergedAlphaBeta = gammaCluster.merge(alphaBetaCluster);

		Assertions.assertEquals(alphaBetaMergedGamma, gammaMergedAlphaBeta);
		Assertions.assertEquals(3, alphaBetaMergedGamma.getTestCases().size());
		Assertions.assertTrue(alphaBetaMergedGamma.getTestCases().contains(this.alpha));
		Assertions.assertTrue(alphaBetaMergedGamma.getTestCases().contains(this.beta));
		Assertions.assertTrue(alphaBetaMergedGamma.getTestCases().contains(this.gamma));

		Assertions.assertEquals(gammaMergedAlphaBeta, alphaBetaMergedGamma);
		Assertions.assertEquals(3, gammaMergedAlphaBeta.getTestCases().size());
		Assertions.assertTrue(gammaMergedAlphaBeta.getTestCases().contains(this.alpha));
		Assertions.assertTrue(gammaMergedAlphaBeta.getTestCases().contains(this.beta));
		Assertions.assertTrue(gammaMergedAlphaBeta.getTestCases().contains(this.gamma));
	}

	@Test
	void mergeClustersWithOneEqualElementsAndOneDistincElement() throws Exception {
		Cluster alphaCluster = new Cluster(this.alpha);
		Cluster betaCluster = new Cluster(this.beta);
		Cluster gammaCluster = new Cluster(this.gamma);
		Cluster alphaMergedGamma = alphaCluster.merge(gammaCluster);
		Cluster betaMergedGama = betaCluster.merge(gammaCluster);

		Assertions.assertNotEquals(alphaMergedGamma, betaMergedGama);
		Assertions.assertEquals(2, alphaMergedGamma.getTestCases().size());
		Assertions.assertTrue(alphaMergedGamma.getTestCases().contains(this.alpha));
		Assertions.assertFalse(alphaMergedGamma.getTestCases().contains(this.beta));
		Assertions.assertTrue(alphaMergedGamma.getTestCases().contains(this.gamma));

		Assertions.assertNotEquals(betaMergedGama, alphaMergedGamma);
		Assertions.assertEquals(2, betaMergedGama.getTestCases().size());
		Assertions.assertFalse(betaMergedGama.getTestCases().contains(this.alpha));
		Assertions.assertTrue(betaMergedGama.getTestCases().contains(this.beta));
		Assertions.assertTrue(betaMergedGama.getTestCases().contains(this.gamma));
	}

}
