package sunit.parser;

import sunit.parser.combined.SafeCombinedParser;

public class SafeParserFactory {
	
	private static SafeParser instance;
	
	private SafeParserFactory() {}
	
	public static SafeParser createParser() {
		
		if (instance != null) {
			return instance;
		}

		instance = new SafeCombinedParser();

		return instance;
	}

}
