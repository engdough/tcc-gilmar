package src.br.ufsc.ine.leb.roza.materialization;

import com.github.javaparser.ast.stmt.BlockStmt;

import src.br.ufsc.ine.leb.roza.TestCase;

public class Junit4WithoutAssertionsTestCaseMaterializer extends Junit4TestCaseMaterializer implements TestCaseMaterializer {

	public Junit4WithoutAssertionsTestCaseMaterializer(String baseFolder) {
		super(baseFolder);
	}

	@Override
	protected void addAssertions(TestCase testCase, BlockStmt javaMethodBody) {}

}
