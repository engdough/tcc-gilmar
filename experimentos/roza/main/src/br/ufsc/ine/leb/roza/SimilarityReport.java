package src.br.ufsc.ine.leb.roza;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import src.br.ufsc.ine.leb.roza.exceptions.MissingPairException;

public class SimilarityReport {

	private List<SimilarityAssessment> assessments;

	SimilarityReport(List<SimilarityAssessment> assessments) {
		this.assessments = new ArrayList<>(assessments);
	}

	public List<SimilarityAssessment> getAssessments() {
		return Collections.unmodifiableList(assessments);
	}

	public SimilarityReport removeReflexives() {
		List<SimilarityAssessment> filtered = new ArrayList<>();
		for (SimilarityAssessment assessment : assessments) {
			if (assessment != null && !assessment.getSource().equals(assessment.getTarget())) {
				filtered.add(assessment);
			}
		}
		return new SimilarityReport(filtered);
	}

	public SimilarityReport removeNonReflexives() {
		List<SimilarityAssessment> filtered = new ArrayList<>();
		for (SimilarityAssessment assessment : assessments) {
			if (assessment != null && assessment.getSource().equals(assessment.getTarget())) {
				filtered.add(assessment);
			}
		}
		return new SimilarityReport(filtered);
	}

	public SimilarityReport selectSource(TestCase source) {
		List<SimilarityAssessment> filtered = new ArrayList<>();
		for (SimilarityAssessment assessment : assessments) {
			if (assessment != null && assessment.getSource().equals(source)) {
				filtered.add(assessment);
			}
		}
		return new SimilarityReport(filtered);
	}

	public SimilarityReport sort(Comparator<SimilarityAssessment> comparator) {
		List<SimilarityAssessment> ordered = new ArrayList<SimilarityAssessment>(assessments);
		Collections.sort(ordered, comparator);
		return new SimilarityReport(ordered);
	}

	public SimilarityAssessment getPair(TestCase source, TestCase target) {
		for (SimilarityAssessment assessment : assessments) {
			if (assessment.getSource().equals(source) && assessment.getTarget().equals(target)) {
				return assessment;
			}
		}
		throw new MissingPairException(source, target);
	}

	@Override
	public String toString() {
		StringBuilder text = new StringBuilder();
		assessments.forEach(assessment -> {
			text.append(assessment.toString());
			text.append(System.lineSeparator());
		});
		return text.toString();
	}

}
