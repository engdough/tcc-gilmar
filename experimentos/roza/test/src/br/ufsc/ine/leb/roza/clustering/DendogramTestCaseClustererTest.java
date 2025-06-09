package src.br.ufsc.ine.leb.roza.clustering;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;

import src.br.ufsc.ine.leb.roza.Cluster;
import src.br.ufsc.ine.leb.roza.SimilarityReport;
import src.br.ufsc.ine.leb.roza.SimilarityReportBuilder;
import src.br.ufsc.ine.leb.roza.TestCase;
import src.br.ufsc.ine.leb.roza.exceptions.ClusteringLevelGenerationException;
import src.br.ufsc.ine.leb.roza.exceptions.TiebreakException;
import sunit.runner.SunitRunner;

@ExtendWith(SunitRunner.class)
@TestMethodOrder(SunitRunner.class)
class DendogramTestCaseClustererTest {

	private static TestCase alpha;
	private static TestCase beta;
	private static TestCase gamma;
	private static BigDecimal dotFive;
	private static BigDecimal dotOne;
	private static Cluster clusterAlpha;
	private static Cluster clusterBeta;
	private static Cluster clusterGamma;
	private static Cluster clusterAlphaBeta;
	private static Cluster clusterAlphaBetaGamma;

	@BeforeEach
	void setup() {
		this.dotOne = new BigDecimal("0.1");
		this.dotFive = new BigDecimal("0.5");
		this.alpha = new TestCase("alpha", Arrays.asList(), Arrays.asList());
		this.beta = new TestCase("beta", Arrays.asList(), Arrays.asList());
		this.gamma = new TestCase("gamma", Arrays.asList(), Arrays.asList());
		this.clusterAlpha = new Cluster(this.alpha);
		this.clusterBeta = new Cluster(this.beta);
		this.clusterGamma = new Cluster(this.gamma);
		this.clusterAlphaBeta = this.clusterAlpha.merge(this.clusterBeta);
		this.clusterAlphaBetaGamma = this.clusterAlphaBeta.merge(this.clusterGamma);
	}

	@Test
	void zeroTests() throws Exception {
		SimilarityReport report = new SimilarityReportBuilder(true).build();
		Referee referee = new InsecureReferee();
		Linkage linkage = new SingleLinkage(report);
		ThresholdCriteria criteria = new NeverStopCriteria();
		DendogramTestCaseClusterer clusterer = new DendogramTestCaseClusterer(linkage, referee, criteria);
		List<Level> levels = clusterer.generateLevels(report);
		Set<Cluster> clusters = clusterer.cluster(report);

		assertEquals(1, levels.size());

		Level zero = levels.get(0);
		assertEquals(0, zero.getStep());
		Assertions.assertNull(zero.getEvaluationToThisLevel());
		assertEquals(0, zero.getClusters().size());

		assertEquals(0, clusters.size());
	}

	@Test
	void oneTestStopingInLevelOneBecauseItHasntClustersToMerge() throws Exception {
		SimilarityReport report = new SimilarityReportBuilder(true).add(this.alpha).build();
		Referee referee = new InsecureReferee();
		Linkage linkage = new SingleLinkage(report);
		ThresholdCriteria criteria = new NeverStopCriteria();
		DendogramTestCaseClusterer clusterer = new DendogramTestCaseClusterer(linkage, referee, criteria);
		List<Level> levels = clusterer.generateLevels(report);
		Set<Cluster> clusters = clusterer.cluster(report);

		assertEquals(1, levels.size());

		Level zero = levels.get(0);
		assertEquals(0, zero.getStep());
		Assertions.assertNull(zero.getEvaluationToThisLevel());
		assertEquals(1, zero.getClusters().size());
		assertTrue(zero.getClusters().contains(this.clusterAlpha));

		assertEquals(1, clusters.size());
		assertTrue(clusters.contains(this.clusterAlpha));
	}

	@Test
	void twoTestsStopingInLevelZeroBecauseThresholdCriteria() throws Exception {
		SimilarityReport report = new SimilarityReportBuilder(true).add(this.alpha).add(this.beta).complete().build();
		Referee referee = new InsecureReferee();
		Linkage linkage = new SingleLinkage(report);
		ThresholdCriteria criteria = new AlwaysStopCriteria();
		DendogramTestCaseClusterer clusterer = new DendogramTestCaseClusterer(linkage, referee, criteria);
		List<Level> levels = clusterer.generateLevels(report);
		Set<Cluster> clusters = clusterer.cluster(report);

		assertEquals(1, levels.size());

		Level zero = levels.get(0);
		assertEquals(0, zero.getStep());
		Assertions.assertNull(zero.getEvaluationToThisLevel());
		assertEquals(2, zero.getClusters().size());
		assertTrue(zero.getClusters().contains(this.clusterAlpha));
		assertTrue(zero.getClusters().contains(this.clusterBeta));

		assertEquals(2, clusters.size());
		assertTrue(clusters.contains(this.clusterAlpha));
		assertTrue(clusters.contains(this.clusterBeta));
	}

	@Test
	void twoTestsStopingInLevelOneBecauseItHasntClustersToMerge() throws Exception {
		SimilarityReport report = new SimilarityReportBuilder(true).add(this.alpha).add(this.beta).complete().build();
		Referee referee = new InsecureReferee();
		Linkage linkage = new SingleLinkage(report);
		ThresholdCriteria criteria = new NeverStopCriteria();
		DendogramTestCaseClusterer clusterer = new DendogramTestCaseClusterer(linkage, referee, criteria);
		List<Level> levels = clusterer.generateLevels(report);
		Set<Cluster> clusters = clusterer.cluster(report);

		assertEquals(2, levels.size());

		Level zero = levels.get(0);
		assertEquals(0, zero.getStep());
		Assertions.assertNull(zero.getEvaluationToThisLevel());
		assertEquals(2, zero.getClusters().size());
		assertTrue(zero.getClusters().contains(this.clusterAlpha));
		assertTrue(zero.getClusters().contains(this.clusterBeta));

		Level one = levels.get(1);
		assertEquals(1, one.getStep());
		assertEquals(BigDecimal.ZERO, one.getEvaluationToThisLevel());
		assertEquals(1, one.getClusters().size());
		assertTrue(one.getClusters().contains(this.clusterAlphaBeta));

		assertEquals(1, clusters.size());
		assertTrue(clusters.contains(this.clusterAlphaBeta));
	}

	@Test
	void threeTestsStopingInLevelOneBecauseThresholdCriteria() throws Exception {
		SimilarityReport report = new SimilarityReportBuilder(true).add(this.alpha, this.beta, this.dotFive).add(this.gamma).complete().build();
		Referee referee = new InsecureReferee();
		Linkage linkage = new SingleLinkage(report);
		ThresholdCriteria criteria = new SimilarityBasedCriteria(this.dotOne);
		DendogramTestCaseClusterer clusterer = new DendogramTestCaseClusterer(linkage, referee, criteria);
		List<Level> levels = clusterer.generateLevels(report);
		Set<Cluster> clusters = clusterer.cluster(report);

		assertEquals(2, levels.size());

		Level zero = levels.get(0);
		assertEquals(0, zero.getStep());
		Assertions.assertNull(zero.getEvaluationToThisLevel());
		assertEquals(3, zero.getClusters().size());
		assertTrue(zero.getClusters().contains(this.clusterAlpha));
		assertTrue(zero.getClusters().contains(this.clusterBeta));
		assertTrue(zero.getClusters().contains(this.clusterGamma));

		Level one = levels.get(1);
		assertEquals(1, one.getStep());
		assertEquals(new BigDecimal("0.5"), one.getEvaluationToThisLevel());
		assertEquals(2, one.getClusters().size());
		assertTrue(one.getClusters().contains(this.clusterAlphaBeta));
		assertTrue(one.getClusters().contains(this.clusterGamma));

		assertEquals(2, clusters.size());
		assertTrue(clusters.contains(this.clusterAlphaBeta));
		assertTrue(clusters.contains(this.clusterGamma));
	}

	@Test
	void threeTestsStopingInLevelTwoBecauseItHasntClustersToMerge() throws Exception {
		SimilarityReport report = new SimilarityReportBuilder(true).add(this.alpha, this.beta, this.dotFive).add(this.gamma).complete().build();
		Referee referee = new InsecureReferee();
		Linkage linkage = new SingleLinkage(report);
		ThresholdCriteria criteria = new NeverStopCriteria();
		DendogramTestCaseClusterer clusterer = new DendogramTestCaseClusterer(linkage, referee, criteria);
		List<Level> levels = clusterer.generateLevels(report);
		Set<Cluster> clusters = clusterer.cluster(report);

		assertEquals(3, levels.size());

		Level zero = levels.get(0);
		assertEquals(0, zero.getStep());
		Assertions.assertNull(zero.getEvaluationToThisLevel());
		assertEquals(3, zero.getClusters().size());
		assertTrue(zero.getClusters().contains(this.clusterAlpha));
		assertTrue(zero.getClusters().contains(this.clusterBeta));
		assertTrue(zero.getClusters().contains(this.clusterGamma));

		Level one = levels.get(1);
		assertEquals(1, one.getStep());
		assertEquals(new BigDecimal("0.5"), one.getEvaluationToThisLevel());
		assertEquals(2, one.getClusters().size());
		assertTrue(one.getClusters().contains(this.clusterAlphaBeta));
		assertTrue(one.getClusters().contains(this.clusterGamma));

		Level two = levels.get(2);
		assertEquals(2, two.getStep());
		assertEquals(BigDecimal.ZERO, two.getEvaluationToThisLevel());
		assertEquals(1, two.getClusters().size());
		assertTrue(two.getClusters().contains(this.clusterAlphaBetaGamma));

		assertEquals(1, clusters.size());
		assertTrue(clusters.contains(this.clusterAlphaBetaGamma));
	}

	@Test
	void threDistinctTestsWithTiebreak() throws Exception {
		SimilarityReport report = new SimilarityReportBuilder(true).add(this.alpha).add(this.beta).add(this.gamma).complete().build();
		Referee referee = new InsecureReferee();
		Linkage linkage = new SingleLinkage(report);
		ThresholdCriteria criteria = new NeverStopCriteria();
		DendogramTestCaseClusterer clusterer = new DendogramTestCaseClusterer(linkage, referee, criteria);
		ClusteringLevelGenerationException exception = assertThrows(ClusteringLevelGenerationException.class, () -> clusterer.cluster(report));

		TiebreakException tiebreak = exception.getTibreakException();
		assertEquals(3, tiebreak.getTies().size());
		assertTrue(tiebreak.getTies().contains(new Combination(this.clusterAlpha, this.clusterBeta)));
		assertTrue(tiebreak.getTies().contains(new Combination(this.clusterAlpha, this.clusterGamma)));
		assertTrue(tiebreak.getTies().contains(new Combination(this.clusterBeta, this.clusterGamma)));

		List<Level> levels = exception.getLevels();
		assertEquals(1, levels.size());
		assertEquals(3, levels.get(0).getClusters().size());
		assertTrue(levels.get(0).getClusters().contains(this.clusterAlpha));
		assertTrue(levels.get(0).getClusters().contains(this.clusterBeta));
		assertTrue(levels.get(0).getClusters().contains(this.clusterGamma));
	}

}
