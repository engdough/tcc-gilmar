package solunit.parser;

import java.lang.reflect.Method;

import org.junit.jupiter.api.MethodDescriptor;

@FunctionalInterface
public interface SafeParser {
	
	public boolean isSafe(Method actualMethod);

}