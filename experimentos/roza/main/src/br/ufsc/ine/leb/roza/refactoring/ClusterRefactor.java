package src.br.ufsc.ine.leb.roza.refactoring;

import java.util.List;
import java.util.Set;

import src.br.ufsc.ine.leb.roza.Cluster;
import src.br.ufsc.ine.leb.roza.TestClass;

public interface ClusterRefactor {

	List<TestClass> refactor(Set<Cluster> clusters);

}
