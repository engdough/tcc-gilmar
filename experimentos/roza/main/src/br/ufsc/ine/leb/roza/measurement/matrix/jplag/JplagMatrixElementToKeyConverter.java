package src.br.ufsc.ine.leb.roza.measurement.matrix.jplag;

import src.br.ufsc.ine.leb.roza.TestCaseMaterialization;
import src.br.ufsc.ine.leb.roza.measurement.matrix.MatrixElementToKeyConverter;

public class JplagMatrixElementToKeyConverter implements MatrixElementToKeyConverter<TestCaseMaterialization, String> {

	@Override
	public String convert(TestCaseMaterialization element) {
		return element.getFileName();
	}

}
