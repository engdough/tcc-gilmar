package src.br.ufsc.ine.leb.roza.ui.window.toolbar.measuring;

import java.util.List;

import javax.swing.JComboBox;

import src.br.ufsc.ine.leb.roza.measurement.DeckardSimilarityMeasurer;
import src.br.ufsc.ine.leb.roza.measurement.JplagSimilarityMeasurer;
import src.br.ufsc.ine.leb.roza.measurement.LccssSimilarityMeasurer;
import src.br.ufsc.ine.leb.roza.measurement.LcsSimilarityMeasurer;
import src.br.ufsc.ine.leb.roza.measurement.SimianSimilarityMeasurer;
import src.br.ufsc.ine.leb.roza.measurement.configuration.deckard.DeckardConfigurations;
import src.br.ufsc.ine.leb.roza.measurement.configuration.jplag.JplagConfigurations;
import src.br.ufsc.ine.leb.roza.measurement.configuration.simian.SimianConfigurations;
import src.br.ufsc.ine.leb.roza.ui.Hub;
import src.br.ufsc.ine.leb.roza.ui.Manager;
import src.br.ufsc.ine.leb.roza.ui.UiComponent;
import src.br.ufsc.ine.leb.roza.ui.shared.ComboBoxBuilder;

public class MeasurerComboBox implements UiComponent {

	private MeasuringTab toolbar;
	private JComboBox<String> combo;

	public MeasurerComboBox(MeasuringTab toolbar) {
		this.toolbar = toolbar;
	}

	@Override
	public void init(Hub hub, Manager manager) {
		combo = new ComboBoxBuilder("Measurer").add("LCCSS", () -> {
			manager.setSimilarityMeasurer(new LccssSimilarityMeasurer());
			hub.unselectDeckardMetricPublish();
			hub.unselectJplagMetricPublish();
			hub.unselectSimianMetricPublish();
		}).add("LCS", () -> {
			manager.setSimilarityMeasurer(new LcsSimilarityMeasurer());
		}).add("Deckard", () -> {
			DeckardConfigurations settings = new DeckardConfigurations(true);
			manager.setSimilarityMeasurer(new DeckardSimilarityMeasurer(settings));
			hub.unselectJplagMetricPublish();
			hub.unselectSimianMetricPublish();
			hub.selectDeckardMetricPublish();
			hub.changeDeckardSettingsSubscribe((minTokens, stride, similarity) -> {
				settings.minTokens(minTokens);
				settings.stride(stride);
				settings.similarity(similarity);
			});
		}).add("JPlag", () -> {
			JplagConfigurations settings = new JplagConfigurations();
			manager.setSimilarityMeasurer(new JplagSimilarityMeasurer(settings));
			hub.unselectDeckardMetricPublish();
			hub.unselectSimianMetricPublish();
			hub.selectJplagMetricPublish();
			hub.changeJplagSettingsSubscribe(sensitivity -> {
				settings.sensitivity(sensitivity);
			});
		}).add("Simian", () -> {
			SimianConfigurations settings = new SimianConfigurations();
			manager.setSimilarityMeasurer(new SimianSimilarityMeasurer(settings));
			hub.unselectDeckardMetricPublish();
			hub.unselectJplagMetricPublish();
			hub.selectSimianMetricPublish();
			hub.changeSimianSettingsSubscribe(threshold -> {
				settings.threshold(threshold);
			});
		}).build();
		toolbar.addComponent(combo);
	}

	@Override
	public void addChilds(List<UiComponent> childs) {}

	@Override
	public void start() {
		combo.setSelectedIndex(0);
	}

}
