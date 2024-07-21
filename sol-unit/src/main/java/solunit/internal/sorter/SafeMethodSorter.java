package solunit.internal.sorter;

import java.lang.reflect.Method;
import java.util.Comparator;

import org.junit.jupiter.api.MethodDescriptor;

import solunit.parser.SafeParser;
import solunit.parser.SafeParserFactory;

public class SafeMethodSorter implements Comparator<MethodDescriptor> {
	
	SafeParser parser;
	
	public SafeMethodSorter() {
		this.parser = SafeParserFactory.createParser();
	}
	
    public int compare(MethodDescriptor m1, MethodDescriptor m2) {
    	
    	boolean i1Safe = parser.isSafe(m1.getMethod());
    	boolean i2Safe = parser.isSafe(m2.getMethod());
        if (i1Safe && !i2Safe) {
            return -1;
        }
        else if (!i1Safe && i2Safe) {
            return 1;
        }
        return 0;
    	
    }
    
}