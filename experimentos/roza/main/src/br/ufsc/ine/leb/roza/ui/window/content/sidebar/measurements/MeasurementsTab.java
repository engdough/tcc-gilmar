package src.br.ufsc.ine.leb.roza.ui.window.content.sidebar.measurements;

import java.awt.Component;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.JPanel;

import src.br.ufsc.ine.leb.roza.ui.Hub;
import src.br.ufsc.ine.leb.roza.ui.Manager;
import src.br.ufsc.ine.leb.roza.ui.UiComponent;
import src.br.ufsc.ine.leb.roza.ui.window.content.sidebar.Sidebar;

public class MeasurementsTab implements UiComponent {

	private Sidebar sidebar;
	private JPanel panel;
	private Component bottom;
	private Component middle;
	private Component top;

	public MeasurementsTab(Sidebar sidebar) {
		this.sidebar = sidebar;
	}

	@Override
	public void init(Hub hub, Manager manager) {
		panel = new JPanel();
		sidebar.addComponent("Measurements", panel);
		hub.measureTestsSubscribe(report -> {
			GroupLayout group = new GroupLayout(panel);
			panel.setLayout(group);
			group.setVerticalGroup(group.createSequentialGroup().addComponent(top, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE).addComponent(middle).addComponent(bottom, 400, 800, GroupLayout.PREFERRED_SIZE));
			group.setHorizontalGroup(group.createParallelGroup().addComponent(top).addComponent(middle).addComponent(bottom));
		});
	}

	@Override
	public void addChilds(List<UiComponent> childs) {
		childs.add(new SelectTestCasesMeasurementPanel(this));
		childs.add(new MatrixMeasurementPanel(this));
		childs.add(new CompareTestCasesMeasurementPanel(this));
	}

	@Override
	public void start() {}

	public void addTopComponent(Component component) {
		this.top = component;
	}

	public void addMiddleComponent(Component component) {
		this.middle = component;
	}

	public void addBottomComponent(Component component) {
		this.bottom = component;
	}

}
