package src.br.ufsc.ine.leb.roza.clustering;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;

import src.br.ufsc.ine.leb.roza.TestCase;
import sunit.runner.SunitRunner;

@ExtendWith(SunitRunner.class)
@TestMethodOrder(SunitRunner.class)
class PairTest {

	private static Pair alphaBetaPair;
	private static Pair betaAlphaPair;
	private static TestCase alpha;
	private static TestCase beta;

	@BeforeEach
	void setup() {
		this.alpha = new TestCase("alpha", Arrays.asList(), Arrays.asList());
		this.beta = new TestCase("beta", Arrays.asList(), Arrays.asList());
		this.alphaBetaPair = new Pair(this.alpha, this.beta);
		this.betaAlphaPair = new Pair(this.beta, this.alpha);
	}

	@Test
	void alphaBeta() throws Exception {
		assertEquals(this.alpha, this.alphaBetaPair.getFirst());
		assertEquals(this.beta, this.alphaBetaPair.getSecond());
		assertEquals(this.alphaBetaPair, new Pair(this.alpha, this.beta));
		assertEquals(this.alphaBetaPair.hashCode(), new Pair(this.alpha, this.beta).hashCode());
		assertNotEquals(this.alphaBetaPair, this.betaAlphaPair);
	}

	@Test
	void betaAlpha() throws Exception {
		assertEquals(this.beta, this.betaAlphaPair.getFirst());
		assertEquals(this.alpha, this.betaAlphaPair.getSecond());
		assertEquals(this.betaAlphaPair, new Pair(this.beta, this.alpha));
		assertEquals(this.betaAlphaPair.hashCode(), new Pair(this.beta, this.alpha).hashCode());
		assertNotEquals(this.betaAlphaPair, this.alphaBetaPair);
	}

}
