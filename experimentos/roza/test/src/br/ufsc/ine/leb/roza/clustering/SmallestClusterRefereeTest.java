package src.br.ufsc.ine.leb.roza.clustering;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;

import src.br.ufsc.ine.leb.roza.Cluster;
import src.br.ufsc.ine.leb.roza.TestCase;
import src.br.ufsc.ine.leb.roza.exceptions.NoCombinationToChooseException;
import src.br.ufsc.ine.leb.roza.exceptions.TiebreakException;
import src.br.ufsc.ine.leb.roza.utils.CollectionUtils;
import sunit.runner.SunitRunner;

@ExtendWith(SunitRunner.class)
@TestMethodOrder(SunitRunner.class)
class SmallestClusterRefereeTest {

	private static Referee referee;
	private static Combination alphaBetaCombinedDelta;
	private static Combination gammaCombinedDelta;
	private static Combination alphaCombinedBeta;
	private static Combination deltaCombinedGamma;
	private static CollectionUtils collectionUtils;

	@BeforeEach
	void setup() {
		TestCase alpha = new TestCase("alpha", Arrays.asList(), Arrays.asList());
		TestCase beta = new TestCase("beta", Arrays.asList(), Arrays.asList());
		TestCase gamma = new TestCase("gamma", Arrays.asList(), Arrays.asList());
		TestCase delta = new TestCase("delta", Arrays.asList(), Arrays.asList());
		Cluster alphaCluster = new Cluster(alpha);
		Cluster betaCluster = new Cluster(beta);
		Cluster gammaCluster = new Cluster(gamma);
		Cluster deltaCluster = new Cluster(delta);
		Cluster alphaBetaCluster = alphaCluster.merge(betaCluster);
		this.alphaBetaCombinedDelta = new Combination(alphaBetaCluster, deltaCluster);
		this.gammaCombinedDelta = new Combination(gammaCluster, deltaCluster);
		this.deltaCombinedGamma = new Combination(deltaCluster, gammaCluster);
		this.alphaCombinedBeta = new Combination(alphaCluster, betaCluster);
		this.collectionUtils = new CollectionUtils();
		this.referee = new SmallestClusterReferee();
	}

	@Test
	void withoutElements() throws Exception {
		assertThrows(NoCombinationToChooseException.class, () -> this.referee.untie(this.collectionUtils.set()));
	}

	@Test
	void chooseUnique() throws Exception {
		Combination chosen = this.referee.untie(this.collectionUtils.set(this.alphaBetaCombinedDelta));
		assertEquals(chosen, this.alphaBetaCombinedDelta);
	}

	@Test
	void chooseFirst() throws Exception {
		Combination chosen = this.referee.untie(this.collectionUtils.set(this.gammaCombinedDelta, this.alphaBetaCombinedDelta));
		assertEquals(chosen, this.gammaCombinedDelta);
	}

	@Test
	void chooseLast() throws Exception {
		Combination chosen = this.referee.untie(this.collectionUtils.set(this.alphaBetaCombinedDelta, this.gammaCombinedDelta));
		assertEquals(chosen, this.gammaCombinedDelta);
	}

	@Test
	void tiebreak() throws Exception {
		assertThrows(TiebreakException.class, () -> this.referee.untie(this.collectionUtils.set(this.gammaCombinedDelta, this.alphaBetaCombinedDelta, this.alphaCombinedBeta)));
	}

	@Test
	void chooseFirstAndLastEquals() throws Exception {
		Combination chosen = this.referee.untie(this.collectionUtils.set(this.gammaCombinedDelta, this.alphaBetaCombinedDelta, this.deltaCombinedGamma));
		assertEquals(chosen, this.gammaCombinedDelta);
		assertEquals(chosen, this.deltaCombinedGamma);
	}

}
