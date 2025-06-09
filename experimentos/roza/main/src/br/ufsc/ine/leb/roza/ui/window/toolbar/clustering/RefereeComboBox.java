package src.br.ufsc.ine.leb.roza.ui.window.toolbar.clustering;

import java.util.List;

import javax.swing.JComboBox;

import src.br.ufsc.ine.leb.roza.clustering.AnyClusterReferee;
import src.br.ufsc.ine.leb.roza.clustering.BiggestClusterReferee;
import src.br.ufsc.ine.leb.roza.clustering.ComposedReferee;
import src.br.ufsc.ine.leb.roza.clustering.InsecureReferee;
import src.br.ufsc.ine.leb.roza.clustering.SmallestClusterReferee;
import src.br.ufsc.ine.leb.roza.ui.Hub;
import src.br.ufsc.ine.leb.roza.ui.Manager;
import src.br.ufsc.ine.leb.roza.ui.UiComponent;
import src.br.ufsc.ine.leb.roza.ui.shared.ComboBoxBuilder;

public class RefereeComboBox implements UiComponent {

	private ClusteringTab toolbar;
	private JComboBox<String> combo;

	public RefereeComboBox(ClusteringTab toolbar) {
		this.toolbar = toolbar;
	}

	@Override
	public void init(Hub hub, Manager manager) {
		ComboBoxBuilder builder = new ComboBoxBuilder("Referee Strategy");
		builder.add("Any Cluster Referee", () -> manager.setReferee(new AnyClusterReferee()));
		builder.add("Smallest Cluster Referee", () -> manager.setReferee(new BiggestClusterReferee()));
		builder.add("Smallest and Any Cluster Referee", () -> manager.setReferee(new ComposedReferee(new BiggestClusterReferee(), new AnyClusterReferee())));
		builder.add("Biggest Cluster Referee", () -> manager.setReferee(new SmallestClusterReferee()));
		builder.add("Biggest and Any Cluster Referee", () -> manager.setReferee(new ComposedReferee(new SmallestClusterReferee(), new AnyClusterReferee())));
		builder.add("Insecure Referee", () -> manager.setReferee(new InsecureReferee()));
		combo = builder.build();
		toolbar.addComponent(combo);
	}

	@Override
	public void addChilds(List<UiComponent> childs) {}

	@Override
	public void start() {
		combo.setSelectedIndex(5);
	}

}
