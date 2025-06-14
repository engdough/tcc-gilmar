package src.br.ufsc.ine.leb.roza.ui.window.content.sidebar.measurements;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import src.br.ufsc.ine.leb.roza.SimilarityAssessment;
import src.br.ufsc.ine.leb.roza.ui.Hub;
import src.br.ufsc.ine.leb.roza.ui.Manager;
import src.br.ufsc.ine.leb.roza.ui.UiComponent;
import src.br.ufsc.ine.leb.roza.ui.model.SimilarityAssessmentRenderer;
import src.br.ufsc.ine.leb.roza.ui.model.SimilarityReportModel;

public class MatrixMeasurementPanel implements UiComponent {

	private MeasurementsTab measurementsTab;

	public MatrixMeasurementPanel(MeasurementsTab measurementsTab) {
		this.measurementsTab = measurementsTab;
	}

	@Override
	public void init(Hub hub, Manager manager) {
		JTable table = new JTable();
		JScrollPane scroll = new JScrollPane(table);
		table.setDefaultRenderer(SimilarityAssessment.class, new SimilarityAssessmentRenderer());
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		measurementsTab.addMiddleComponent(scroll);
		hub.loadTestClassesSubscribe(testClasses -> {
			reset(table);
		});
		hub.extractTestCasesSubscribe(testCases -> {
			reset(table);
		});
		hub.measureTestsSubscribe(similarityReport -> {
			SimilarityReportModel model = new SimilarityReportModel(similarityReport);
			table.setModel(model);
			table.addMouseListener(new MouseAdapter() {

				@Override
				public void mouseClicked(MouseEvent event) {
					Integer row = table.rowAtPoint(event.getPoint());
					Integer col = table.columnAtPoint(event.getPoint());
					SimilarityAssessment assessment = model.getValueAt(row, col);
					hub.compareTestCasePublish(assessment);
				}

			});
		});
		table.setCellSelectionEnabled(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}

	private void reset(JTable table) {
		table.setModel(new DefaultTableModel());
		Arrays.asList(table.getMouseListeners()).forEach(listener -> table.removeMouseListener(listener));
	}

	@Override
	public void addChilds(List<UiComponent> childs) {}

	@Override
	public void start() {}

}
