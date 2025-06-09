package src.br.ufsc.ine.leb.proza.expt.c;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import src.br.ufsc.ine.leb.roza.Cluster;
import src.br.ufsc.ine.leb.roza.MaterializationReport;
import src.br.ufsc.ine.leb.roza.SimilarityReport;
import src.br.ufsc.ine.leb.roza.TestCase;
import src.br.ufsc.ine.leb.roza.TestClass;
import src.br.ufsc.ine.leb.roza.TextFile;
import src.br.ufsc.ine.leb.roza.clustering.AverageLinkage;
import src.br.ufsc.ine.leb.roza.clustering.BiggestClusterReferee;
import src.br.ufsc.ine.leb.roza.clustering.CompleteLinkage;
import src.br.ufsc.ine.leb.roza.clustering.DendogramTestCaseClusterer;
import src.br.ufsc.ine.leb.roza.clustering.InsecureReferee;
import src.br.ufsc.ine.leb.roza.clustering.LevelBasedCriteria;
import src.br.ufsc.ine.leb.roza.clustering.Linkage;
import src.br.ufsc.ine.leb.roza.clustering.Referee;
import src.br.ufsc.ine.leb.roza.clustering.SimilarityBasedCriteria;
import src.br.ufsc.ine.leb.roza.clustering.SingleLinkage;
import src.br.ufsc.ine.leb.roza.clustering.SmallestClusterReferee;
import src.br.ufsc.ine.leb.roza.clustering.TestCaseClusterer;
import src.br.ufsc.ine.leb.roza.clustering.TestsPerClassCriteria;
import src.br.ufsc.ine.leb.roza.clustering.ThresholdCriteria;
import src.br.ufsc.ine.leb.roza.extraction.Junit4TestCaseExtractor;
import src.br.ufsc.ine.leb.roza.extraction.TestCaseExtractor;
import src.br.ufsc.ine.leb.roza.loading.RecursiveTextFileLoader;
import src.br.ufsc.ine.leb.roza.loading.TextFileLoader;
import src.br.ufsc.ine.leb.roza.materialization.Junit4WithAssertionsTestCaseMaterializer;
import src.br.ufsc.ine.leb.roza.materialization.TestCaseMaterializer;
import src.br.ufsc.ine.leb.roza.measurement.LccssSimilarityMeasurer;
import src.br.ufsc.ine.leb.roza.measurement.SimilarityMeasurer;
import src.br.ufsc.ine.leb.roza.parsing.Junit4TestClassParser;
import src.br.ufsc.ine.leb.roza.parsing.TestClassParser;
import src.br.ufsc.ine.leb.roza.selection.JavaExtensionTextFileSelector;
import src.br.ufsc.ine.leb.roza.selection.TextFileSelector;
import src.br.ufsc.ine.leb.roza.utils.FolderUtils;
import src.br.ufsc.ine.leb.roza.utils.MathUtils;

public class Experiment {

	public static void main(String[] args) {
		new FolderUtils("main/exec/materializer").createEmptyFolder();
		TextFileLoader loader = new RecursiveTextFileLoader("expt/resources/c");
		TextFileSelector selector = new JavaExtensionTextFileSelector();
		TestClassParser parser = new Junit4TestClassParser();
		TestCaseExtractor extractor = new Junit4TestCaseExtractor();
		TestCaseMaterializer materializer = new Junit4WithAssertionsTestCaseMaterializer("main/exec/materializer");
		SimilarityMeasurer measurer = new LccssSimilarityMeasurer();
		List<TextFile> files = loader.load();
		List<TextFile> selected = selector.select(files);
		List<TestClass> classes = parser.parse(selected);
		List<TestCase> tests = extractor.extract(classes);
		MaterializationReport materialization = materializer.materialize(tests);
		SimilarityReport report = measurer.measure(materialization);

		List<Linkage> linkages = createLinkages(report);
		List<Referee> referees = createReferees();
		List<ThresholdCriteria> criterias = createThresholdCriterias();
		execute(linkages, referees, criterias, report);
	}

	private static List<Linkage> createLinkages(SimilarityReport report) {
		SingleLinkage single = new SingleLinkage(report);
		CompleteLinkage complete = new CompleteLinkage(report);
		AverageLinkage average = new AverageLinkage(report);
		List<Linkage> linkages = Arrays.asList(single, complete, average);
		return linkages;
	}

	private static List<Referee> createReferees() {
		InsecureReferee insecure = new InsecureReferee();
		BiggestClusterReferee biggest = new BiggestClusterReferee();
		SmallestClusterReferee smallest = new SmallestClusterReferee();
		List<Referee> referees = Arrays.asList(insecure, biggest, smallest);
		return referees;
	}

	private static List<ThresholdCriteria> createThresholdCriterias() {
		List<ThresholdCriteria> criterias = new LinkedList<ThresholdCriteria>();
		addLevelBasedCriteria(criterias);
		addSimilarityBasedCriteria(criterias);
		addTestPerClassCriteria(criterias);
		return criterias;
	}

	private static void addLevelBasedCriteria(List<ThresholdCriteria> criterias) {
		for (Integer index = 1; index <= 46; index++) {
			ThresholdCriteria level = new LevelBasedCriteria(index);
			criterias.add(level);
		}
	}

	private static void addSimilarityBasedCriteria(List<ThresholdCriteria> criterias) {
		criterias.add(new SimilarityBasedCriteria(BigDecimal.ZERO));
		for (Integer index = 1; index <= 10; index++) {
			BigDecimal degree = new MathUtils().oneOver(index);
			ThresholdCriteria similarity = new SimilarityBasedCriteria(degree);
			criterias.add(similarity);
		}
	}

	private static void addTestPerClassCriteria(List<ThresholdCriteria> criterias) {
		for (Integer index = 1; index <= 46; index++) {
			ThresholdCriteria level = new TestsPerClassCriteria(index);
			criterias.add(level);
		}
	}

	private static void execute(List<Linkage> linkages, List<Referee> referees, List<ThresholdCriteria> criterias,
			SimilarityReport report) {
		for (Linkage linkage : linkages) {
			for (Referee referee : referees) {
				for (ThresholdCriteria criteria : criterias) {
					TestCaseClusterer clusterer = new DendogramTestCaseClusterer(linkage, referee, criteria);
					Set<Cluster> clusters = clusterer.cluster(report);
					System.out.println(String.format("%s %s %s %s", linkage, referee, criteria, clusters.size()));
				}
			}
		}
	}

}
