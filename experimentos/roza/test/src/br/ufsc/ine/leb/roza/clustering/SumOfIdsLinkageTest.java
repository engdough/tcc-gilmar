package src.br.ufsc.ine.leb.roza.clustering;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;

import src.br.ufsc.ine.leb.roza.Cluster;
import src.br.ufsc.ine.leb.roza.TestCase;
import src.br.ufsc.ine.leb.roza.utils.MathUtils;
import sunit.runner.SunitRunner;

@ExtendWith(SunitRunner.class)
@TestMethodOrder(SunitRunner.class)
class SumOfIdsLinkageTest {

	private static TestCase alpha;
	private static TestCase beta;
	private static TestCase gamma;
	private static Linkage linkage;
	private static MathUtils mathUtils;
	private static Cluster alphaCluster;
	private static Cluster betaCluster;
	private static Cluster gammaCluster;
	private static Cluster alphaBetaCluster;
	private static Cluster betaGammaCluster;

	@BeforeEach
	void setup() {
		this.linkage = new SumOfIdsLinkage();
		this.mathUtils = new MathUtils();
		this.alpha = new TestCase("alpha", Arrays.asList(), Arrays.asList());
		this.beta = new TestCase("beta", Arrays.asList(), Arrays.asList());
		this.gamma = new TestCase("gamma", Arrays.asList(), Arrays.asList());
		this.alphaCluster = new Cluster(this.alpha);
		this.betaCluster = new Cluster(this.beta);
		this.gammaCluster = new Cluster(this.gamma);
		this.alphaBetaCluster = this.alphaCluster.merge(this.betaCluster);
		this.betaGammaCluster = this.gammaCluster.merge(this.betaCluster);
	}

	@Test
	void alphaAndBeta() throws Exception {
		BigDecimal evaluation = this.linkage.evaluate(this.alphaCluster, this.betaCluster);
		assertEquals(this.mathUtils.oneOver(this.alpha.getId(), this.beta.getId()), evaluation);
	}

	@Test
	void betaAndAlpha() throws Exception {
		BigDecimal evaluation = this.linkage.evaluate(this.betaCluster, this.alphaCluster);
		assertEquals(this.mathUtils.oneOver(this.alpha.getId(), this.beta.getId()), evaluation);
	}

	@Test
	void alphaBetaAndGamma() throws Exception {
		BigDecimal evaluation = this.linkage.evaluate(this.alphaBetaCluster, this.gammaCluster);
		assertEquals(this.mathUtils.oneOver(this.alpha.getId(), this.beta.getId(), this.gamma.getId()), evaluation);
	}

	@Test
	void gammaAndAlphaBeta() throws Exception {
		BigDecimal evaluation = this.linkage.evaluate(this.gammaCluster, this.alphaBetaCluster);
		assertEquals(this.mathUtils.oneOver(this.alpha.getId(), this.beta.getId(), this.gamma.getId()), evaluation);
	}

	@Test
	void alphaAndBetaGamma() throws Exception {
		BigDecimal evaluation = this.linkage.evaluate(this.alphaCluster, this.betaGammaCluster);
		assertEquals(this.mathUtils.oneOver(this.alpha.getId(), this.beta.getId(), this.gamma.getId()), evaluation);
	}

	@Test
	void betaGammaAndAlpha() throws Exception {
		BigDecimal evaluation = this.linkage.evaluate(this.betaGammaCluster, this.alphaCluster);
		assertEquals(this.mathUtils.oneOver(this.alpha.getId(), this.beta.getId(), this.gamma.getId()), evaluation);
	}

}
