package solunit.parser.annotation;

import java.lang.reflect.Method;

import org.junit.jupiter.api.MethodDescriptor;

import solunit.annotations.Safe;
import solunit.parser.SafeParser;

public class SafeAnnotationParser implements SafeParser {
	
	@Override
    public boolean isSafe(Method actualMethod) {
    	Safe safe = actualMethod.getAnnotation(Safe.class);
    	return (safe != null);
    }
    
}
