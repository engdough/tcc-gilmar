package src.br.ufsc.ine.leb.roza.ui.window.content.sidebar.classes;

import java.awt.Component;
import java.util.List;

import javax.swing.JSplitPane;

import src.br.ufsc.ine.leb.roza.ui.Hub;
import src.br.ufsc.ine.leb.roza.ui.Manager;
import src.br.ufsc.ine.leb.roza.ui.UiComponent;
import src.br.ufsc.ine.leb.roza.ui.window.content.sidebar.Sidebar;

public class TestClassesTab implements UiComponent {

	private Sidebar sidebar;
	private JSplitPane panel;

	public TestClassesTab(Sidebar sidebar) {
		this.sidebar = sidebar;
	}

	@Override
	public void init(Hub hub, Manager manager) {
		panel = new JSplitPane();
		panel.setOrientation(JSplitPane.VERTICAL_SPLIT);
		panel.setResizeWeight(0.5);
		sidebar.addComponent("Test Classes", panel);
	}

	@Override
	public void addChilds(List<UiComponent> childs) {
		childs.add(new TestClassList(this));
		childs.add(new TestClassInformation(this));
	}

	@Override
	public void start() {}

	public void addTopComponent(Component component) {
		panel.setTopComponent(component);
	}

	public void addBottomComponent(Component component) {
		panel.setBottomComponent(component);
	}

}
