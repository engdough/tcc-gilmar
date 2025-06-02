package sunit.internal.sorter;

import java.util.Comparator;

import org.junit.jupiter.api.MethodDescriptor;

import sunit.parser.SafeParser;

public class SafeMethodSorter implements Comparator<MethodDescriptor> {
	
	SafeParser parser;
	
	public SafeMethodSorter() {
		this.parser = new SafeParser();
	}

    public int compare(MethodDescriptor m1, MethodDescriptor m2) {
    	boolean m1Safe = parser.isSafe(m1.getMethod());
    	boolean m2Safe = parser.isSafe(m2.getMethod());

        if (m1Safe && !m2Safe) {
            return -1;
        }
        else if (!m1Safe && m2Safe) {
            return 1;
        }
        return 0;
    }
}
