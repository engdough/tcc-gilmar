package src.br.ufsc.ine.leb.roza.ui.window.toolbar.clustering;

import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JButton;

import src.br.ufsc.ine.leb.roza.clustering.Level;
import src.br.ufsc.ine.leb.roza.exceptions.ClusteringLevelGenerationException;
import src.br.ufsc.ine.leb.roza.ui.Hub;
import src.br.ufsc.ine.leb.roza.ui.Manager;
import src.br.ufsc.ine.leb.roza.ui.UiComponent;

public class DistributeTestsButton implements UiComponent {

	private ClusteringTab toolbar;

	public DistributeTestsButton(ClusteringTab toolbar) {
		this.toolbar = toolbar;
	}

	@Override
	public void init(Hub hub, Manager manager) {
		JButton button = new JButton("Distribute Tests");
		toolbar.addComponent(button);
		button.addActionListener(event -> {
			List<Level> levels;
			try {
				levels = manager.distributeTests();
				hub.infoMessagePublish(String.format("Clustering performed"));
			} catch (ClusteringLevelGenerationException exception) {
				levels = exception.getLevels();
				List<String> ties = exception.getTibreakException().getTies().stream().map(tie -> {
					List<String> first = tie.getFirst().getTestCases().stream().map(test -> test.toString()).collect(Collectors.toList());
					List<String> second = tie.getSecond().getTestCases().stream().map(test -> test.toString()).collect(Collectors.toList());
					String firstMessage = String.join(", ", first);
					String secondMessage = String.join(", ", second);
					return String.format("<li><strong>first</strong>: %s, <strong>second</strong>: %s</li>", firstMessage, secondMessage);
				}).collect(Collectors.toList());
				String tieMessage = String.join("", ties);
				String message = String.format("Clustering failed due tiebreak to generate level %d: <ul>%s</ul>", levels.size(), tieMessage);
				hub.errorMessagePublish(message);
			}
			hub.distributeTestsPublish(levels);
		});
	}

	@Override
	public void addChilds(List<UiComponent> childs) {}

	@Override
	public void start() {}

}
