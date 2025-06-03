package sunit.parser;

import java.io.IOException;
import java.lang.reflect.Method;

import sunit.annotations.Safe;
import sunit.constants.Config;
import sunit.internal.utilities.PropertiesReader;
import sunit.parser.code.ast.JavaASTparser;

public class SafeParser {

	private JavaASTparser javaParser;
	
	public  SafeParser() {
		if (javaParser != null) {
			return;
		}

		try {
			String testSourceDir = new PropertiesReader().loadProperties(Config.PROPERTIES_FILE).getProperty("test.src.dir");
			String mainSourceDir = new PropertiesReader().loadProperties(Config.PROPERTIES_FILE).getProperty("main.src.dir");
			this.javaParser = new JavaASTparser( testSourceDir, mainSourceDir );
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public boolean isSafe(Method actualMethod) {
		Safe safe = actualMethod.getAnnotation(Safe.class);
		return (safe != null) || this.javaParser.isSafe(actualMethod);
	}
}
