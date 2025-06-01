package sunit.parser;

import java.lang.reflect.Method;

@FunctionalInterface
public interface SafeParser {
	
	public boolean isSafe(Method actualMethod);

}
