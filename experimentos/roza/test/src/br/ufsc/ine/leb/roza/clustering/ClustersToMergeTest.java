package src.br.ufsc.ine.leb.roza.clustering;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;

import src.br.ufsc.ine.leb.roza.Cluster;
import src.br.ufsc.ine.leb.roza.TestCase;
import src.br.ufsc.ine.leb.roza.exceptions.NoClustersToMergeException;
import src.br.ufsc.ine.leb.roza.utils.CollectionUtils;
import sunit.runner.SunitRunner;

@ExtendWith(SunitRunner.class)
@TestMethodOrder(SunitRunner.class)
class ClustersToMergeTest {

	private static Cluster alphaCluster;
	private static Cluster betaCluster;
	private static Cluster gammaCluster;
	private static CollectionUtils collectionUtils;

	@BeforeEach
	void setup() {
		TestCase alpha = new TestCase("alpha", Arrays.asList(), Arrays.asList());
		TestCase beta = new TestCase("beta", Arrays.asList(), Arrays.asList());
		TestCase gamma = new TestCase("gamma", Arrays.asList(), Arrays.asList());
		this.alphaCluster = new Cluster(alpha);
		this.betaCluster = new Cluster(beta);
		this.gammaCluster = new Cluster(gamma);
		this.collectionUtils = new CollectionUtils();
	}

	@Test
	void zeroElements() throws Exception {
		assertThrows(NoClustersToMergeException.class, () -> {
			new ClustersToMerge(this.collectionUtils.set());
		});
	}

	@Test
	void oneElement() throws Exception {
		assertThrows(NoClustersToMergeException.class, () -> {
			new ClustersToMerge(this.collectionUtils.set(this.alphaCluster));
		});
	}

	@Test
	void twoElements() throws Exception {
		ClustersToMerge clusters = new ClustersToMerge(this.collectionUtils.set(this.alphaCluster, this.betaCluster));
		Combination alphaBetaCombination = new Combination(this.alphaCluster, this.betaCluster);
		Combination betaAlphaCombination = new Combination(this.betaCluster, this.alphaCluster);
		assertEquals(1, clusters.getCombinations().size());
		assertTrue(clusters.getCombinations().contains(alphaBetaCombination));
		assertTrue(clusters.getCombinations().contains(betaAlphaCombination));
	}

	@Test
	void threeElements() throws Exception {
		ClustersToMerge clusters = new ClustersToMerge(this.collectionUtils.set(this.alphaCluster, this.betaCluster, this.gammaCluster));
		Combination alphaBetaCombination = new Combination(this.alphaCluster, this.betaCluster);
		Combination alphaGammaCombination = new Combination(this.alphaCluster, this.gammaCluster);
		Combination betaGammaCombination = new Combination(this.betaCluster, this.gammaCluster);
		assertEquals(3, clusters.getCombinations().size());
		assertTrue(clusters.getCombinations().contains(alphaBetaCombination));
		assertTrue(clusters.getCombinations().contains(alphaGammaCombination));
		assertTrue(clusters.getCombinations().contains(betaGammaCombination));
	}

}
