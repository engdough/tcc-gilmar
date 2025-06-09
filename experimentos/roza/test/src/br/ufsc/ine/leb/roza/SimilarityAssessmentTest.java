package src.br.ufsc.ine.leb.roza;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;

import src.br.ufsc.ine.leb.roza.exceptions.InvalidSimilarityScoreException;
import sunit.runner.SunitRunner;

@ExtendWith(SunitRunner.class)
@TestMethodOrder(SunitRunner.class)
class SimilarityAssessmentTest {

	private static TestCase testCaseA;
	private static TestCase testCaseB;

	@BeforeEach
	void setup() {
		this.testCaseA = new TestCase("test1", Arrays.asList(), Arrays.asList());
		this.testCaseB = new TestCase("test2", Arrays.asList(), Arrays.asList());
	}

	@Test
	void minimumScore() throws Exception {
		SimilarityAssessment assessment = new SimilarityAssessment(this.testCaseA, this.testCaseB, BigDecimal.ZERO);
		assertEquals(BigDecimal.ZERO, assessment.getScore());
		assertEquals(this.testCaseA, assessment.getSource());
		assertEquals(this.testCaseB, assessment.getTarget());
	}

	@Test
	void maximumScore() throws Exception {
		SimilarityAssessment assessment = new SimilarityAssessment(this.testCaseA, this.testCaseB, BigDecimal.ONE);
		assertEquals(BigDecimal.ONE, assessment.getScore());
		assertEquals(this.testCaseA, assessment.getSource());
		assertEquals(this.testCaseB, assessment.getTarget());
	}

	@Test
	void scoreLessThanZero() throws Exception {
		assertThrows(InvalidSimilarityScoreException.class, () -> {
			new SimilarityAssessment(this.testCaseA, this.testCaseB, new BigDecimal(-0.5));
		});
	}

	@Test
	void scoreGreaterThanOne() throws Exception {
		assertThrows(InvalidSimilarityScoreException.class, () -> {
			new SimilarityAssessment(this.testCaseA, this.testCaseB, new BigDecimal(1.5));
		});
	}

}
