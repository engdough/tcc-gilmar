package src.br.ufsc.ine.leb.roza;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;

import src.br.ufsc.ine.leb.roza.exceptions.MissingPairException;
import src.br.ufsc.ine.leb.roza.utils.comparator.SimilarityAssessmentComparatorByScoreSourceNameAndTargetName;
import sunit.runner.SunitRunner;

@ExtendWith(SunitRunner.class)
@TestMethodOrder(SunitRunner.class)
class SimilarityReportTest {

	private static SimilarityAssessment assessmentAA;
	private static SimilarityAssessment assessmentAB;
	private static SimilarityAssessment assessmentAC;
	private static SimilarityAssessment assessmentBA;
	private static SimilarityAssessment assessmentBB;
	private static SimilarityAssessment assessmentBC;
	private static SimilarityAssessment assessmentCA;
	private static SimilarityAssessment assessmentCB;
	private static SimilarityAssessment assessmentCC;
	private static TestCase testCaseA;
	private static TestCase testCaseB;
	private static TestCase testCaseC;

	@BeforeEach
	void setup() {
		this.testCaseA = new TestCase("testA", Arrays.asList(), Arrays.asList());
		this.testCaseB = new TestCase("testB", Arrays.asList(), Arrays.asList());
		this.testCaseC = new TestCase("testC", Arrays.asList(), Arrays.asList());
		 this.assessmentAA = new SimilarityAssessment(this.testCaseA, this.testCaseA, BigDecimal.ONE);
		this.assessmentAB = new SimilarityAssessment(this.testCaseA, this.testCaseB, new BigDecimal("0.2"));
		assessmentAC = new SimilarityAssessment(this.testCaseA, this.testCaseC, new BigDecimal("0.9"));
		this.assessmentBA = new SimilarityAssessment(this.testCaseB, this.testCaseA, new BigDecimal("0.3"));
		this.assessmentBB = new SimilarityAssessment(this.testCaseB, this.testCaseB, BigDecimal.ONE);
		this.assessmentBC = new SimilarityAssessment(this.testCaseB, this.testCaseC, new BigDecimal("0.5"));
		this.assessmentCA = new SimilarityAssessment(this.testCaseC, this.testCaseA, new BigDecimal("0.8"));
		this.assessmentCB = new SimilarityAssessment(this.testCaseC, this.testCaseB, new BigDecimal("0.5"));
		this.assessmentCC = new SimilarityAssessment(this.testCaseC, this.testCaseC, BigDecimal.ONE);
	}

	@Test
	void create() throws Exception {
		SimilarityReport report = new SimilarityReport(Arrays.asList( this.assessmentAA, this.assessmentAB));
		report.sort(new SimilarityAssessmentComparatorByScoreSourceNameAndTargetName());
		assertEquals(2, report.getAssessments().size());
		assertEquals( this.assessmentAA, report.getAssessments().get(0));
		assertEquals(this.assessmentAB, report.getAssessments().get(1));
	}

	@Test
	void removeReflexives() throws Exception {
		SimilarityReport report = new SimilarityReport(Arrays.asList( this.assessmentAA, this.assessmentAB));
		SimilarityReport filtered = report.removeReflexives();
		assertEquals(2, report.getAssessments().size());
		assertEquals(1, filtered.getAssessments().size());
		assertEquals(this.assessmentAB, filtered.getAssessments().get(0));
	}

	@Test
	void removeNonReflexives() throws Exception {
		SimilarityReport report = new SimilarityReport(Arrays.asList( this.assessmentAA, this.assessmentAB));
		SimilarityReport filtered = report.removeNonReflexives();
		assertEquals(2, report.getAssessments().size());
		assertEquals(1, filtered.getAssessments().size());
		assertEquals( this.assessmentAA, filtered.getAssessments().get(0));
	}

	@Test
	void selectSource() throws Exception {
		SimilarityReport report = new SimilarityReport(Arrays.asList(this.assessmentBB,  this.assessmentAA, this.assessmentAB, assessmentAC, this.assessmentBA, this.assessmentBC, this.assessmentCA, this.assessmentCB, this.assessmentCC));
		SimilarityReport filtered = report.selectSource(this.testCaseA);
		assertEquals(9, report.getAssessments().size());
		assertEquals(3, filtered.getAssessments().size());
		assertEquals( this.assessmentAA, filtered.getAssessments().get(0));
		assertEquals(this.assessmentAB, filtered.getAssessments().get(1));
		assertEquals(assessmentAC, filtered.getAssessments().get(2));
	}

	@Test
	void unsorted() throws Exception {
		SimilarityReport unsorted = new SimilarityReport(Arrays.asList(this.assessmentBB,  this.assessmentAA, this.assessmentAB, assessmentAC, this.assessmentBA, this.assessmentBC, this.assessmentCA, this.assessmentCB, this.assessmentCC));
		assertEquals(9, unsorted.getAssessments().size());
		assertEquals(this.assessmentBB, unsorted.getAssessments().get(0));
		assertEquals( this.assessmentAA, unsorted.getAssessments().get(1));
		assertEquals(this.assessmentAB, unsorted.getAssessments().get(2));
		assertEquals(assessmentAC, unsorted.getAssessments().get(3));
		assertEquals(this.assessmentBA, unsorted.getAssessments().get(4));
		assertEquals(this.assessmentBC, unsorted.getAssessments().get(5));
		assertEquals(this.assessmentCA, unsorted.getAssessments().get(6));
		assertEquals(this.assessmentCB, unsorted.getAssessments().get(7));
		assertEquals(this.assessmentCC, unsorted.getAssessments().get(8));
	}

	@Test
	void sorted() throws Exception {
		SimilarityReport unsorted = new SimilarityReport(Arrays.asList(this.assessmentBB,  this.assessmentAA, this.assessmentAB, assessmentAC, this.assessmentBA, this.assessmentBC, this.assessmentCA, this.assessmentCB, this.assessmentCC));
		SimilarityReport sorted = unsorted.sort(new SimilarityAssessmentComparatorByScoreSourceNameAndTargetName());
		assertEquals(9, sorted.getAssessments().size());
		assertEquals( this.assessmentAA, sorted.getAssessments().get(0));
		assertEquals(this.assessmentBB, sorted.getAssessments().get(1));
		assertEquals(this.assessmentCC, sorted.getAssessments().get(2));
		assertEquals(assessmentAC, sorted.getAssessments().get(3));
		assertEquals(this.assessmentCA, sorted.getAssessments().get(4));
		assertEquals(this.assessmentBC, sorted.getAssessments().get(5));
		assertEquals(this.assessmentCB, sorted.getAssessments().get(6));
		assertEquals(this.assessmentBA, sorted.getAssessments().get(7));
		assertEquals(this.assessmentAB, sorted.getAssessments().get(8));
	}

	@Test
	void existingPair() throws Exception {
		SimilarityReport report = new SimilarityReport(Arrays.asList(this.assessmentBB,  this.assessmentAA, this.assessmentAB, this.assessmentBA));
		assertEquals( this.assessmentAA, report.getPair(this.testCaseA, this.testCaseA));
		assertEquals(this.assessmentAB, report.getPair(this.testCaseA, this.testCaseB));
		assertEquals(this.assessmentBA, report.getPair(this.testCaseB, this.testCaseA));
		assertEquals(this.assessmentBB, report.getPair(this.testCaseB, this.testCaseB));
	}

	@Test
	void nonExistentPair() throws Exception {
		SimilarityReport report = new SimilarityReport(Arrays.asList(this.assessmentBB,  this.assessmentAA, this.assessmentAB, this.assessmentBA));
		assertThrows(MissingPairException.class, () -> {
			report.getPair(this.testCaseA, this.testCaseC);
		});
		assertThrows(MissingPairException.class, () -> {
			report.getPair(this.testCaseC, this.testCaseA);
		});
		assertThrows(MissingPairException.class, () -> {
			report.getPair(this.testCaseB, this.testCaseC);
		});
		assertThrows(MissingPairException.class, () -> {
			report.getPair(this.testCaseC, this.testCaseB);
		});
	}

}
