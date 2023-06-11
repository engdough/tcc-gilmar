package solunit.parser;

import org.junit.runners.model.FrameworkMethod;

@FunctionalInterface
public interface SafeParser {
	
	public boolean isSafe(FrameworkMethod actualMethod);

}