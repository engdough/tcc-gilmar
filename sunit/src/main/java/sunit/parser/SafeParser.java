package sunit.parser;

import java.lang.reflect.Method;

import sunit.parser.combined.SafeCombinedParser;

public class SafeParser {
	
	private static SafeCombinedParser instance;
	
	public  SafeParser() {
		if (instance != null) {
			return;
		}

		instance = new SafeCombinedParser();
	}

	public boolean isSafe(Method md) {
		return instance.isSafe(md);
	}

}
