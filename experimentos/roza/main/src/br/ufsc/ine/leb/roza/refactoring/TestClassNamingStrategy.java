package src.br.ufsc.ine.leb.roza.refactoring;

import java.util.List;

import src.br.ufsc.ine.leb.roza.Field;
import src.br.ufsc.ine.leb.roza.SetupMethod;
import src.br.ufsc.ine.leb.roza.TestMethod;

public interface TestClassNamingStrategy {

	String nominate(List<Field> fields, List<SetupMethod> setupMethods, List<TestMethod> testMethods);

}
