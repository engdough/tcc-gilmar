package src.br.ufsc.ine.leb.roza.clustering;

import java.math.BigDecimal;

import src.br.ufsc.ine.leb.roza.Cluster;

public interface Linkage {

	BigDecimal evaluate(Cluster first, Cluster second);

}
