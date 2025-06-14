package src.br.ufsc.ine.leb.roza.measurement.configuration.jplag;

import java.util.List;

import src.br.ufsc.ine.leb.roza.measurement.configuration.BooleanConfiguration;
import src.br.ufsc.ine.leb.roza.measurement.configuration.Configuration;

public class JplagBooleanConfiguration extends BooleanConfiguration implements Configuration {

	public JplagBooleanConfiguration(String name, Boolean value, String description) {
		super(name, value, description);
	}

	@Override
	public void addArgument(List<String> arguments) {
		if (getValue()) {
			arguments.add(String.format("-%s", getName()));
		}
	}

}
