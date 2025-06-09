package src.br.ufsc.ine.leb.roza.clustering;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
class CombinationTest {

	private static Cluster alphaCluster;
	private static Cluster betaCluster;
	private static Combination alphaBetaCombination;
	private static Combination betaAlphaCombination;

	@BeforeEach
	void setup() {
		TestCase alpha = new TestCase("alpha", Arrays.asList(), Arrays.asList());
		TestCase beta = new TestCase("beta", Arrays.asList(), Arrays.asList());
		this.alphaCluster = new Cluster(alpha);
		this.betaCluster = new Cluster(beta);
		this.alphaBetaCombination = new Combination(this.alphaCluster, this.betaCluster);
		this.betaAlphaCombination = new Combination(this.betaCluster, this.alphaCluster);
	}

	@Test
	void alphaBeta() throws Exception {
		assertEquals(this.alphaCluster, this.alphaBetaCombination.getFirst());
		assertEquals(this.betaCluster, this.alphaBetaCombination.getSecond());
		assertEquals(this.alphaBetaCombination, new Combination(this.alphaCluster, this.betaCluster));
		assertEquals(this.alphaBetaCombination.hashCode(), new Combination(this.alphaCluster, this.betaCluster).hashCode());
		assertEquals(this.alphaBetaCombination, this.betaAlphaCombination);
	}

	@Test
	void betaAlpha() throws Exception {
		assertEquals(this.betaCluster, this.betaAlphaCombination.getFirst());
		assertEquals(this.alphaCluster, this.betaAlphaCombination.getSecond());
		assertEquals(this.betaAlphaCombination, new Combination(this.betaCluster, this.alphaCluster));
		assertEquals(this.betaAlphaCombination.hashCode(), new Combination(this.betaCluster, this.alphaCluster).hashCode());
		assertEquals(this.betaAlphaCombination, this.alphaBetaCombination);
	}

}
