package src.br.ufsc.ine.leb.roza.ui.window.toolbar.clustering;

import java.util.List;

import javax.swing.JComboBox;

import src.br.ufsc.ine.leb.roza.clustering.AverageLinkageFactory;
import src.br.ufsc.ine.leb.roza.clustering.CompleteLinkageFactory;
import src.br.ufsc.ine.leb.roza.clustering.SingleLinkageFactory;
import src.br.ufsc.ine.leb.roza.clustering.SumOfIdsLinkageFactory;
import src.br.ufsc.ine.leb.roza.ui.Hub;
import src.br.ufsc.ine.leb.roza.ui.Manager;
import src.br.ufsc.ine.leb.roza.ui.UiComponent;
import src.br.ufsc.ine.leb.roza.ui.shared.ComboBoxBuilder;

public class LinkageComboBox implements UiComponent {

	private ClusteringTab toolbar;
	private JComboBox<String> combo;

	public LinkageComboBox(ClusteringTab toolbar) {
		this.toolbar = toolbar;
	}

	@Override
	public void init(Hub hub, Manager manager) {
		ComboBoxBuilder builder = new ComboBoxBuilder("Linkage Method");
		builder.add("Sum of Identifiers Linkage", () -> manager.setLinkageFactory(new SumOfIdsLinkageFactory()));
		builder.add("Single Linkage", () -> manager.setLinkageFactory(new SingleLinkageFactory()));
		builder.add("Complete Linkage", () -> manager.setLinkageFactory(new CompleteLinkageFactory()));
		builder.add("Average Linkage", () -> manager.setLinkageFactory(new AverageLinkageFactory()));
		combo = builder.build();
		toolbar.addComponent(combo);
	}

	@Override
	public void addChilds(List<UiComponent> childs) {}

	@Override
	public void start() {
		combo.setSelectedIndex(0);
	}

}
