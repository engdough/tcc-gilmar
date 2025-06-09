package src.br.ufsc.ine.leb.roza.measurement.configuration.simian;

import java.util.List;

import src.br.ufsc.ine.leb.roza.measurement.configuration.Configuration;
import src.br.ufsc.ine.leb.roza.measurement.configuration.StringConfiguration;

public class SimianStringConfiguration extends StringConfiguration implements Configuration {

	public SimianStringConfiguration(String name, String value, String description) {
		super(name, value, description);
	}

	@Override
	public void addArgument(List<String> arguments) {
		String argument = String.format("-%s=%s", getName(), getValue());
		arguments.add(argument);
	}

}
