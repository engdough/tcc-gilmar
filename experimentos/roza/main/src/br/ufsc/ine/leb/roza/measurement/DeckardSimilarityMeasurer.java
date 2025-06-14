package src.br.ufsc.ine.leb.roza.measurement;

import java.io.File;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import src.br.ufsc.ine.leb.roza.MaterializationReport;
import src.br.ufsc.ine.leb.roza.SimilarityReport;
import src.br.ufsc.ine.leb.roza.SimilarityReportBuilder;
import src.br.ufsc.ine.leb.roza.TestCase;
import src.br.ufsc.ine.leb.roza.TestCaseMaterialization;
import src.br.ufsc.ine.leb.roza.measurement.configuration.deckard.DeckardConfigurations;
import src.br.ufsc.ine.leb.roza.measurement.intersector.Intersector;
import src.br.ufsc.ine.leb.roza.measurement.matrix.Matrix;
import src.br.ufsc.ine.leb.roza.measurement.matrix.MatrixElementToKeyConverter;
import src.br.ufsc.ine.leb.roza.measurement.matrix.MatrixPair;
import src.br.ufsc.ine.leb.roza.measurement.matrix.MatrixValueFactory;
import src.br.ufsc.ine.leb.roza.measurement.matrix.deckard.DeckardMatrixElementToKeyConverter;
import src.br.ufsc.ine.leb.roza.measurement.matrix.deckard.DeckardMatrixValueFactory;
import src.br.ufsc.ine.leb.roza.utils.FileUtils;
import src.br.ufsc.ine.leb.roza.utils.FolderUtils;
import src.br.ufsc.ine.leb.roza.utils.ProcessUtils;

public class DeckardSimilarityMeasurer extends AbstractSimilarityMeasurer implements SimilarityMeasurer {

	private DeckardConfigurations configurations;

	public DeckardSimilarityMeasurer(DeckardConfigurations configurations) {
		this.configurations = configurations;
	}

	@Override
	public SimilarityReport measureMoreThanOne(MaterializationReport materializationReport, SimilarityReportBuilder builder) {
		List<TestCaseMaterialization> materializations = materializationReport.getMaterializations();
		MatrixElementToKeyConverter<TestCaseMaterialization, String> converter = new DeckardMatrixElementToKeyConverter();
		MatrixValueFactory<TestCaseMaterialization, Intersector> factory = new DeckardMatrixValueFactory();
		Matrix<TestCaseMaterialization, String, Intersector> matrix = new Matrix<>(materializations, converter, factory);
		run(materializationReport);
		parse(matrix);
		for (MatrixPair<TestCaseMaterialization, Intersector> pair : matrix.getNonReflexivePairs()) {
			TestCase source = pair.getSource().getTestCase();
			TestCase target = pair.getTarget().getTestCase();
			Intersector intersector = pair.getValue();
			BigDecimal evaluation = intersector.evaluate();
			builder.add(source, target, evaluation);
		}
		return builder.build();
	}

	private void parse(Matrix<TestCaseMaterialization, String, Intersector> matrix) {
		List<File> reports = new FolderUtils(configurations.clusterDir()).listFilesRecursively("post_cluster_vdb_.*");
		for (File reportFile : reports) {
			String reportContent = new FileUtils().readContetAsString(reportFile).trim();
			String lineSeparator = System.lineSeparator();
			List<String> clusters = Arrays.asList(reportContent.split(lineSeparator + lineSeparator));
			Iterator<String> clusterIterator = clusters.stream().filter((cluster) -> {
				return !cluster.trim().isEmpty();
			}).iterator();
			while (clusterIterator.hasNext()) {
				String cluster = clusterIterator.next();
				List<String> matches = Arrays.asList(cluster.split(lineSeparator));
				for (String sourceMatch : matches) {
					List<String> sourceFields = Arrays.asList(sourceMatch.split("\\s"));
					String sourceFile = new File(sourceFields.get(3)).getName();
					Integer sourceLine = Integer.parseInt(sourceFields.get(4).replaceFirst("LINE:([0-9]+):([0-9]+)", "$1"));
					Integer sourceLenght = Integer.parseInt(sourceFields.get(4).replaceFirst("LINE:([0-9]+):([0-9]+)", "$2"));
					for (String targetMatch : matches) {
						/**
						 * TODO: the report might being interpreted incorrectly.
						 * Possibly we must to make sure to only include targets that have the same TBID and TEID.
						 * Otherwise we will consider the line a match even if it is not.
						 * Consider the following example:
						 *
						 * TestClass18CadastrarFichaDeAvaliacaoVerFichaDeAvaliacaoTest.java LINE:9:1 NODE_KIND:121 nVARs:2 NUM_NODE:8 TBID:49 TEID:54
						 * TestClass18CadastrarFichaDeAvaliacaoVerFichaDeAvaliacaoTest.java LINE:10:1 NODE_KIND:121 nVARs:2 NUM_NODE:8 TBID:55 TEID:60
						 * TestClass18CadastrarFichaDeAvaliacaoVerFichaDeAvaliacaoTest.java LINE:11:1 NODE_KIND:121 nVARs:2 NUM_NODE:8 TBID:61 TEID:66
						 * TestClass18CadastrarFichaDeAvaliacaoVerFichaDeAvaliacaoTest.java LINE:12:1 NODE_KIND:121 nVARs:2 NUM_NODE:8 TBID:67 TEID:72
						 * TestClass18CadastrarFichaDeAvaliacaoVerFichaDeAvaliacaoTest.java LINE:30:1 NODE_KIND:121 nVARs:2 NUM_NODE:8 TBID:200 TEID:205
						 * TestClass38ImportarProducoesDuasVezesComSucessoTest.java LINE:9:1 NODE_KIND:121 nVARs:2 NUM_NODE:8 TBID:49 TEID:54
						 * TestClass38ImportarProducoesDuasVezesComSucessoTest.java LINE:10:1 NODE_KIND:121 nVARs:2 NUM_NODE:8 TBID:55 TEID:60
						 * TestClass38ImportarProducoesDuasVezesComSucessoTest.java LINE:11:1 NODE_KIND:121 nVARs:2 NUM_NODE:8 TBID:61 TEID:66
						 * TestClass38ImportarProducoesDuasVezesComSucessoTest.java LINE:12:1 NODE_KIND:121 nVARs:2 NUM_NODE:8 TBID:67 TEID:72
						 *
						 * In the above example we will consider the fifth line a match, but it is not because there is no equivalence for the target.
						 * This will demand a deeper investigation about how Deckard's report works.
						 * */
						List<String> targetFields = Arrays.asList(targetMatch.split("\\s"));
						String targetFile = new File(targetFields.get(3)).getName();
						matrix.get(sourceFile, targetFile).addSegment(sourceLine, sourceLine + sourceLenght - 1);
					}
				}
			}
		}
	}

	private void run(MaterializationReport materializationReport) {
		FolderUtils folderUtils = new FolderUtils("main/tools/deckard");
		String argumentsText = configurations.getAllAsArguments().stream().collect(Collectors.joining(System.lineSeparator()));
		String argumentsScript = String.format("#!/bin/sh\n%s", argumentsText);
		folderUtils.writeContetAsString("config", argumentsScript);
		ProcessUtils processUtils = new ProcessUtils(true, true, true, false);
		if (configurations.isDockerMode()) {
			processUtils.execute(new File("main/tools/deckard"), "./execute-using-docker.sh");
		} else {
			processUtils.execute(new File("main/tools/deckard"), "./execute.sh");
		}
		folderUtils.removeFile("config");
	}

}
