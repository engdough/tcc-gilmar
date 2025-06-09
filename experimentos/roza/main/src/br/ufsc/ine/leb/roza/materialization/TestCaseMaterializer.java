package src.br.ufsc.ine.leb.roza.materialization;

import java.util.List;

import src.br.ufsc.ine.leb.roza.MaterializationReport;
import src.br.ufsc.ine.leb.roza.TestCase;

public interface TestCaseMaterializer {

	MaterializationReport materialize(List<TestCase> tests);

}
