package src.br.ufsc.ine.leb.roza.measurement;

import src.br.ufsc.ine.leb.roza.MaterializationReport;
import src.br.ufsc.ine.leb.roza.SimilarityReport;

public interface SimilarityMeasurer {

	SimilarityReport measure(MaterializationReport materializationReport);

}
