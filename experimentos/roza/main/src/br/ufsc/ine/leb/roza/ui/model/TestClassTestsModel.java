package src.br.ufsc.ine.leb.roza.ui.model;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

import src.br.ufsc.ine.leb.roza.TestClass;
import src.br.ufsc.ine.leb.roza.TestMethod;

public class TestClassTestsModel extends TestClassAbstractModel<TestMethod> {

	private static final long serialVersionUID = 1L;

	public TestClassTestsModel(TestClass testClass) {
		super(testClass);
	}

	@Override
	protected List<Supplier<String>> getColumnTitleMappers() {
		return Arrays.asList(() -> "Name");
	}

	@Override
	protected List<Function<TestMethod, String>> getValueMappers() {
		return Arrays.asList(method -> method.getName());
	}

	@Override
	protected List<TestMethod> getElements(TestClass testClass) {
		return testClass.getTestMethods();
	}

}
