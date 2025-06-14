package src.br.ufsc.ine.leb.roza.ui.window.toolbar.refactoring;

import java.util.List;

import javax.swing.JComboBox;

import src.br.ufsc.ine.leb.roza.refactoring.IncrementalTestClassNamingStrategy;
import src.br.ufsc.ine.leb.roza.refactoring.SimpleClusterRefactor;
import src.br.ufsc.ine.leb.roza.ui.Hub;
import src.br.ufsc.ine.leb.roza.ui.Manager;
import src.br.ufsc.ine.leb.roza.ui.UiComponent;
import src.br.ufsc.ine.leb.roza.ui.shared.ComboBoxBuilder;

public class RefactorStrategyComboBox implements UiComponent {

	private RefactoringTab toolbar;
	private JComboBox<String> combo;

	public RefactorStrategyComboBox(RefactoringTab toolbar) {
		this.toolbar = toolbar;
	}

	@Override
	public void init(Hub hub, Manager manager) {
		combo = new ComboBoxBuilder("Refactor Strategy").add("Simple Cluster Refactor", () -> {
			manager.setRefactorStrategy(new SimpleClusterRefactor(new IncrementalTestClassNamingStrategy()));
		}).build();
		combo.setEnabled(true);
		toolbar.addComponent(combo);
	}

	@Override
	public void addChilds(List<UiComponent> childs) {}

	@Override
	public void start() {
		combo.setSelectedIndex(0);
	}

}
