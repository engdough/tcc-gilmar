package src.br.ufsc.ine.leb.roza;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;

import src.br.ufsc.ine.leb.roza.exceptions.MissingAssessmentException;
import src.br.ufsc.ine.leb.roza.exceptions.PotentialErrorProneOperationException;
import src.br.ufsc.ine.leb.roza.utils.comparator.SimilarityAssessmentComparatorBySourceAndTargetNames;
import sunit.runner.SunitRunner;

@ExtendWith(SunitRunner.class)
@TestMethodOrder(SunitRunner.class)
public class SimilarityReportBuilderTest {

	private static TestCase testA;
	private static TestCase testB;
	private static BigDecimal dotFive;
	private static BigDecimal dotSix;
	private static SimilarityReportBuilder symmetric;
	private static SimilarityReportBuilder asymmetric;

	@BeforeEach
	void setup() {
		this.testA = new TestCase("testA", Arrays.asList(), Arrays.asList());
		this.testB = new TestCase("testB", Arrays.asList(), Arrays.asList());
		this.dotFive = new BigDecimal("0.5");
		this.dotSix = new BigDecimal("0.6");
		this.symmetric = new SimilarityReportBuilder(true);
		this.asymmetric = new SimilarityReportBuilder(false);
	}

	@Test
	void emptySymmetric() throws Exception {
		SimilarityReport report = this.symmetric.build();
		assertEquals(0, report.getAssessments().size());
	}

	@Test
	void emptyAsymmetric() throws Exception {
		SimilarityReport report = this.asymmetric.build();
		assertEquals(0, report.getAssessments().size());
	}

	@Test
	void addOneTestSymmetric() throws Exception {
		SimilarityReport report = this.symmetric.add(this.testA).build();
		assertEquals(1, report.getAssessments().size());
		assertEquals(this.testA, report.getAssessments().get(0).getSource());
		assertEquals(this.testA, report.getAssessments().get(0).getTarget());
		assertEquals(BigDecimal.ONE, report.getAssessments().get(0).getScore());
	}

	@Test
	void addOneTestAsymmetric() throws Exception {
		SimilarityReport report = this.asymmetric.add(this.testA).build();
		assertEquals(1, report.getAssessments().size());
		assertEquals(this.testA, report.getAssessments().get(0).getSource());
		assertEquals(this.testA, report.getAssessments().get(0).getTarget());
		assertEquals(BigDecimal.ONE, report.getAssessments().get(0).getScore());
	}

	@Test
	void addTwoTestsSymmetric() throws Exception {
		SimilarityReport report = this.symmetric.add(this.testA).add(this.testB).complete().build().sort(new SimilarityAssessmentComparatorBySourceAndTargetNames());
		assertEquals(4, report.getAssessments().size());
		assertEquals(this.testA, report.getAssessments().get(0).getSource());
		assertEquals(this.testA, report.getAssessments().get(0).getTarget());
		assertEquals(BigDecimal.ONE, report.getAssessments().get(0).getScore());
		assertEquals(this.testA, report.getAssessments().get(1).getSource());
		assertEquals(this.testB, report.getAssessments().get(1).getTarget());
		assertEquals(BigDecimal.ZERO, report.getAssessments().get(1).getScore());
		assertEquals(this.testB, report.getAssessments().get(2).getSource());
		assertEquals(this.testA, report.getAssessments().get(2).getTarget());
		assertEquals(BigDecimal.ZERO, report.getAssessments().get(2).getScore());
		assertEquals(this.testB, report.getAssessments().get(3).getSource());
		assertEquals(this.testB, report.getAssessments().get(3).getTarget());
		assertEquals(BigDecimal.ONE, report.getAssessments().get(3).getScore());
	}

	@Test
	void addTwoTestsAsymmetric() throws Exception {
		SimilarityReport report = this.asymmetric.add(this.testA).add(this.testB).complete().build().sort(new SimilarityAssessmentComparatorBySourceAndTargetNames());
		assertEquals(4, report.getAssessments().size());
		assertEquals(this.testA, report.getAssessments().get(0).getSource());
		assertEquals(this.testA, report.getAssessments().get(0).getTarget());
		assertEquals(BigDecimal.ONE, report.getAssessments().get(0).getScore());
		assertEquals(this.testA, report.getAssessments().get(1).getSource());
		assertEquals(this.testB, report.getAssessments().get(1).getTarget());
		assertEquals(BigDecimal.ZERO, report.getAssessments().get(1).getScore());
		assertEquals(this.testB, report.getAssessments().get(2).getSource());
		assertEquals(this.testA, report.getAssessments().get(2).getTarget());
		assertEquals(BigDecimal.ZERO, report.getAssessments().get(2).getScore());
		assertEquals(this.testB, report.getAssessments().get(3).getSource());
		assertEquals(this.testB, report.getAssessments().get(3).getTarget());
		assertEquals(BigDecimal.ONE, report.getAssessments().get(3).getScore());
	}

	@Test
	void addPairOfTwoTestsSymmetric() throws Exception {
		SimilarityReport report = this.symmetric.add(this.testA, this.testB, this.dotFive).build().sort(new SimilarityAssessmentComparatorBySourceAndTargetNames());
		assertEquals(4, report.getAssessments().size());
		assertEquals(this.testA, report.getAssessments().get(0).getSource());
		assertEquals(this.testA, report.getAssessments().get(0).getTarget());
		assertEquals(BigDecimal.ONE, report.getAssessments().get(0).getScore());
		assertEquals(this.testA, report.getAssessments().get(1).getSource());
		assertEquals(this.testB, report.getAssessments().get(1).getTarget());
		assertEquals(this.dotFive, report.getAssessments().get(1).getScore());
		assertEquals(this.testB, report.getAssessments().get(2).getSource());
		assertEquals(this.testA, report.getAssessments().get(2).getTarget());
		assertEquals(this.dotFive, report.getAssessments().get(2).getScore());
		assertEquals(this.testB, report.getAssessments().get(3).getSource());
		assertEquals(this.testB, report.getAssessments().get(3).getTarget());
		assertEquals(BigDecimal.ONE, report.getAssessments().get(3).getScore());
	}

	@Test
	void addPairOfTwoTestsAsymmetric() throws Exception {
		SimilarityReport report = this.asymmetric.add(this.testA, this.testB, this.dotFive).add(testB, this.testA, this.dotSix).build().sort(new SimilarityAssessmentComparatorBySourceAndTargetNames());
		assertEquals(4, report.getAssessments().size());
		assertEquals(this.testA, report.getAssessments().get(0).getSource());
		assertEquals(this.testA, report.getAssessments().get(0).getTarget());
		assertEquals(BigDecimal.ONE, report.getAssessments().get(0).getScore());
		assertEquals(this.testA, report.getAssessments().get(1).getSource());
		assertEquals(this.testB, report.getAssessments().get(1).getTarget());
		assertEquals(this.dotFive, report.getAssessments().get(1).getScore());
		assertEquals(this.testB, report.getAssessments().get(2).getSource());
		assertEquals(this.testA, report.getAssessments().get(2).getTarget());
		assertEquals(this.dotSix, report.getAssessments().get(2).getScore());
		assertEquals(this.testB, report.getAssessments().get(3).getSource());
		assertEquals(this.testB, report.getAssessments().get(3).getTarget());
		assertEquals(BigDecimal.ONE, report.getAssessments().get(3).getScore());
	}

	@Test
	void addTestTwice() throws Exception {
		assertThrows(PotentialErrorProneOperationException.class, () -> {
			this.symmetric.add(this.testA).add(this.testA);
		});
		assertThrows(PotentialErrorProneOperationException.class, () -> {
			this.asymmetric.add(this.testA).add(this.testA);
		});
	}

	@Test
	void addTestWithItself() throws Exception {
		assertThrows(PotentialErrorProneOperationException.class, () -> {
			this.symmetric.add(this.testA, this.testA, this.dotFive);
		});
		assertThrows(PotentialErrorProneOperationException.class, () -> {
			this.asymmetric.add(this.testA, this.testA, this.dotFive);
		});
	}

	@Test
	void addTestAndPairWithItseflAndAnotherTest() throws Exception {
		assertThrows(PotentialErrorProneOperationException.class, () -> {
			this.symmetric.add(this.testA).add(this.testA, this.testB, this.dotFive);
		});
		assertThrows(PotentialErrorProneOperationException.class, () -> {
			this.symmetric.add(this.testA, this.testB, this.dotFive).add(testB);
		});
		assertThrows(PotentialErrorProneOperationException.class, () -> {
			this.asymmetric.add(this.testA).add(this.testA, this.testB, this.dotFive);
		});
		assertThrows(PotentialErrorProneOperationException.class, () -> {
			this.asymmetric.add(this.testA, this.testB, this.dotFive).add(this.testB);
		});
	}

	@Test
	void addSamePairTwice() throws Exception {
		assertThrows(PotentialErrorProneOperationException.class, () -> {
			this.symmetric.add(this.testA, this.testB, this.dotFive).add(this.testA, this.testB, this.dotSix);
		});
		assertThrows(PotentialErrorProneOperationException.class, () -> {
			this.symmetric.add(this.testA, this.testB, this.dotFive).add(this.testB, this.testA, this.dotSix);
		});
		assertThrows(PotentialErrorProneOperationException.class, () -> {
			this.asymmetric.add(this.testA, this.testB, this.dotFive).add(this.testA, this.testB, this.dotSix);
		});
	}

	@Test
	void addTwoTestsSymmetricWithoutComplete() throws Exception {
		assertThrows(MissingAssessmentException.class, () -> {
			this.symmetric.add(this.testA).add(this.testB).build();
		});
	}

	@Test
	void addTwoTestsAsymmetricWithoutComplete() throws Exception {
		assertThrows(MissingAssessmentException.class, () -> {
			this.asymmetric.add(this.testA).add(this.testB).build();
		});
	}

}
