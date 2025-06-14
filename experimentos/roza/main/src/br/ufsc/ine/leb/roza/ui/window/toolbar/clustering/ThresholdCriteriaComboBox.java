package src.br.ufsc.ine.leb.roza.ui.window.toolbar.clustering;

import java.util.List;

import javax.swing.JComboBox;

import src.br.ufsc.ine.leb.roza.ui.Hub;
import src.br.ufsc.ine.leb.roza.ui.Manager;
import src.br.ufsc.ine.leb.roza.ui.UiComponent;
import src.br.ufsc.ine.leb.roza.ui.shared.ComboBoxBuilder;

public class ThresholdCriteriaComboBox implements UiComponent {

	private ClusteringTab toolbar;
	private JComboBox<String> combo;

	public ThresholdCriteriaComboBox(ClusteringTab toolbar) {
		this.toolbar = toolbar;
	}

	@Override
	public void init(Hub hub, Manager manager) {
		ComboBoxBuilder builder = new ComboBoxBuilder("Theshold Criteria");
		builder.add("Level Based Criteria", () -> hub.selectLevelBasedCriteriaPublish());
		builder.add("Test per Class Criteria", () -> hub.selectTestsPerClassCriteriaPublish());
		builder.add("Similarity Based Criteria", () -> hub.selectSimilarityBasedCriteriaPublish());
		builder.add("Never stop", () -> hub.selectNeverStopCriteriaPublish());
		combo = builder.build();
		toolbar.addComponent(combo);
	}

	@Override
	public void addChilds(List<UiComponent> childs) {}

	@Override
	public void start() {
		combo.setSelectedIndex(3);
	}

}
