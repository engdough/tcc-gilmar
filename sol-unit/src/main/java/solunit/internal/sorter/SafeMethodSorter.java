package solunit.internal.sorter;

import java.util.Comparator;

import org.junit.runners.model.FrameworkMethod;

import solunit.mapper.MapperTimeTest;
import solunit.model.TimeTest;
import solunit.parser.SafeParser;
import solunit.parser.SafeParserFactory;

public class SafeMethodSorter implements Comparator<FrameworkMethod> {
	
	SafeParser parser;

	MapperTimeTest mapperTimeTest;
	
	public SafeMethodSorter() {
		this.parser = SafeParserFactory.createParser();
	}
	
    public int compare(FrameworkMethod m1, FrameworkMethod m2) {

		this.mapperTimeTest = MapperTimeTest.getMapper(m1.getMethod().getDeclaringClass().getName());
    	
    	boolean i1Safe = parser.isSafe(m1);
		TimeTest i1Time = this.mapperTimeTest.get(m1.getMethod().getName());

    	boolean i2Safe = parser.isSafe(m2);
		TimeTest i2Time = this.mapperTimeTest.get(m2.getMethod().getName());

        if (i1Safe && !i2Safe) {
            return -1;
        }
        else if (!i1Safe && i2Safe) {
            return 1;
        }
		else if (i1Time == null && i2Time != null) {
			return 1;
		}
		else if (i1Time != null && i2Time == null) {
			return -1;
		}
		else if (i1Time == null && i2Time == null) {
			return 0;
		}
		else if (i1Time.getDuration() < i2Time.getDuration()) {
			return -1;
		}
        return 0;
    	
    }
    
}
