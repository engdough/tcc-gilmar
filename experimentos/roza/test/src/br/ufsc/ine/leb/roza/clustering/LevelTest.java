package src.br.ufsc.ine.leb.roza.clustering;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
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
class LevelTest {

	private static Cluster alphaCluster;
	private static Cluster betaCluster;
	private static Cluster gammaCluster;
	private static Cluster alphaBetaCluster;

	@BeforeEach
	void setup() {
		TestCase alpha = new TestCase("alpha", Arrays.asList(), Arrays.asList());
		TestCase beta = new TestCase("beta", Arrays.asList(), Arrays.asList());
		TestCase gamma = new TestCase("gamma", Arrays.asList(), Arrays.asList());
		this.alphaCluster = new Cluster(alpha);
		this.betaCluster = new Cluster(beta);
		this.gammaCluster = new Cluster(gamma);
		this.alphaBetaCluster = this.alphaCluster.merge(this.betaCluster);
	}

	@Test
	void levelZeroWithoutElements() throws Exception {
		Set<Cluster> clusters = new HashSet<>();
		Level zero = new Level(clusters);

		assertEquals(0, zero.getStep());
		assertEquals(0, zero.getClusters().size());
		assertNull(zero.getEvaluationToThisLevel());
	}

	@Test
	void levelZeroWithOneElement() throws Exception {
		Set<Cluster> clusters = new HashSet<>();
		clusters.add(this.alphaCluster);
		Level zero = new Level(clusters);

		assertEquals(0, zero.getStep());
		assertEquals(1, zero.getClusters().size());
		assertTrue(zero.getClusters().contains(this.alphaCluster));
		assertNull(zero.getEvaluationToThisLevel());
	}

	@Test
	void levelOne() throws Exception {
		Set<Cluster> clustersZero = new HashSet<>();
		clustersZero.add(this.alphaCluster);
		clustersZero.add(this.betaCluster);
		clustersZero.add(this.gammaCluster);
		Set<Cluster> clustersOne = new HashSet<>();
		clustersOne.add(this.alphaBetaCluster);
		clustersOne.add(this.gammaCluster);
		Level zero = new Level(clustersZero);
		Level one = new Level(zero, clustersOne, BigDecimal.ONE);

		assertEquals(1, one.getStep());
		assertEquals(2, one.getClusters().size());
		assertTrue(one.getClusters().contains(this.alphaBetaCluster));
		assertTrue(one.getClusters().contains(this.gammaCluster));
		assertEquals(BigDecimal.ONE, one.getEvaluationToThisLevel());
	}

}
