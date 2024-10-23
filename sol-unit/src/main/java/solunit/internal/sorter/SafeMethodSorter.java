package solunit.internal.sorter;

import java.util.Comparator;

import org.junit.runners.model.FrameworkMethod;

import solunit.parser.SafeParser;
import solunit.parser.SafeParserFactory;

public class SafeMethodSorter implements Comparator<FrameworkMethod> {
	
	SafeParser parser;
	
	public SafeMethodSorter() {
		this.parser = SafeParserFactory.createParser();
	}
	
    public int compare(FrameworkMethod m1, FrameworkMethod m2) {
    	
    	boolean i1Safe = parser.isSafe(m1);

    	boolean i2Safe = parser.isSafe(m2);

        if (i1Safe && !i2Safe) {
            return -1;
        }
        else if (!i1Safe && i2Safe) {
            return 1;
        }
        return 0;
    	
    }
    
}
