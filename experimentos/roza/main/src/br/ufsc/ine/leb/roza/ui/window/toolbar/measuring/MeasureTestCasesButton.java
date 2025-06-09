package src.br.ufsc.ine.leb.roza.ui.window.toolbar.measuring;

import java.util.List;

import javax.swing.JButton;

import src.br.ufsc.ine.leb.roza.SimilarityReport;
import src.br.ufsc.ine.leb.roza.ui.Hub;
import src.br.ufsc.ine.leb.roza.ui.Manager;
import src.br.ufsc.ine.leb.roza.ui.UiComponent;

public class MeasureTestCasesButton implements UiComponent {

	private MeasuringTab toolbar;

	public MeasureTestCasesButton(MeasuringTab toolbar) {
		this.toolbar = toolbar;
	}

	@Override
	public void init(Hub hub, Manager manager) {
		JButton button = new JButton("Measure Tests");
		toolbar.addComponent(button);
		button.addActionListener(listner -> {
			SimilarityReport similarityReort = manager.measureTestCases();
			hub.measureTestsPublish(similarityReort);
			hub.infoMessagePublish(String.format("Similarity matrix created"));
		});
	}

	@Override
	public void addChilds(List<UiComponent> childs) {}

	@Override
	public void start() {}

}
