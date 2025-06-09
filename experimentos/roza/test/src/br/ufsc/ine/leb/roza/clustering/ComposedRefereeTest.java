package src.br.ufsc.ine.leb.roza.clustering;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;

import src.br.ufsc.ine.leb.roza.Cluster;
import src.br.ufsc.ine.leb.roza.TestCase;
import src.br.ufsc.ine.leb.roza.exceptions.InsufficientRefereeException;
import src.br.ufsc.ine.leb.roza.exceptions.NoCombinationToChooseException;
import src.br.ufsc.ine.leb.roza.exceptions.TiebreakException;
import src.br.ufsc.ine.leb.roza.utils.CollectionUtils;
import sunit.runner.SunitRunner;

@ExtendWith(SunitRunner.class)
@TestMethodOrder(SunitRunner.class)
public class ComposedRefereeTest {

	private static Combination gammaCombinedDelta;
	private static Combination alphaBetaCombinedDelta;
	private static Combination alphaBetaCombinedGamma;
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
		this.alphaBetaCombinedGamma = new Combination(alphaBetaCluster, gammaCluster);
		this.gammaCombinedDelta = new Combination(gammaCluster, deltaCluster);
		this.collectionUtils = new CollectionUtils();
	}

	@Test
	void withoutReferees() throws Exception {
		Assertions.assertThrows(InsufficientRefereeException.class, () -> new ComposedReferee());
	}

	@Test
	void onlyOneReferee() throws Exception {
		Assertions.assertThrows(InsufficientRefereeException.class, () -> new ComposedReferee(new AnyClusterReferee()));
	}

	@Test
	void withoutElements() throws Exception {
		Referee referee = new ComposedReferee(new SmallestClusterReferee(), new BiggestClusterReferee());
		Assertions.assertThrows(NoCombinationToChooseException.class, () -> referee.untie(collectionUtils.set()));
	}

	@Test
	void stopAtFirst() throws Exception {
		Referee referee = new ComposedReferee(new SmallestClusterReferee(), new InsecureReferee());
		Combination chosen = referee.untie(collectionUtils.set(this.alphaBetaCombinedDelta, this.gammaCombinedDelta));
		assertEquals(chosen, this.gammaCombinedDelta);
	}

	@Test
	void stopAtLast() throws Exception {
		Referee referee = new ComposedReferee(new InsecureReferee(), new BiggestClusterReferee());
		Combination chosen = referee.untie(collectionUtils.set(this.alphaBetaCombinedDelta, this.gammaCombinedDelta));
		assertEquals(chosen, this.alphaBetaCombinedDelta);
	}

	@Test
	void doesNotStopAtAll() throws Exception {
		Referee referee = new ComposedReferee(new SmallestClusterReferee(), new BiggestClusterReferee());
		Assertions.assertThrows(TiebreakException.class, () -> referee.untie(collectionUtils.set(this.alphaBetaCombinedDelta, this.alphaBetaCombinedGamma)));
	}

}
