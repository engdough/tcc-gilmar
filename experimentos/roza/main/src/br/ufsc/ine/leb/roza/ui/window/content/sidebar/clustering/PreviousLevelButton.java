package src.br.ufsc.ine.leb.roza.ui.window.content.sidebar.clustering;

import java.util.List;

import javax.swing.JButton;

import src.br.ufsc.ine.leb.roza.clustering.Level;
import src.br.ufsc.ine.leb.roza.ui.Hub;
import src.br.ufsc.ine.leb.roza.ui.Manager;
import src.br.ufsc.ine.leb.roza.ui.UiComponent;

public class PreviousLevelButton implements UiComponent {

	private ClusteringTab clusteringTab;
	private Level selected;
	private List<Level> levels;

	public PreviousLevelButton(ClusteringTab clusteringTab) {
		this.clusteringTab = clusteringTab;
	}

	@Override
	public void init(Hub hub, Manager manager) {
		JButton button = new JButton("<");
		button.setEnabled(false);
		hub.distributeTestsSubscribe(levels -> {
			button.setEnabled(false);
			this.levels = levels;
			if (!levels.isEmpty()) {
				this.selected = levels.get(0);
			}
		});
		hub.selectLevelSubscribe(level -> {
			selected = level;
			button.setEnabled(selected.getStep() > 0);
		});
		button.addActionListener(event -> {
			Level previous = levels.get(selected.getStep() - 1);
			hub.selectLevelPublish(previous);
			manager.selectCluster(previous.getClusters());
		});
		clusteringTab.setPreviousButton(button);
	}

	@Override
	public void addChilds(List<UiComponent> childs) {}

	@Override
	public void start() {}

}
