package solunit.parser.combined;

import java.io.IOException;

import org.junit.runners.model.FrameworkMethod;

import solunit.annotations.Safe;
import solunit.constants.Config;
import solunit.internal.utilities.PropertiesReader;
import solunit.parser.SafeParser;
import solunit.parser.code.ast.JavaASTparser;

public class SafeCombinedParser implements SafeParser {

	private JavaASTparser javaParser;
	
	public SafeCombinedParser() {
		try {
			String testSourceDir = new PropertiesReader().loadProperties(Config.PROPERTIES_FILE).getProperty("test.src.dir");
			this.javaParser = new JavaASTparser( testSourceDir );
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}


	@Override
	public boolean isSafe(FrameworkMethod actualMethod) {
		Safe safe = actualMethod.getAnnotation(Safe.class);
    	return (safe != null) || this.javaParser.isSafe(actualMethod);
	}
	
}