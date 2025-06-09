package src.br.ufsc.ine.leb.roza.measurement.matrix.simian;

import src.br.ufsc.ine.leb.roza.TestCaseMaterialization;
import src.br.ufsc.ine.leb.roza.measurement.matrix.MatrixElementToKeyConverter;

public class SimianMatrixElementToKeyConverter implements MatrixElementToKeyConverter<TestCaseMaterialization, String> {

	@Override
	public String convert(TestCaseMaterialization element) {
		return element.getAbsoluteFilePath();
	}

}
