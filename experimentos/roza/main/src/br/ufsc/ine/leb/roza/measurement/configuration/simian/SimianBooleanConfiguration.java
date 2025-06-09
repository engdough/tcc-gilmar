package src.br.ufsc.ine.leb.roza.measurement.configuration.simian;

import java.util.List;

import src.br.ufsc.ine.leb.roza.measurement.configuration.BooleanConfiguration;
import src.br.ufsc.ine.leb.roza.measurement.configuration.Configuration;

public class SimianBooleanConfiguration extends BooleanConfiguration implements Configuration {

	public SimianBooleanConfiguration(String name, Boolean value, String description) {
		super(name, value, description);
	}

	@Override
	public void addArgument(List<String> arguments) {
		String sinal = getValue() ? "+" : "-";
		String argument = String.format("-%s%s", getName(), sinal);
		arguments.add(argument);
	}

}
