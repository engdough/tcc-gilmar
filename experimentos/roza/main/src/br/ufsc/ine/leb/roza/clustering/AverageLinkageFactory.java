package src.br.ufsc.ine.leb.roza.clustering;

import src.br.ufsc.ine.leb.roza.SimilarityReport;

public class AverageLinkageFactory implements LinkageFactory {

	@Override
	public Linkage create(SimilarityReport report) {
		return new AverageLinkage(report);
	}

}
