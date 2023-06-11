package solunit.parser.annotation;

import org.junit.runners.model.FrameworkMethod;

import solunit.annotations.Safe;
import solunit.parser.SafeParser;

public class SafeAnnotationParser implements SafeParser {
	
	@Override
    public boolean isSafe(FrameworkMethod actualMethod) {
    	Safe safe = actualMethod.getAnnotation(Safe.class);
    	return (safe != null);
    }
    
}
