package solunit.internal.utilities;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesReader {
	
	/**
	 * Lê um arquivo de properties do diretório src/main/resources
	 * @param file arquivo de properties
	 * @return Properties do arquivo lido
	 * @throws IOException erro de acesso ao arquivo
	 */
	public Properties loadProperties(String file) throws IOException {
		Properties p =  new Properties();
		InputStream in = PropertiesReader.class.getClassLoader().getResourceAsStream(file);                
		p.load(in);
		in.close();
		return p;
	}

}
