package src.br.ufsc.ine.leb.roza;

import java.util.List;

public class TestMethod {

	private String name;
	private List<Statement> statements;

	public TestMethod(String name, List<Statement> statements) {
		this.name = name;
		this.statements = statements;
	}

	public String getName() {
		return name;
	}

	public List<Statement> getStatements() {
		return statements;
	}

}
