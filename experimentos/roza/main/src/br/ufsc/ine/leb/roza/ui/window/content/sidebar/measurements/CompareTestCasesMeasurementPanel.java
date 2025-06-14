package src.br.ufsc.ine.leb.roza.ui.window.content.sidebar.measurements;

import java.util.List;

import javax.swing.JSplitPane;

import src.br.ufsc.ine.leb.roza.TestCase;
import src.br.ufsc.ine.leb.roza.ui.Hub;
import src.br.ufsc.ine.leb.roza.ui.Manager;
import src.br.ufsc.ine.leb.roza.ui.UiComponent;
import src.br.ufsc.ine.leb.roza.ui.shared.CodePanel;

public class CompareTestCasesMeasurementPanel implements UiComponent {

	private MeasurementsTab measurementsTab;
	private JSplitPane panel;

	public CompareTestCasesMeasurementPanel(MeasurementsTab measurementsTab) {
		this.measurementsTab = measurementsTab;
	}

	@Override
	public void init(Hub hub, Manager manager) {
		panel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		CodePanel sourcePanel = new CodePanel();
		CodePanel targetPanel = new CodePanel();
		panel.setResizeWeight(0.5);
		panel.setLeftComponent(sourcePanel);
		panel.setRightComponent(targetPanel);
		measurementsTab.addBottomComponent(panel);
		hub.loadTestClassesSubscribe(testClasses -> {
			sourcePanel.clearTestCase();
			targetPanel.clearTestCase();
		});
		hub.extractTestCasesSubscribe(testCases -> {
			sourcePanel.clearTestCase();
			targetPanel.clearTestCase();
		});
		hub.compareTestCaseSubscribe(assessment -> {
			TestCase source = assessment.getSource();
			TestCase target = assessment.getTarget();
			sourcePanel.setTestCase(source);
			targetPanel.setTestCase(target);
		});
	}

	@Override
	public void addChilds(List<UiComponent> childs) {}

	@Override
	public void start() {}

}
