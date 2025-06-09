package src.br.ufsc.ine.leb.roza.parsing;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class Junit4TestClassParser extends JunitTestClassParser implements TestClassParser {

	public Junit4TestClassParser() {
		super(Test.class, BeforeAll.class);
	}

}
