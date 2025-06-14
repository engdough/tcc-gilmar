package src.br.ufsc.ine.leb.roza.ui.window.toolbar.extraction;

import java.awt.Component;
import java.util.List;

import javax.swing.JPanel;

import src.br.ufsc.ine.leb.roza.ui.Hub;
import src.br.ufsc.ine.leb.roza.ui.Manager;
import src.br.ufsc.ine.leb.roza.ui.UiComponent;
import src.br.ufsc.ine.leb.roza.ui.window.toolbar.Toolbar;

public class ExtractionTab implements UiComponent {

	private Toolbar toolbar;
	private JPanel panel;

	public ExtractionTab(Toolbar toolbar) {
		this.toolbar = toolbar;
	}

	@Override
	public void init(Hub hub, Manager manager) {
		panel = new JPanel();
		toolbar.addComponent("Extraction", panel);
	}

	@Override
	public void addChilds(List<UiComponent> childs) {
		childs.add(new ExtractorComboBox(this));
		childs.add(new ExtractTestCasesButton(this));
	}

	@Override
	public void start() {}

	public void addComponent(Component component) {
		panel.add(component);
	}

}
