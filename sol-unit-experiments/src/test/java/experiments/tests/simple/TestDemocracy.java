package experiments.tests.simple;

import org.junit.runner.RunWith;

import experiments.contracts.Democracy;
import solunit.annotations.Contract;
import solunit.runner.SolUnitRunner;

@RunWith(SolUnitRunner.class)
public class TestDemocracy {
	
	@Contract
	Democracy democracy;

}
