package experiments.tests.simple;

import org.junit.jupiter.api.extension.ExtendWith;

import experiments.contracts.Democracy;
import solunit.annotations.Contract;
import solunit.runner.SolUnitRunner;

@ExtendWith(SolUnitRunner.class)
public class TestDemocracy {
	
	@Contract
	Democracy democracy;

}
