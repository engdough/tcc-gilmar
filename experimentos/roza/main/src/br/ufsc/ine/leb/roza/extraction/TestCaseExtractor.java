package src.br.ufsc.ine.leb.roza.extraction;

import java.util.List;

import src.br.ufsc.ine.leb.roza.TestCase;
import src.br.ufsc.ine.leb.roza.TestClass;

public interface TestCaseExtractor {

	List<TestCase> extract(List<TestClass> testClasses);

}
