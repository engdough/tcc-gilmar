package solunit.parser.code;

import java.io.IOException;

import org.junit.runners.model.FrameworkMethod;

import solunit.constants.Config;
import solunit.internal.utilities.PropertiesReader;
import solunit.parser.SafeParser;
import solunit.parser.code.ast.JavaASTparser;

public class SafeCodeParser implements SafeParser {

	private JavaASTparser javaParser;
	
	public SafeCodeParser() {
		try {
			String testSourceDir = new PropertiesReader().loadProperties(Config.PROPERTIES_FILE).getProperty("test.src.dir");
			this.javaParser = new JavaASTparser( testSourceDir );
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}


	@Override
	public boolean isSafe(FrameworkMethod actualMethod) {
		return this.javaParser.isSafe(actualMethod);
	}

}
