package src.br.ufsc.ine.leb.roza.measurement.metric;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Arrays;
import java.util.Iterator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;

import src.br.ufsc.ine.leb.roza.MaterializationReport;
import src.br.ufsc.ine.leb.roza.SimilarityAssessment;
import src.br.ufsc.ine.leb.roza.SimilarityReport;
import src.br.ufsc.ine.leb.roza.Statement;
import src.br.ufsc.ine.leb.roza.TestCase;
import src.br.ufsc.ine.leb.roza.materialization.Junit4WithAssertionsTestCaseMaterializer;
import src.br.ufsc.ine.leb.roza.materialization.TestCaseMaterializer;
import src.br.ufsc.ine.leb.roza.measurement.LccssSimilarityMeasurer;
import src.br.ufsc.ine.leb.roza.measurement.SimilarityMeasurer;
import src.br.ufsc.ine.leb.roza.utils.FolderUtils;
import src.br.ufsc.ine.leb.roza.utils.comparator.SimilarityAssessmentComparatorByScoreSourceNameAndTargetName;
import sunit.runner.SunitRunner;

@ExtendWith(SunitRunner.class)
@TestMethodOrder(SunitRunner.class)
class LccssSimilarityMetricTest {

	private static BigDecimal oneOfTwo;
	private static BigDecimal oneOfThree;
	private static BigDecimal twoOfThree;
	private static BigDecimal fourOfFive;
	private static BigDecimal oneOfFour;
	private static BigDecimal fourOfSeven;
	private static BigDecimal twoOfFive;

	private static TestCaseMaterializer materializer;
	private static SimilarityMeasurer measurer;

	@BeforeEach
	void setup() {
		new FolderUtils("main/exec/materializer").createEmptyFolder();
		new FolderUtils("main/exec/measurer").createEmptyFolder();
		this.materializer = new Junit4WithAssertionsTestCaseMaterializer("main/exec/materializer");
		this.measurer = new LccssSimilarityMeasurer();
		this.oneOfTwo = new BigDecimal(1).divide(new BigDecimal(2), MathContext.DECIMAL32);
		this.oneOfThree = new BigDecimal(1).divide(new BigDecimal(3), MathContext.DECIMAL32);
		this.twoOfThree = new BigDecimal(2).divide(new BigDecimal(3), MathContext.DECIMAL32);
		this.fourOfFive = new BigDecimal(4).divide(new BigDecimal(5), MathContext.DECIMAL32);
		this.oneOfFour = new BigDecimal(1).divide(new BigDecimal(4), MathContext.DECIMAL32);
		this.fourOfSeven = new BigDecimal(4).divide(new BigDecimal(7), MathContext.DECIMAL32);
		this.twoOfFive = new BigDecimal(2).divide(new BigDecimal(5), MathContext.DECIMAL32);
	}

	@Test
	void zeroFixtures() throws Exception {
		Statement assertion = new Statement("assertEquals(0, 0);");
		TestCase testCaseA = new TestCase("testA", Arrays.asList(), Arrays.asList(assertion));
		TestCase testCaseB = new TestCase("testB", Arrays.asList(), Arrays.asList(assertion));
		MaterializationReport materializationReport = this.materializer.materialize(Arrays.asList(testCaseA, testCaseB));
		SimilarityReport report = this.measurer.measure(materializationReport).sort(new SimilarityAssessmentComparatorByScoreSourceNameAndTargetName());

		Iterator<SimilarityAssessment> iterator = report.getAssessments().iterator();
		SimilarityAssessment assessmentAA = iterator.next();
		SimilarityAssessment assessmentBB = iterator.next();
		SimilarityAssessment assessmentAB = iterator.next();
		SimilarityAssessment assessmentBA = iterator.next();

		Assertions.assertFalse(iterator.hasNext());
		assertEquals(BigDecimal.ONE, assessmentAA.getScore());
		assertEquals(testCaseA, assessmentAA.getSource());
		assertEquals(testCaseA, assessmentAA.getTarget());
		assertEquals(BigDecimal.ONE, assessmentBB.getScore());
		assertEquals(testCaseB, assessmentBB.getSource());
		assertEquals(testCaseB, assessmentBB.getTarget());
		assertEquals(BigDecimal.ZERO, assessmentAB.getScore());
		assertEquals(testCaseA, assessmentAB.getSource());
		assertEquals(testCaseB, assessmentAB.getTarget());
		assertEquals(BigDecimal.ZERO, assessmentBA.getScore());
		assertEquals(testCaseB, assessmentBA.getSource());
		assertEquals(testCaseA, assessmentBA.getTarget());
	}

	@Test
	void commonStartSequenceSameAssertions() throws Exception {
		Statement fixture1 = new Statement("sut(1);");
		Statement fixture2 = new Statement("sut(2);");
		Statement fixture3 = new Statement("sut(3);");
		Statement assertion = new Statement("assertEquals(0, 0);");
		TestCase testCaseA = new TestCase("testA", Arrays.asList(fixture1, fixture2), Arrays.asList(assertion));
		TestCase testCaseB = new TestCase("testB", Arrays.asList(fixture1, fixture3), Arrays.asList(assertion));
		MaterializationReport materializationReport = this.materializer.materialize(Arrays.asList(testCaseA, testCaseB));
		SimilarityReport report = this.measurer.measure(materializationReport).sort(new SimilarityAssessmentComparatorByScoreSourceNameAndTargetName());

		Iterator<SimilarityAssessment> iterator = report.getAssessments().iterator();
		SimilarityAssessment assessmentAA = iterator.next();
		SimilarityAssessment assessmentBB = iterator.next();
		SimilarityAssessment assessmentAB = iterator.next();
		SimilarityAssessment assessmentBA = iterator.next();

		Assertions.assertFalse(iterator.hasNext());
		assertEquals(BigDecimal.ONE, assessmentAA.getScore());
		assertEquals(testCaseA, assessmentAA.getSource());
		assertEquals(testCaseA, assessmentAA.getTarget());
		assertEquals(BigDecimal.ONE, assessmentBB.getScore());
		assertEquals(testCaseB, assessmentBB.getSource());
		assertEquals(testCaseB, assessmentBB.getTarget());
		assertEquals(this.oneOfTwo, assessmentAB.getScore());
		assertEquals(testCaseA, assessmentAB.getSource());
		assertEquals(testCaseB, assessmentAB.getTarget());
		assertEquals(this.oneOfTwo, assessmentBA.getScore());
		assertEquals(testCaseB, assessmentBA.getSource());
		assertEquals(testCaseA, assessmentBA.getTarget());
	}

	@Test
	void commonStartSequenceDifferentAssertions() throws Exception {
		Statement fixture1 = new Statement("sut(1);");
		Statement fixture2 = new Statement("sut(2);");
		Statement fixture3 = new Statement("sut(3);");
		Statement assertion1 = new Statement("assertEquals(1, 1);");
		Statement assertion2 = new Statement("assertEquals(2, 2);");
		TestCase testCaseA = new TestCase("testA", Arrays.asList(fixture1, fixture2), Arrays.asList(assertion1));
		TestCase testCaseB = new TestCase("testB", Arrays.asList(fixture1, fixture3), Arrays.asList(assertion2));
		MaterializationReport materializationReport = this.materializer.materialize(Arrays.asList(testCaseA, testCaseB));
		SimilarityReport report = this.measurer.measure(materializationReport).sort(new SimilarityAssessmentComparatorByScoreSourceNameAndTargetName());

		Iterator<SimilarityAssessment> iterator = report.getAssessments().iterator();
		SimilarityAssessment assessmentAA = iterator.next();
		SimilarityAssessment assessmentBB = iterator.next();
		SimilarityAssessment assessmentAB = iterator.next();
		SimilarityAssessment assessmentBA = iterator.next();

		Assertions.assertFalse(iterator.hasNext());
		assertEquals(BigDecimal.ONE, assessmentAA.getScore());
		assertEquals(testCaseA, assessmentAA.getSource());
		assertEquals(testCaseA, assessmentAA.getTarget());
		assertEquals(BigDecimal.ONE, assessmentBB.getScore());
		assertEquals(testCaseB, assessmentBB.getSource());
		assertEquals(testCaseB, assessmentBB.getTarget());
		assertEquals(this.oneOfTwo, assessmentAB.getScore());
		assertEquals(testCaseA, assessmentAB.getSource());
		assertEquals(testCaseB, assessmentAB.getTarget());
		assertEquals(this.oneOfTwo, assessmentBA.getScore());
		assertEquals(testCaseB, assessmentBA.getSource());
		assertEquals(testCaseA, assessmentBA.getTarget());
	}

	@Test
	void commonShortContigousStartSequence() throws Exception {
		Statement fixture1 = new Statement("sut(1);");
		Statement fixture2 = new Statement("sut(2);");
		Statement fixture3 = new Statement("sut(3);");
		TestCase testCaseA = new TestCase("testA", Arrays.asList(fixture1, fixture2), Arrays.asList());
		TestCase testCaseB = new TestCase("testB", Arrays.asList(fixture1, fixture3), Arrays.asList());
		MaterializationReport materializationReport = this.materializer.materialize(Arrays.asList(testCaseA, testCaseB));
		SimilarityReport report = this.measurer.measure(materializationReport).sort(new SimilarityAssessmentComparatorByScoreSourceNameAndTargetName());

		Iterator<SimilarityAssessment> iterator = report.getAssessments().iterator();
		SimilarityAssessment assessmentAA = iterator.next();
		SimilarityAssessment assessmentBB = iterator.next();
		SimilarityAssessment assessmentAB = iterator.next();
		SimilarityAssessment assessmentBA = iterator.next();

		Assertions.assertFalse(iterator.hasNext());
		assertEquals(BigDecimal.ONE, assessmentAA.getScore());
		assertEquals(testCaseA, assessmentAA.getSource());
		assertEquals(testCaseA, assessmentAA.getTarget());
		assertEquals(BigDecimal.ONE, assessmentBB.getScore());
		assertEquals(testCaseB, assessmentBB.getSource());
		assertEquals(testCaseB, assessmentBB.getTarget());
		assertEquals(this.oneOfTwo, assessmentAB.getScore());
		assertEquals(testCaseA, assessmentAB.getSource());
		assertEquals(testCaseB, assessmentAB.getTarget());
		assertEquals(this.oneOfTwo, assessmentBA.getScore());
		assertEquals(testCaseB, assessmentBA.getSource());
		assertEquals(testCaseA, assessmentBA.getTarget());
	}

	@Test
	void commonLongContigousStartSequence() throws Exception {
		Statement fixture1 = new Statement("sut(1);");
		Statement fixture2 = new Statement("sut(2);");
		Statement fixture3 = new Statement("sut(3);");
		Statement fixture4 = new Statement("sut(4);");
		Statement fixture5 = new Statement("sut(5);");
		Statement fixture6 = new Statement("sut(6);");
		TestCase testCaseA = new TestCase("testA", Arrays.asList(fixture1, fixture2, fixture3, fixture5), Arrays.asList());
		TestCase testCaseB = new TestCase("testB", Arrays.asList(fixture1, fixture2, fixture4, fixture6), Arrays.asList());
		MaterializationReport materializationReport = this.materializer.materialize(Arrays.asList(testCaseA, testCaseB));
		SimilarityReport report = this.measurer.measure(materializationReport).sort(new SimilarityAssessmentComparatorByScoreSourceNameAndTargetName());

		Iterator<SimilarityAssessment> iterator = report.getAssessments().iterator();
		SimilarityAssessment assessmentAA = iterator.next();
		SimilarityAssessment assessmentBB = iterator.next();
		SimilarityAssessment assessmentAB = iterator.next();
		SimilarityAssessment assessmentBA = iterator.next();

		Assertions.assertFalse(iterator.hasNext());
		assertEquals(BigDecimal.ONE, assessmentAA.getScore());
		assertEquals(testCaseA, assessmentAA.getSource());
		assertEquals(testCaseA, assessmentAA.getTarget());
		assertEquals(BigDecimal.ONE, assessmentBB.getScore());
		assertEquals(testCaseB, assessmentBB.getSource());
		assertEquals(testCaseB, assessmentBB.getTarget());
		assertEquals(this.oneOfTwo, assessmentAB.getScore());
		assertEquals(testCaseA, assessmentAB.getSource());
		assertEquals(testCaseB, assessmentAB.getTarget());
		assertEquals(this.oneOfTwo, assessmentBA.getScore());
		assertEquals(testCaseB, assessmentBA.getSource());
		assertEquals(testCaseA, assessmentBA.getTarget());
	}

	@Test
	void commonNonContigousSequenceAfterStart() throws Exception {
		Statement fixture1 = new Statement("sut(1);");
		Statement fixture2 = new Statement("sut(2);");
		Statement fixture3 = new Statement("sut(3);");
		Statement fixture4 = new Statement("sut(4);");
		TestCase testCaseA = new TestCase("testA", Arrays.asList(fixture1, fixture2, fixture4), Arrays.asList());
		TestCase testCaseB = new TestCase("testB", Arrays.asList(fixture1, fixture3, fixture4), Arrays.asList());
		MaterializationReport materializationReport = this.materializer.materialize(Arrays.asList(testCaseA, testCaseB));
		SimilarityReport report = this.measurer.measure(materializationReport).sort(new SimilarityAssessmentComparatorByScoreSourceNameAndTargetName());

		Iterator<SimilarityAssessment> iterator = report.getAssessments().iterator();
		SimilarityAssessment assessmentAA = iterator.next();
		SimilarityAssessment assessmentBB = iterator.next();
		SimilarityAssessment assessmentAB = iterator.next();
		SimilarityAssessment assessmentBA = iterator.next();

		Assertions.assertFalse(iterator.hasNext());
		assertEquals(BigDecimal.ONE, assessmentAA.getScore());
		assertEquals(testCaseA, assessmentAA.getSource());
		assertEquals(testCaseA, assessmentAA.getTarget());
		assertEquals(BigDecimal.ONE, assessmentBB.getScore());
		assertEquals(testCaseB, assessmentBB.getSource());
		assertEquals(testCaseB, assessmentBB.getTarget());
		assertEquals(this.oneOfThree, assessmentAB.getScore());
		assertEquals(testCaseA, assessmentAB.getSource());
		assertEquals(testCaseB, assessmentAB.getTarget());
		assertEquals(this.oneOfThree, assessmentBA.getScore());
		assertEquals(testCaseB, assessmentBA.getSource());
		assertEquals(testCaseA, assessmentBA.getTarget());
	}

	@Test
	void commonNonContigousSequenceSinceStart() throws Exception {
		Statement fixture1 = new Statement("sut(1);");
		Statement fixture2 = new Statement("sut(2);");
		Statement fixture3 = new Statement("sut(3);");
		Statement fixture4 = new Statement("sut(4);");
		Statement fixture5 = new Statement("sut(5);");
		Statement fixture6 = new Statement("sut(6);");
		TestCase testCaseA = new TestCase("testA", Arrays.asList(fixture5, fixture1, fixture2, fixture4), Arrays.asList());
		TestCase testCaseB = new TestCase("testB", Arrays.asList(fixture6, fixture1, fixture3, fixture4), Arrays.asList());
		MaterializationReport materializationReport = this.materializer.materialize(Arrays.asList(testCaseA, testCaseB));
		SimilarityReport report = this.measurer.measure(materializationReport).sort(new SimilarityAssessmentComparatorByScoreSourceNameAndTargetName());

		Iterator<SimilarityAssessment> iterator = report.getAssessments().iterator();
		SimilarityAssessment assessmentAA = iterator.next();
		SimilarityAssessment assessmentBB = iterator.next();
		SimilarityAssessment assessmentAB = iterator.next();
		SimilarityAssessment assessmentBA = iterator.next();

		Assertions.assertFalse(iterator.hasNext());
		assertEquals(BigDecimal.ONE, assessmentAA.getScore());
		assertEquals(testCaseA, assessmentAA.getSource());
		assertEquals(testCaseA, assessmentAA.getTarget());
		assertEquals(BigDecimal.ONE, assessmentBB.getScore());
		assertEquals(testCaseB, assessmentBB.getSource());
		assertEquals(testCaseB, assessmentBB.getTarget());
		assertEquals(BigDecimal.ZERO, assessmentAB.getScore());
		assertEquals(testCaseA, assessmentAB.getSource());
		assertEquals(testCaseB, assessmentAB.getTarget());
		assertEquals(BigDecimal.ZERO, assessmentBA.getScore());
		assertEquals(testCaseB, assessmentBA.getSource());
		assertEquals(testCaseA, assessmentBA.getTarget());
	}

	@Test
	void commonShortAsymmetricStartSequence() throws Exception {
		Statement fixture1 = new Statement("sut(1);");
		Statement fixture2 = new Statement("sut(2);");
		TestCase testCaseA = new TestCase("testA", Arrays.asList(fixture1), Arrays.asList());
		TestCase testCaseB = new TestCase("testB", Arrays.asList(fixture1, fixture2), Arrays.asList());
		MaterializationReport materializationReport = this.materializer.materialize(Arrays.asList(testCaseA, testCaseB));
		SimilarityReport report = this.measurer.measure(materializationReport).sort(new SimilarityAssessmentComparatorByScoreSourceNameAndTargetName());

		Iterator<SimilarityAssessment> iterator = report.getAssessments().iterator();
		SimilarityAssessment assessmentAA = iterator.next();
		SimilarityAssessment assessmentBB = iterator.next();
		SimilarityAssessment assessmentAB = iterator.next();
		SimilarityAssessment assessmentBA = iterator.next();

		Assertions.assertFalse(iterator.hasNext());
		assertEquals(BigDecimal.ONE, assessmentAA.getScore());
		assertEquals(testCaseA, assessmentAA.getSource());
		assertEquals(testCaseA, assessmentAA.getTarget());
		assertEquals(BigDecimal.ONE, assessmentBB.getScore());
		assertEquals(testCaseB, assessmentBB.getSource());
		assertEquals(testCaseB, assessmentBB.getTarget());
		assertEquals(this.twoOfThree, assessmentAB.getScore());
		assertEquals(testCaseA, assessmentAB.getSource());
		assertEquals(testCaseB, assessmentAB.getTarget());
		assertEquals(this.twoOfThree, assessmentBA.getScore());
		assertEquals(testCaseB, assessmentBA.getSource());
		assertEquals(testCaseA, assessmentBA.getTarget());
	}

	@Test
	void commonLongAsymmetricStartSequence() throws Exception {
		Statement fixture1 = new Statement("sut(1);");
		Statement fixture2 = new Statement("sut(2);");
		Statement fixture3 = new Statement("sut(3);");
		TestCase testCaseA = new TestCase("testA", Arrays.asList(fixture1), Arrays.asList());
		TestCase testCaseB = new TestCase("testB", Arrays.asList(fixture1, fixture2, fixture3), Arrays.asList());
		MaterializationReport materializationReport = this.materializer.materialize(Arrays.asList(testCaseA, testCaseB));
		SimilarityReport report = this.measurer.measure(materializationReport).sort(new SimilarityAssessmentComparatorByScoreSourceNameAndTargetName());

		Iterator<SimilarityAssessment> iterator = report.getAssessments().iterator();
		SimilarityAssessment assessmentAA = iterator.next();
		SimilarityAssessment assessmentBB = iterator.next();
		SimilarityAssessment assessmentAB = iterator.next();
		SimilarityAssessment assessmentBA = iterator.next();

		Assertions.assertFalse(iterator.hasNext());
		assertEquals(BigDecimal.ONE, assessmentAA.getScore());
		assertEquals(testCaseA, assessmentAA.getSource());
		assertEquals(testCaseA, assessmentAA.getTarget());
		assertEquals(BigDecimal.ONE, assessmentBB.getScore());
		assertEquals(testCaseB, assessmentBB.getSource());
		assertEquals(testCaseB, assessmentBB.getTarget());
		assertEquals(this.oneOfTwo, assessmentAB.getScore());
		assertEquals(testCaseA, assessmentAB.getSource());
		assertEquals(testCaseB, assessmentAB.getTarget());
		assertEquals(this.oneOfTwo, assessmentBA.getScore());
		assertEquals(testCaseB, assessmentBA.getSource());
		assertEquals(testCaseA, assessmentBA.getTarget());
	}

	@Test
	void commonAsymmetricStartSequenceWithoutRemainings() throws Exception {
		Statement fixture1 = new Statement("sut(1);");
		Statement fixture2 = new Statement("sut(2);");
		Statement fixture3 = new Statement("sut(3);");
		TestCase testCaseA = new TestCase("testA", Arrays.asList(fixture1), Arrays.asList());
		TestCase testCaseB = new TestCase("testB", Arrays.asList(fixture1, fixture2), Arrays.asList());
		TestCase testCaseC = new TestCase("testC", Arrays.asList(fixture1, fixture2, fixture3), Arrays.asList());
		MaterializationReport materializationReport = this.materializer.materialize(Arrays.asList(testCaseA, testCaseB, testCaseC));
		SimilarityReport report = this.measurer.measure(materializationReport).sort(new SimilarityAssessmentComparatorByScoreSourceNameAndTargetName());

		Iterator<SimilarityAssessment> iterator = report.getAssessments().iterator();
		SimilarityAssessment assessmentAA = iterator.next();
		SimilarityAssessment assessmentBB = iterator.next();
		SimilarityAssessment assessmentCC = iterator.next();
		SimilarityAssessment assessmentBC = iterator.next();
		SimilarityAssessment assessmentCB = iterator.next();
		SimilarityAssessment assessmentAB = iterator.next();
		SimilarityAssessment assessmentBA = iterator.next();
		SimilarityAssessment assessmentAC = iterator.next();
		SimilarityAssessment assessmentCA = iterator.next();

		Assertions.assertFalse(iterator.hasNext());
		assertEquals(BigDecimal.ONE, assessmentAA.getScore());
		assertEquals(testCaseA, assessmentAA.getSource());
		assertEquals(testCaseA, assessmentAA.getTarget());
		assertEquals(BigDecimal.ONE, assessmentBB.getScore());
		assertEquals(testCaseB, assessmentBB.getSource());
		assertEquals(testCaseB, assessmentBB.getTarget());
		assertEquals(BigDecimal.ONE, assessmentBB.getScore());
		assertEquals(testCaseC, assessmentCC.getSource());
		assertEquals(testCaseC, assessmentCC.getTarget());
		assertEquals(this.fourOfFive, assessmentBC.getScore());
		assertEquals(testCaseB, assessmentBC.getSource());
		assertEquals(testCaseC, assessmentBC.getTarget());
		assertEquals(this.fourOfFive, assessmentCB.getScore());
		assertEquals(testCaseC, assessmentCB.getSource());
		assertEquals(testCaseB, assessmentCB.getTarget());
		assertEquals(this.twoOfThree, assessmentAB.getScore());
		assertEquals(testCaseA, assessmentAB.getSource());
		assertEquals(testCaseB, assessmentAB.getTarget());
		assertEquals(this.twoOfThree, assessmentBA.getScore());
		assertEquals(testCaseB, assessmentBA.getSource());
		assertEquals(testCaseA, assessmentBA.getTarget());
		assertEquals(this.oneOfTwo, assessmentAC.getScore());
		assertEquals(testCaseA, assessmentAC.getSource());
		assertEquals(testCaseC, assessmentAC.getTarget());
		assertEquals(this.oneOfTwo, assessmentCA.getScore());
		assertEquals(testCaseC, assessmentCA.getSource());
		assertEquals(testCaseA, assessmentCA.getTarget());
	}

	@Test
	void commonAsymmetricStartSequenceWithFixedRemainings() throws Exception {
		Statement fixture1 = new Statement("sut(1);");
		Statement fixture2 = new Statement("sut(2);");
		Statement fixture3 = new Statement("sut(3);");
		Statement fixture4 = new Statement("sut(4);");
		TestCase testCaseA = new TestCase("testA", Arrays.asList(fixture1, fixture4), Arrays.asList());
		TestCase testCaseB = new TestCase("testB", Arrays.asList(fixture1, fixture2, fixture4), Arrays.asList());
		TestCase testCaseC = new TestCase("testC", Arrays.asList(fixture1, fixture2, fixture3, fixture4), Arrays.asList());
		MaterializationReport materializationReport = this.materializer.materialize(Arrays.asList(testCaseA, testCaseB, testCaseC));
		SimilarityReport report = this.measurer.measure(materializationReport).sort(new SimilarityAssessmentComparatorByScoreSourceNameAndTargetName());

		Iterator<SimilarityAssessment> iterator = report.getAssessments().iterator();
		SimilarityAssessment assessmentAA = iterator.next();
		SimilarityAssessment assessmentBB = iterator.next();
		SimilarityAssessment assessmentCC = iterator.next();
		SimilarityAssessment assessmentBC = iterator.next();
		SimilarityAssessment assessmentCB = iterator.next();
		SimilarityAssessment assessmentAB = iterator.next();
		SimilarityAssessment assessmentBA = iterator.next();
		SimilarityAssessment assessmentAC = iterator.next();
		SimilarityAssessment assessmentCA = iterator.next();

		Assertions.assertFalse(iterator.hasNext());
		assertEquals(BigDecimal.ONE, assessmentAA.getScore());
		assertEquals(testCaseA, assessmentAA.getSource());
		assertEquals(testCaseA, assessmentAA.getTarget());
		assertEquals(BigDecimal.ONE, assessmentBB.getScore());
		assertEquals(testCaseB, assessmentBB.getSource());
		assertEquals(testCaseB, assessmentBB.getTarget());
		assertEquals(BigDecimal.ONE, assessmentBB.getScore());
		assertEquals(testCaseC, assessmentCC.getSource());
		assertEquals(testCaseC, assessmentCC.getTarget());
		assertEquals(this.fourOfSeven, assessmentBC.getScore());
		assertEquals(testCaseB, assessmentBC.getSource());
		assertEquals(testCaseC, assessmentBC.getTarget());
		assertEquals(this.fourOfSeven, assessmentCB.getScore());
		assertEquals(testCaseC, assessmentCB.getSource());
		assertEquals(testCaseB, assessmentCB.getTarget());
		assertEquals(this.twoOfFive, assessmentAB.getScore());
		assertEquals(testCaseA, assessmentAB.getSource());
		assertEquals(testCaseB, assessmentAB.getTarget());
		assertEquals(this.twoOfFive, assessmentBA.getScore());
		assertEquals(testCaseB, assessmentBA.getSource());
		assertEquals(testCaseA, assessmentBA.getTarget());
		assertEquals(this.oneOfThree, assessmentAC.getScore());
		assertEquals(testCaseA, assessmentAC.getSource());
		assertEquals(testCaseC, assessmentAC.getTarget());
		assertEquals(this.oneOfThree, assessmentCA.getScore());
		assertEquals(testCaseC, assessmentCA.getSource());
		assertEquals(testCaseA, assessmentCA.getTarget());
	}

	@Test
	void commonAsymmetricStartSequenceWithProportionalRemainings() throws Exception {
		Statement fixture1 = new Statement("sut(1);");
		Statement fixture2 = new Statement("sut(2);");
		Statement fixture3 = new Statement("sut(3);");
		Statement fixture4 = new Statement("sut(4);");
		Statement fixture5 = new Statement("sut(5);");
		Statement fixture6 = new Statement("sut(6);");
		TestCase testCaseA = new TestCase("testA", Arrays.asList(fixture1, fixture4), Arrays.asList());
		TestCase testCaseB = new TestCase("testB", Arrays.asList(fixture1, fixture2, fixture4, fixture5), Arrays.asList());
		TestCase testCaseC = new TestCase("testC", Arrays.asList(fixture1, fixture2, fixture3, fixture4, fixture5, fixture6), Arrays.asList());
		MaterializationReport materializationReport = this.materializer.materialize(Arrays.asList(testCaseA, testCaseB, testCaseC));
		SimilarityReport report = this.measurer.measure(materializationReport).sort(new SimilarityAssessmentComparatorByScoreSourceNameAndTargetName());

		Iterator<SimilarityAssessment> iterator = report.getAssessments().iterator();
		SimilarityAssessment assessmentAA = iterator.next();
		SimilarityAssessment assessmentBB = iterator.next();
		SimilarityAssessment assessmentCC = iterator.next();
		SimilarityAssessment assessmentBC = iterator.next();
		SimilarityAssessment assessmentCB = iterator.next();
		SimilarityAssessment assessmentAB = iterator.next();
		SimilarityAssessment assessmentBA = iterator.next();
		SimilarityAssessment assessmentAC = iterator.next();
		SimilarityAssessment assessmentCA = iterator.next();

		Assertions.assertFalse(iterator.hasNext());
		assertEquals(BigDecimal.ONE, assessmentAA.getScore());
		assertEquals(testCaseA, assessmentAA.getSource());
		assertEquals(testCaseA, assessmentAA.getTarget());
		assertEquals(BigDecimal.ONE, assessmentBB.getScore());
		assertEquals(testCaseB, assessmentBB.getSource());
		assertEquals(testCaseB, assessmentBB.getTarget());
		assertEquals(BigDecimal.ONE, assessmentBB.getScore());
		assertEquals(testCaseC, assessmentCC.getSource());
		assertEquals(testCaseC, assessmentCC.getTarget());
		assertEquals(this.twoOfFive, assessmentBC.getScore());
		assertEquals(testCaseB, assessmentBC.getSource());
		assertEquals(testCaseC, assessmentBC.getTarget());
		assertEquals(this.twoOfFive, assessmentCB.getScore());
		assertEquals(testCaseC, assessmentCB.getSource());
		assertEquals(testCaseB, assessmentCB.getTarget());
		assertEquals(this.oneOfThree, assessmentAB.getScore());
		assertEquals(testCaseA, assessmentAB.getSource());
		assertEquals(testCaseB, assessmentAB.getTarget());
		assertEquals(this.oneOfThree, assessmentBA.getScore());
		assertEquals(testCaseB, assessmentBA.getSource());
		assertEquals(testCaseA, assessmentBA.getTarget());
		assertEquals(this.oneOfFour, assessmentAC.getScore());
		assertEquals(testCaseA, assessmentAC.getSource());
		assertEquals(testCaseC, assessmentAC.getTarget());
		assertEquals(this.oneOfFour, assessmentCA.getScore());
		assertEquals(testCaseC, assessmentCA.getSource());
		assertEquals(testCaseA, assessmentCA.getTarget());
	}

	@Test
	void commonAsymmetricStartSequenceWithInverselyProportionalRemainings() throws Exception {
		Statement fixture1 = new Statement("sut(1);");
		Statement fixture2 = new Statement("sut(2);");
		Statement fixture3 = new Statement("sut(3);");
		Statement fixture4 = new Statement("sut(4);");
		Statement fixture5 = new Statement("sut(5);");
		Statement fixture6 = new Statement("sut(6);");
		TestCase testCaseA = new TestCase("testA", Arrays.asList(fixture1, fixture4, fixture5, fixture6), Arrays.asList());
		TestCase testCaseB = new TestCase("testB", Arrays.asList(fixture1, fixture2, fixture4, fixture5), Arrays.asList());
		TestCase testCaseC = new TestCase("testC", Arrays.asList(fixture1, fixture2, fixture3, fixture4), Arrays.asList());
		MaterializationReport materializationReport = this.materializer.materialize(Arrays.asList(testCaseA, testCaseB, testCaseC));
		SimilarityReport report = this.measurer.measure(materializationReport).sort(new SimilarityAssessmentComparatorByScoreSourceNameAndTargetName());

		Iterator<SimilarityAssessment> iterator = report.getAssessments().iterator();
		SimilarityAssessment assessmentAA = iterator.next();
		SimilarityAssessment assessmentBB = iterator.next();
		SimilarityAssessment assessmentCC = iterator.next();
		SimilarityAssessment assessmentBC = iterator.next();
		SimilarityAssessment assessmentCB = iterator.next();
		SimilarityAssessment assessmentAB = iterator.next();
		SimilarityAssessment assessmentAC = iterator.next();
		SimilarityAssessment assessmentBA = iterator.next();
		SimilarityAssessment assessmentCA = iterator.next();

		Assertions.assertFalse(iterator.hasNext());
		assertEquals(BigDecimal.ONE, assessmentAA.getScore());
		assertEquals(testCaseA, assessmentAA.getSource());
		assertEquals(testCaseA, assessmentAA.getTarget());
		assertEquals(BigDecimal.ONE, assessmentBB.getScore());
		assertEquals(testCaseB, assessmentBB.getSource());
		assertEquals(testCaseB, assessmentBB.getTarget());
		assertEquals(BigDecimal.ONE, assessmentBB.getScore());
		assertEquals(testCaseC, assessmentCC.getSource());
		assertEquals(testCaseC, assessmentCC.getTarget());
		assertEquals(this.oneOfTwo, assessmentBC.getScore());
		assertEquals(testCaseB, assessmentBC.getSource());
		assertEquals(testCaseC, assessmentBC.getTarget());
		assertEquals(this.oneOfTwo, assessmentCB.getScore());
		assertEquals(testCaseC, assessmentCB.getSource());
		assertEquals(testCaseB, assessmentCB.getTarget());
		assertEquals(this.oneOfFour, assessmentAB.getScore());
		assertEquals(testCaseA, assessmentAB.getSource());
		assertEquals(testCaseB, assessmentAB.getTarget());
		assertEquals(this.oneOfFour, assessmentBA.getScore());
		assertEquals(testCaseA, assessmentAC.getSource());
		assertEquals(testCaseC, assessmentAC.getTarget());
		assertEquals(this.oneOfFour, assessmentAC.getScore());
		assertEquals(testCaseB, assessmentBA.getSource());
		assertEquals(testCaseA, assessmentBA.getTarget());
		assertEquals(this.oneOfFour, assessmentCA.getScore());
		assertEquals(testCaseC, assessmentCA.getSource());
		assertEquals(testCaseA, assessmentCA.getTarget());
	}

}
