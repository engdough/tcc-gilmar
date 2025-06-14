package src.br.ufsc.ine.leb.roza.measurement.configuration.jplag;

import java.util.List;

import src.br.ufsc.ine.leb.roza.measurement.configuration.Configuration;
import src.br.ufsc.ine.leb.roza.measurement.configuration.StringConfiguration;

public class JplagStringConfiguration extends StringConfiguration implements Configuration {

	public JplagStringConfiguration(String name, String value, String description) {
		super(name, value, description);
	}

	@Override
	public void addArgument(List<String> arguments) {
		arguments.add(String.format("-%s", getName()));
		arguments.add(String.format("%s", getValue()));
	}

}
