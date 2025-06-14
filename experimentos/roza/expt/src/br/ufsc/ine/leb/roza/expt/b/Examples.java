package src.br.ufsc.ine.leb.roza.expt.b;

import java.util.Comparator;
import java.util.List;

import src.br.ufsc.ine.leb.roza.MaterializationReport;
import src.br.ufsc.ine.leb.roza.SimilarityAssessment;
import src.br.ufsc.ine.leb.roza.SimilarityReport;
import src.br.ufsc.ine.leb.roza.TestCase;
import src.br.ufsc.ine.leb.roza.TestClass;
import src.br.ufsc.ine.leb.roza.TextFile;
import src.br.ufsc.ine.leb.roza.extraction.Junit4TestCaseExtractor;
import src.br.ufsc.ine.leb.roza.extraction.TestCaseExtractor;
import src.br.ufsc.ine.leb.roza.loading.RecursiveTextFileLoader;
import src.br.ufsc.ine.leb.roza.loading.TextFileLoader;
import src.br.ufsc.ine.leb.roza.materialization.Junit4WithoutAssertionsTestCaseMaterializer;
import src.br.ufsc.ine.leb.roza.materialization.TestCaseMaterializer;
import src.br.ufsc.ine.leb.roza.measurement.DeckardSimilarityMeasurer;
import src.br.ufsc.ine.leb.roza.measurement.JplagSimilarityMeasurer;
import src.br.ufsc.ine.leb.roza.measurement.LccssSimilarityMeasurer;
import src.br.ufsc.ine.leb.roza.measurement.LcsSimilarityMeasurer;
import src.br.ufsc.ine.leb.roza.measurement.SimianSimilarityMeasurer;
import src.br.ufsc.ine.leb.roza.measurement.SimilarityMeasurer;
import src.br.ufsc.ine.leb.roza.measurement.configuration.deckard.DeckardConfigurations;
import src.br.ufsc.ine.leb.roza.measurement.configuration.jplag.JplagConfigurations;
import src.br.ufsc.ine.leb.roza.measurement.configuration.simian.SimianConfigurations;
import src.br.ufsc.ine.leb.roza.parsing.Junit4TestClassParser;
import src.br.ufsc.ine.leb.roza.parsing.TestClassParser;
import src.br.ufsc.ine.leb.roza.selection.JavaExtensionTextFileSelector;
import src.br.ufsc.ine.leb.roza.selection.TextFileSelector;
import src.br.ufsc.ine.leb.roza.utils.CommaSeparatedValues;
import src.br.ufsc.ine.leb.roza.utils.FolderUtils;
import src.br.ufsc.ine.leb.roza.utils.FormatterUtils;
import src.br.ufsc.ine.leb.roza.utils.comparator.SimilarityAssessmentComparatorBySourceAndTargetNames;

public class Examples {

	public static void main(String[] args) {
		new FolderUtils("main/exec/materializer").createEmptyFolder();
		FolderUtils folderUtils = new FolderUtils("expt/results/b");
		folderUtils.createEmptyFolder();
		CommaSeparatedValues csv = new CommaSeparatedValues();

		TextFileLoader loader = new RecursiveTextFileLoader("expt/resources/b");
		TextFileSelector selector = new JavaExtensionTextFileSelector();
		TestClassParser parser = new Junit4TestClassParser();
		TestCaseExtractor extractor = new Junit4TestCaseExtractor();
		TestCaseMaterializer materializer = new Junit4WithoutAssertionsTestCaseMaterializer("main/exec/materializer");

		List<TextFile> files = loader.load();
		List<TextFile> selected = selector.select(files);
		List<TestClass> classes = parser.parse(selected);
		List<TestCase> tests = extractor.extract(classes);
		MaterializationReport materializations = materializer.materialize(tests);

		JplagConfigurations jplagSettings = new JplagConfigurations().sensitivity(1);
		SimianConfigurations simianSettings = new SimianConfigurations().threshold(2);
		DeckardConfigurations deckardSettings = new DeckardConfigurations(true).minTokens(1).stride(Integer.MAX_VALUE).similarity(1.0);
		includeMetric(csv, materializations, "LCS", new LcsSimilarityMeasurer());
		includeMetric(csv, materializations, "LCCSS", new LccssSimilarityMeasurer());
		includeMetric(csv, materializations, "JPlag", new JplagSimilarityMeasurer(jplagSettings));
		includeMetric(csv, materializations, "Simian", new SimianSimilarityMeasurer(simianSettings));
		includeMetric(csv, materializations, "Deckard", new DeckardSimilarityMeasurer(deckardSettings));
		folderUtils.writeContetAsString("examples.csv", csv.getContent());
	}

	private static void includeMetric(CommaSeparatedValues csv, MaterializationReport materializations, String metric, SimilarityMeasurer measurer) {
		new FolderUtils("main/exec/measurer").createEmptyFolder();
		FormatterUtils formatterUtils = new FormatterUtils();
		Comparator<SimilarityAssessment> comparator = new SimilarityAssessmentComparatorBySourceAndTargetNames();
		SimilarityReport report = measurer.measure(materializations).sort(comparator).removeReflexives();
		for (SimilarityAssessment assessment : report.getAssessments()) {
			String source = assessment.getSource().getName();
			String target = assessment.getTarget().getName();
			String score = formatterUtils.fractionNumberForCsv(assessment.getScore());
			csv.addLine(metric, source, target, score);
		}
	}

}
