package src.br.ufsc.ine.leb.roza.ui.window.toolbar.clustering;

import java.awt.Component;
import java.util.List;

import javax.swing.JPanel;

import src.br.ufsc.ine.leb.roza.ui.Hub;
import src.br.ufsc.ine.leb.roza.ui.Manager;
import src.br.ufsc.ine.leb.roza.ui.UiComponent;
import src.br.ufsc.ine.leb.roza.ui.window.toolbar.Toolbar;

public class ClusteringTab implements UiComponent {

	private Toolbar toolbar;
	private JPanel panel;

	public ClusteringTab(Toolbar toolbar) {
		this.toolbar = toolbar;
	}

	@Override
	public void init(Hub hub, Manager manager) {
		panel = new JPanel();
		toolbar.addComponent("Clustering", panel);
	}

	@Override
	public void addChilds(List<UiComponent> childs) {
		childs.add(new LinkageComboBox(this));
		childs.add(new RefereeComboBox(this));
		childs.add(new ThresholdCriteriaComboBox(this));
		childs.add(new ThresholdCriteriaInputs(this));
		childs.add(new DistributeTestsButton(this));
	}

	@Override
	public void start() {}

	public void addComponent(Component component) {
		panel.add(component);
	}
}
