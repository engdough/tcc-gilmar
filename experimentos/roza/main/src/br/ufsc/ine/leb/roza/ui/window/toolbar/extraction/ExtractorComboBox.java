package src.br.ufsc.ine.leb.roza.ui.window.toolbar.extraction;

import java.util.Arrays;
import java.util.List;

import javax.swing.JComboBox;

import src.br.ufsc.ine.leb.roza.extraction.Junit4TestCaseExtractor;
import src.br.ufsc.ine.leb.roza.extraction.Junit5TestCaseExtractor;
import src.br.ufsc.ine.leb.roza.extraction.JunitTestCaseExtractor;
import src.br.ufsc.ine.leb.roza.ui.Hub;
import src.br.ufsc.ine.leb.roza.ui.Manager;
import src.br.ufsc.ine.leb.roza.ui.UiComponent;
import src.br.ufsc.ine.leb.roza.ui.shared.ComboBoxBuilder;

public class ExtractorComboBox implements UiComponent {

	private ExtractionTab toolbar;
	private JComboBox<String> combo;

	public ExtractorComboBox(ExtractionTab toolbar) {
		this.toolbar = toolbar;
	}

	@Override
	public void init(Hub hub, Manager manager) {
		combo = new ComboBoxBuilder("Extractor").add("JUnit 4", () -> {
			manager.setTestCaseExtractor(new Junit4TestCaseExtractor());
		}).add("JUnit 5", () -> {
			manager.setTestCaseExtractor(new Junit5TestCaseExtractor());
		}).add("JUnit 5 - OMP/CAPES Project", () -> {
			List<String> assertions = Arrays.asList("assegurarTexto", "assegurarValor", "assegurarQuantidadeDeElementos", "assegurarConteudoDeArquivoBaixado", "assegurarNaoMarcado", "assegurarMarcado", "assegurarMarcacao");
			manager.setTestCaseExtractor(new JunitTestCaseExtractor(assertions));
		}).build();
		toolbar.addComponent(combo);
	}

	@Override
	public void addChilds(List<UiComponent> childs) {}

	@Override
	public void start() {
		combo.setSelectedIndex(2);
	}

}
