package src.br.ufsc.ine.leb.roza.ui.window.content.sidebar.classes;

import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import src.br.ufsc.ine.leb.roza.TestClass;
import src.br.ufsc.ine.leb.roza.ui.Hub;
import src.br.ufsc.ine.leb.roza.ui.Manager;
import src.br.ufsc.ine.leb.roza.ui.UiComponent;
import src.br.ufsc.ine.leb.roza.ui.model.TestClassRenderer;

public class TestClassList implements UiComponent {

	private TestClassesTab testClassesTab;

	public TestClassList(TestClassesTab testClassesTab) {
		this.testClassesTab = testClassesTab;
	}

	@Override
	public void init(Hub hub, Manager manager) {
		JList<TestClass> list = new JList<>();
		JScrollPane scroller = new JScrollPane(list);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		testClassesTab.addTopComponent(scroller);
		hub.loadTestClassesSubscribe((List<TestClass> classes) -> {
			DefaultListModel<TestClass> model = new DefaultListModel<>();
			classes.forEach(testClass -> model.addElement(testClass));
			list.setModel(model);
			list.setCellRenderer(new TestClassRenderer());
		});
		list.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent event) {
				TestClass testClass = list.getSelectedValue();
				if (testClass != null && !event.getValueIsAdjusting()) {
					hub.selectTestClassPublish(testClass);
				}
			}

		});
	}

	@Override
	public void addChilds(List<UiComponent> childs) {}

	@Override
	public void start() {}

}
