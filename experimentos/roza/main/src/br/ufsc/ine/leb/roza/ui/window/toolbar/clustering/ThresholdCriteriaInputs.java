package src.br.ufsc.ine.leb.roza.ui.window.toolbar.clustering;

import java.math.BigDecimal;
import java.util.List;

import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import src.br.ufsc.ine.leb.roza.clustering.LevelBasedCriteria;
import src.br.ufsc.ine.leb.roza.clustering.NeverStopCriteria;
import src.br.ufsc.ine.leb.roza.clustering.SimilarityBasedCriteria;
import src.br.ufsc.ine.leb.roza.clustering.TestsPerClassCriteria;
import src.br.ufsc.ine.leb.roza.ui.Hub;
import src.br.ufsc.ine.leb.roza.ui.Manager;
import src.br.ufsc.ine.leb.roza.ui.UiComponent;

public class ThresholdCriteriaInputs implements UiComponent {

	private Hub hub;
	private Manager manager;

	private ClusteringTab toolbar;

	private JSpinner levelInput;
	private JSpinner testsPerClassInput;
	private JSpinner similarityInput;

	public ThresholdCriteriaInputs(ClusteringTab toolbar) {
		this.toolbar = toolbar;
	}

	@Override
	public void init(Hub hub, Manager manager) {
		this.hub = hub;
		this.manager = manager;
		levelInput = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
		testsPerClassInput = new JSpinner(new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1));
		similarityInput = new JSpinner(new SpinnerNumberModel(0.5, 0.0, 1.0, 0.1));
		similarityInput.setPreferredSize(levelInput.getPreferredSize());
		levelInput.setVisible(false);
		testsPerClassInput.setVisible(false);
		similarityInput.setVisible(false);
		createValueChangedEvents();
		createSelectionEvents();
		toolbar.addComponent(levelInput);
		toolbar.addComponent(testsPerClassInput);
		toolbar.addComponent(similarityInput);
	}

	private BigDecimal getBigDecimalValue(JSpinner spiner) {
		Double valor = (Double) spiner.getValue();
		BigDecimal convertido = new BigDecimal(valor);
		return convertido;
	}

	private Integer getIntegerValue(JSpinner spiner) {
		Integer valor = (Integer) spiner.getValue();
		return valor;
	}

	private void createValueChangedEvents() {
		levelInput.addChangeListener((evento) -> {
			Integer valor = getIntegerValue(levelInput);
			manager.setThresholdCriteria(new LevelBasedCriteria(valor));
		});
		testsPerClassInput.addChangeListener((evento) -> {
			Integer valor = getIntegerValue(testsPerClassInput);
			manager.setThresholdCriteria(new TestsPerClassCriteria(valor));
		});
		similarityInput.addChangeListener((evento) -> {
			BigDecimal convertido = getBigDecimalValue(similarityInput);
			manager.setThresholdCriteria(new SimilarityBasedCriteria(convertido));
		});
	}

	private void createSelectionEvents() {
		hub.selectLevelBasedCriteriaSubscribe(() -> {
			levelInput.setVisible(true);
			testsPerClassInput.setVisible(false);
			similarityInput.setVisible(false);
			manager.setThresholdCriteria(new LevelBasedCriteria(getIntegerValue(levelInput)));
		});
		hub.selectTestsPerClassCriteriaSubscribe(() -> {
			levelInput.setVisible(false);
			testsPerClassInput.setVisible(true);
			similarityInput.setVisible(false);
			manager.setThresholdCriteria(new TestsPerClassCriteria(getIntegerValue(testsPerClassInput)));
		});
		hub.selectSimilarityBasedCriteriaSubscribe(() -> {
			levelInput.setVisible(false);
			testsPerClassInput.setVisible(false);
			similarityInput.setVisible(true);
			manager.setThresholdCriteria(new SimilarityBasedCriteria(getBigDecimalValue(similarityInput)));
		});
		hub.selectNeverStopCriteriaSubscribe(() -> {
			levelInput.setVisible(false);
			testsPerClassInput.setVisible(false);
			similarityInput.setVisible(false);
			manager.setThresholdCriteria(new NeverStopCriteria());
		});
	}

	@Override
	public void addChilds(List<UiComponent> childs) {}

	@Override
	public void start() {}

}
