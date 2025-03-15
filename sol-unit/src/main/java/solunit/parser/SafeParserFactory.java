package solunit.parser;

import java.io.IOException;
import java.util.Properties;

import solunit.constants.Config;
import solunit.internal.utilities.PropertiesReader;
import solunit.parser.annotation.SafeAnnotationParser;

public class SafeParserFactory {
	
	private static SafeParser instance;
	
	private SafeParserFactory() {}
	
	public static SafeParser createParser() {
		
		if (instance != null) {
			return instance;
		}
		
		try {
			Properties testProperties = new PropertiesReader().loadProperties(Config.PROPERTIES_FILE);

			Class<?> clazz = Class.forName(testProperties.getProperty("safe.parser.class"));

			instance  = (SafeParser) clazz.newInstance();

			return instance;
		} catch (IOException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}

		return new SafeAnnotationParser();
		
	}

}
