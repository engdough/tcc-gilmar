package sunit.parser.annotation;

import java.lang.reflect.Method;

import sunit.annotations.Safe;
import sunit.parser.SafeParser;

public class SafeAnnotationParser implements SafeParser {
	
	@Override
    public boolean isSafe(Method actualMethod) {
    	Safe safe = actualMethod.getAnnotation(Safe.class);
    	return (safe != null);
    }
    
}
