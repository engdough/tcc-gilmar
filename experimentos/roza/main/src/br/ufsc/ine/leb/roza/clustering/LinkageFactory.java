package src.br.ufsc.ine.leb.roza.clustering;

import src.br.ufsc.ine.leb.roza.SimilarityReport;

public interface LinkageFactory {

	Linkage create(SimilarityReport report);

}
