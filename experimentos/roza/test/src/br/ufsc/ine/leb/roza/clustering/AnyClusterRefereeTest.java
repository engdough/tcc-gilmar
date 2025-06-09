package src.br.ufsc.ine.leb.roza.clustering;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;

import src.br.ufsc.ine.leb.roza.Cluster;
import src.br.ufsc.ine.leb.roza.TestCase;
import src.br.ufsc.ine.leb.roza.exceptions.NoCombinationToChooseException;
import src.br.ufsc.ine.leb.roza.utils.CollectionUtils;
import sunit.runner.SunitRunner;

@ExtendWith(SunitRunner.class)
@TestMethodOrder(SunitRunner.class)
class AnyClusterRefereeTest {

	private static Referee referee;
	private static Combination alphaBetaCombinedDelta;
	private static Combination gammaCombinedDelta;
	private static Combination alphaCombinedBeta;
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
		this.alphaCombinedBeta = new Combination(alphaCluster, betaCluster);
		this.collectionUtils = new CollectionUtils();
		this.referee = new AnyClusterReferee();
	}

	@Test
	void withoutElements() throws Exception {
		assertThrows(NoCombinationToChooseException.class, () -> this.referee.untie(this.collectionUtils.set()));
	}

	@Test
	void chooseUnique() throws Exception {
		Combination chosen = this.referee.untie(this.collectionUtils.set(this.alphaCombinedBeta));
		assertEquals(chosen, this.alphaCombinedBeta);
	}

	@Test
	void chooseAnyWithClustersOfSameSize() throws Exception {
		Combination chosen =  this.referee.untie(this.collectionUtils.set(this.alphaCombinedBeta, this.gammaCombinedDelta));
		Assertions.assertTrue(chosen.equals(this.alphaCombinedBeta) || chosen.equals(this.gammaCombinedDelta));
	}

	@Test
	void chooseAnyWithClustersOfDiffrentSize() throws Exception {
		Combination chosen =  this.referee.untie(this.collectionUtils.set(this.alphaBetaCombinedDelta, this.gammaCombinedDelta));
		Assertions.assertTrue(chosen.equals(alphaBetaCombinedDelta) || chosen.equals(gammaCombinedDelta));
	}

}
