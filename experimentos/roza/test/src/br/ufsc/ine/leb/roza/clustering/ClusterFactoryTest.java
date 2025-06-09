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
import src.br.ufsc.ine.leb.roza.SimilarityReport;
import src.br.ufsc.ine.leb.roza.SimilarityReportBuilder;
import src.br.ufsc.ine.leb.roza.TestCase;
import sunit.runner.SunitRunner;

@ExtendWith(SunitRunner.class)
@TestMethodOrder(SunitRunner.class)
class ClusterFactoryTest {

	private static SimilarityReport report;
	private static ClusterFactory factory;
	private static Cluster alphaCluster;
	private static Cluster betaCluster;

	@BeforeEach
	void setup() {
		TestCase alpha = new TestCase("alpha", Arrays.asList(), Arrays.asList());
		TestCase beta = new TestCase("beta", Arrays.asList(), Arrays.asList());
		this.report = new SimilarityReportBuilder(true).add(alpha).add(beta).complete().build();
		this.alphaCluster = new Cluster(alpha);
		this.betaCluster = new Cluster(beta);
		this.factory = new ClusterFactory();
	}

	@Test
	void create() throws Exception {
		Set<Cluster> clusters = this.factory.create(this.report);
		assertEquals(2, clusters.size());
		assertTrue(clusters.contains(this.alphaCluster));
		assertTrue(clusters.contains(this.betaCluster));
	}

}
