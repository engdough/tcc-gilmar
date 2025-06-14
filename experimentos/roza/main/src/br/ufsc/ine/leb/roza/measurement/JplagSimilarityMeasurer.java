package src.br.ufsc.ine.leb.roza.measurement;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import src.br.ufsc.ine.leb.roza.MaterializationReport;
import src.br.ufsc.ine.leb.roza.SimilarityReport;
import src.br.ufsc.ine.leb.roza.SimilarityReportBuilder;
import src.br.ufsc.ine.leb.roza.TestCase;
import src.br.ufsc.ine.leb.roza.TestCaseMaterialization;
import src.br.ufsc.ine.leb.roza.measurement.configuration.jplag.JplagConfigurations;
import src.br.ufsc.ine.leb.roza.measurement.matrix.Matrix;
import src.br.ufsc.ine.leb.roza.measurement.matrix.MatrixElementToKeyConverter;
import src.br.ufsc.ine.leb.roza.measurement.matrix.MatrixPair;
import src.br.ufsc.ine.leb.roza.measurement.matrix.MatrixValueFactory;
import src.br.ufsc.ine.leb.roza.measurement.matrix.jplag.JplagMatrixElementToKeyConverter;
import src.br.ufsc.ine.leb.roza.measurement.matrix.jplag.JplagMatrixValueFactory;
import src.br.ufsc.ine.leb.roza.utils.FolderUtils;
import src.br.ufsc.ine.leb.roza.utils.ProcessUtils;

public class JplagSimilarityMeasurer extends AbstractSimilarityMeasurer implements SimilarityMeasurer {

	private JplagConfigurations configurations;

	public JplagSimilarityMeasurer(JplagConfigurations configurations) {
		this.configurations = configurations;
	}

	@Override
	public SimilarityReport measureMoreThanOne(MaterializationReport materializationReport, SimilarityReportBuilder builder) {
		List<TestCaseMaterialization> materializations = materializationReport.getMaterializations();
		MatrixElementToKeyConverter<TestCaseMaterialization, String> converter = new JplagMatrixElementToKeyConverter();
		MatrixValueFactory<TestCaseMaterialization, BigDecimal> factory = new JplagMatrixValueFactory();
		Matrix<TestCaseMaterialization, String, BigDecimal> matrix = new Matrix<>(materializations, converter, factory);
		run(materializationReport);
		parse(matrix);
		for (MatrixPair<TestCaseMaterialization, BigDecimal> pair : matrix.getNonReflexivePairs()) {
			TestCase source = pair.getSource().getTestCase();
			TestCase target = pair.getTarget().getTestCase();
			BigDecimal evaluation = pair.getValue();
			builder.add(source, target, evaluation);
		}
		return builder.build();
	}

	private void parse(Matrix<TestCaseMaterialization, String, BigDecimal> matrix) {
		try {
			List<File> results = new FolderUtils(configurations.results()).listFilesRecursively("match[0-9]+-top.html");
			for (File result : results) {
				Document document = Jsoup.parse(result, "utf-8");
				Elements tables = document.body().getElementsByTag("table");
				Element table = tables.get(0);
				Elements tableBodys = table.getElementsByTag("tbody");
				Element tableBody = tableBodys.get(0);
				Elements rows = tableBody.getElementsByTag("tr");
				Element row = rows.get(0);
				Elements columns = row.getElementsByTag("th");
				Element columnFirst = columns.get(1);
				Element columnSecond = columns.get(2);
				String nameFirst = columnFirst.text().split(" ")[0];
				String nameSecond = columnSecond.text().split(" ")[0];
				String textScoreFirst = columnFirst.text().split(" ")[1].replaceAll("[%()]", "");
				String textScoreSecond = columnSecond.text().split(" ")[1].replaceAll("[%()]", "");
				BigDecimal scoreFirst = new BigDecimal(textScoreFirst).divide(new BigDecimal(100));
				BigDecimal scoreSecond = new BigDecimal(textScoreSecond).divide(new BigDecimal(100));
				BigDecimal oneScaled = BigDecimal.ONE.setScale(1);
				BigDecimal oneNotScaled = BigDecimal.ONE;
				scoreFirst = scoreFirst.equals(oneScaled) ? oneNotScaled : scoreFirst;
				scoreSecond = scoreSecond.equals(oneScaled) ? oneNotScaled : scoreSecond;
				matrix.set(nameFirst, nameSecond, scoreFirst);
				matrix.set(nameSecond, nameFirst, scoreSecond);
			}
		} catch (IOException excecao) {
			throw new RuntimeException(excecao);
		}
	}

	private void run(MaterializationReport materializationReport) {
		ProcessUtils processUtils = new ProcessUtils(true, true, true, true);
		List<String> arguments = new LinkedList<String>();
		arguments.add("/home/gilmar/.sdkman/candidates/java/current/bin/java");
		arguments.add("-jar");
		arguments.add("main/tools/jplag/jplag-2.11.9.jar");
		arguments.addAll(configurations.getAllAsArguments());
		processUtils.execute(arguments);
	}

}
