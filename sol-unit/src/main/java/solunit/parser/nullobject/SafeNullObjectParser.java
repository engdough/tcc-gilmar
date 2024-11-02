package solunit.parser.nullobject;

import java.lang.reflect.Method;

import org.junit.jupiter.api.MethodDescriptor;

import solunit.parser.SafeParser;

public class SafeNullObjectParser implements SafeParser {
	
	@Override
    public boolean isSafe(Method actualMethod) {
    	return false;
    }
    

}
