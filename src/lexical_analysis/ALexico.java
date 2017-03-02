package lexical_analysis;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ALexico {

	public static void main(String[] args) {
		
		LexicalAnalysis a = new LexicalAnalysis(args[0]);
		a.dumpToFile();
		
	}
}
