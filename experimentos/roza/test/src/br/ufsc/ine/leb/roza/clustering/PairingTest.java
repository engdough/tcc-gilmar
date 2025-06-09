package src.br.ufsc.ine.leb.roza.clustering;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;

import src.br.ufsc.ine.leb.roza.Cluster;
import src.br.ufsc.ine.leb.roza.TestCase;
import sunit.runner.SunitRunner;

@ExtendWith(SunitRunner.class)
@TestMethodOrder(SunitRunner.class)
class PairingTest {

	private static TestCase alpha;
	private static TestCase beta;
	private static TestCase gamma;
	private static Pair alphaAlphaPair;
	private static Pair alphaBetaPair;
	private static Pair betaAlphaPair;
	private static Pair alphaGammaPair;
	private static Pair gammaAlphaPair;
	private static Pair betaGammaPair;
	private static Pair gammaBetaPair;

	@BeforeEach
	void setup() {
		this.alpha = new TestCase("alpha", Arrays.asList(), Arrays.asList());
		this.beta = new TestCase("beta", Arrays.asList(), Arrays.asList());
		this.gamma = new TestCase("gamma", Arrays.asList(), Arrays.asList());
		this.alphaAlphaPair = new Pair(this.alpha, this.alpha);
		this.alphaBetaPair = new Pair(this.alpha, this.beta);
		this.betaAlphaPair = new Pair(this.beta, this.alpha);
		this.alphaGammaPair = new Pair(this.alpha, this.gamma);
		this.gammaAlphaPair = new Pair(this.gamma, this.alpha);
		this.betaGammaPair = new Pair(this.beta, this.gamma);
		this.gammaBetaPair = new Pair(this.gamma, this.beta);
	}

	@Test
	void pairClusterWithItself() throws Exception {
		Cluster alphaCluster = new Cluster(this.alpha);
		Set<Pair> alphaPairedAlpha = new Pairing(alphaCluster, alphaCluster).getPairs();
		assertEquals(1, alphaPairedAlpha.size());
		assertTrue(alphaPairedAlpha.contains(this.alphaAlphaPair));
	}

	@Test
	void pairClustersWithTheSameElement() throws Exception {
		Cluster alphaCluster = new Cluster(this.alpha);
		Set<Pair> alphaPairedAlpha = new Pairing(alphaCluster, new Cluster(this.alpha)).getPairs();
		assertEquals(1, alphaPairedAlpha.size());
		assertTrue(alphaPairedAlpha.contains(this.alphaAlphaPair));
	}

	@Test
	void pairClustersWithDistincElements() throws Exception {
		Cluster alphaCluster = new Cluster(this.alpha);
		Cluster betaCluster = new Cluster(this.beta);
		Set<Pair> alphaPairedBeta = new Pairing(alphaCluster, betaCluster).getPairs();
		Set<Pair> betaPairedAlpha = new Pairing(betaCluster, alphaCluster).getPairs();

		assertEquals(2, alphaPairedBeta.size());
		assertTrue(alphaPairedBeta.contains(this.alphaBetaPair));
		assertTrue(alphaPairedBeta.contains(this.betaAlphaPair));

		assertEquals(2, betaPairedAlpha.size());
		assertTrue(betaPairedAlpha.contains(this.alphaBetaPair));
		assertTrue(betaPairedAlpha.contains(this.betaAlphaPair));
	}

	@Test
	void pairClustersWithDistincElementsAndDistinctSizes() throws Exception {
		Cluster alphaCluster = new Cluster(this.alpha);
		Cluster betaCluster = new Cluster(this.beta);
		Cluster gammaCluster = new Cluster(this.gamma);
		Cluster alphaBetaCluster = alphaCluster.merge(betaCluster);
		Set<Pair> alphaBetaPairedGamma = new Pairing(alphaBetaCluster, gammaCluster).getPairs();
		Set<Pair> gammaPairedAlphaBeta = new Pairing(gammaCluster, alphaBetaCluster).getPairs();

		assertEquals(4, alphaBetaPairedGamma.size());
		assertTrue(alphaBetaPairedGamma.contains(this.alphaGammaPair));
		assertTrue(alphaBetaPairedGamma.contains(this.betaGammaPair));
		assertTrue(alphaBetaPairedGamma.contains(this.gammaAlphaPair));
		assertTrue(alphaBetaPairedGamma.contains(this.gammaBetaPair));

		assertEquals(4, gammaPairedAlphaBeta.size());
		assertTrue(gammaPairedAlphaBeta.contains(this.alphaGammaPair));
		assertTrue(gammaPairedAlphaBeta.contains(this.betaGammaPair));
		assertTrue(gammaPairedAlphaBeta.contains(this.gammaAlphaPair));
		assertTrue(gammaPairedAlphaBeta.contains(this.gammaBetaPair));
	}

	@Test
	void pairClustersWithDistincElementsAndDistinctSizesWithoutRepetition() throws Exception {
		Cluster alphaCluster = new Cluster(this.alpha);
		Cluster betaCluster = new Cluster(this.beta);
		Cluster gammaCluster = new Cluster(this.gamma);
		Cluster alphaBetaCluster = alphaCluster.merge(betaCluster);
		Set<Pair> alphaBetaPairedGamma = new Pairing(alphaBetaCluster, gammaCluster).getPairsWithoutRepetition();
		Set<Pair> gammaPairedAlphaBeta = new Pairing(gammaCluster, alphaBetaCluster).getPairsWithoutRepetition();

		assertEquals(2, alphaBetaPairedGamma.size());
		assertTrue(alphaBetaPairedGamma.contains(this.alphaGammaPair));
		assertTrue(alphaBetaPairedGamma.contains(this.betaGammaPair));

		assertEquals(2, gammaPairedAlphaBeta.size());
		assertTrue(gammaPairedAlphaBeta.contains(this.gammaAlphaPair));
		assertTrue(gammaPairedAlphaBeta.contains(this.gammaBetaPair));
	}

}
