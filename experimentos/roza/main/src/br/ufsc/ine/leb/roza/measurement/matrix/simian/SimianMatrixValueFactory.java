package src.br.ufsc.ine.leb.roza.measurement.matrix.simian;

import src.br.ufsc.ine.leb.roza.TestCaseMaterialization;
import src.br.ufsc.ine.leb.roza.measurement.intersector.Intersector;
import src.br.ufsc.ine.leb.roza.measurement.matrix.MatrixValueFactory;

public class SimianMatrixValueFactory implements MatrixValueFactory<TestCaseMaterialization, Intersector> {

	@Override
	public Intersector create(TestCaseMaterialization source, TestCaseMaterialization target) {
		Intersector intersector = new Intersector(source.getLength());
		if (source.equals(target)) {
			intersector.addSegment(1, source.getLength());
		}
		return intersector;
	}

}
