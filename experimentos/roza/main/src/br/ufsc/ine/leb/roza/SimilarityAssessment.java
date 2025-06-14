package src.br.ufsc.ine.leb.roza;

import java.math.BigDecimal;

import src.br.ufsc.ine.leb.roza.exceptions.InvalidSimilarityScoreException;

public class SimilarityAssessment {

	private TestCase source;
	private TestCase target;
	private BigDecimal score;

	SimilarityAssessment(TestCase source, TestCase target, BigDecimal score) {
		this.source = source;
		this.target = target;
		this.score = score;
		assertSimilariyScoreIsValid();
	}

	private void assertSimilariyScoreIsValid() {
		Boolean isLessThanZero = score.compareTo(BigDecimal.ZERO) < 0;
		Boolean isGreatherThantOne = score.compareTo(BigDecimal.ONE) > 0;
		if (isLessThanZero || isGreatherThantOne) {
			throw new InvalidSimilarityScoreException();
		}
	}

	public BigDecimal getScore() {
		return score;
	}

	public TestCase getSource() {
		return source;
	}

	public TestCase getTarget() {
		return target;
	}

	@Override
	public String toString() {
		return String.format("(%s, %s) : %s", source, target, score);
	}

}
