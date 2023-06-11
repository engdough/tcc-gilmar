package solunit.parser.nullobject;

import org.junit.runners.model.FrameworkMethod;

import solunit.parser.SafeParser;

public class SafeNullObjectParser implements SafeParser {
	
	@Override
    public boolean isSafe(FrameworkMethod actualMethod) {
    	return false;
    }
    

}
