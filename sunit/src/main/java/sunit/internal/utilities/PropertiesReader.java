package sunit.internal.utilities;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesReader {
	public Properties loadProperties(String file) throws IOException {
		Properties p =  new Properties();
		InputStream in = PropertiesReader.class.getClassLoader().getResourceAsStream(file);
		p.load(in);
		in.close();
		return p;
	}
}
