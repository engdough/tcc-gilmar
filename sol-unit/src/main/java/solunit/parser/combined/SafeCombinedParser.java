package solunit.parser.combined;

import java.io.IOException;
import java.lang.reflect.Method;

import org.junit.jupiter.api.MethodDescriptor;

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
			String mainSourceDir = new PropertiesReader().loadProperties(Config.PROPERTIES_FILE).getProperty("main.src.dir");
			this.javaParser = new JavaASTparser( testSourceDir, mainSourceDir );
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}


	@Override
	public boolean isSafe(Method actualMethod) {
		Safe safe = actualMethod.getAnnotation(Safe.class);
    	return (safe != null) || this.javaParser.isSafe(actualMethod);
	}
	
}