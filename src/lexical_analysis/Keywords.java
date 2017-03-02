package lexical_analysis;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Keywords {	
	
	private Map<String, String> keywordMap;
	
	public Keywords(){
		
		List<String> keywordArray = Arrays.asList(	"abstract", "continue", "for", 			"new", 			"switch",
													"assert", 	"default", 	"goto", 		"package", 		"synchronized",
													"boolean", 	"do", 		"if", 			"private", 		"this",
													"break", 	"double", 	"implements", 	"protected",	"throw",
													"byte", 	"else", 	"import", 		"public", 		"throws",
													"case", 	"enum", 	"instanceof",	"return", 		"transient",
													"catch", 	"extends", 	"int", 			"short", 		"try",
													"char", 	"final", 	"interface", 	"static", 		"void",
													"class", 	"finally", 	"long", 		"strictfp", 	"volatile",
													"const", 	"float", 	"native", 		"super", 		"while");
		
		keywordMap = new HashMap<String, String>();
		for(String i : keywordArray)
			keywordMap.put(i, "KW" + i.toUpperCase());
		
	}
	
	public boolean contains(String word){
		return keywordMap.containsKey(word);
	}
	
	public String get(String word){
		return keywordMap.get(word);
	}
		
}
