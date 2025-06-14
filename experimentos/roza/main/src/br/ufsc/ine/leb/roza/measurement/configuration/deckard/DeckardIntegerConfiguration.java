package src.br.ufsc.ine.leb.roza.measurement.configuration.deckard;

import java.util.List;

import src.br.ufsc.ine.leb.roza.measurement.configuration.Configuration;
import src.br.ufsc.ine.leb.roza.measurement.configuration.IntegerConfiguration;

public class DeckardIntegerConfiguration extends IntegerConfiguration implements Configuration {

	public DeckardIntegerConfiguration(String name, Integer value, String description) {
		super(name, value, description);
	}

	@Override
	public void addArgument(List<String> arguments) {
		String argument = getValue().equals(Integer.MAX_VALUE) ? "inf" : getValue().toString();
		arguments.add(String.format("export %s=%s", getName(), argument));
	}

}
