package lexical_analysis;

import java.util.HashMap;
import java.util.Map;

class Symbols {	
	
	private Map<String, String> symbolMap;
	
	public Symbols(){
		
		symbolMap = new HashMap<String, String>();
		
		symbolMap.put("(", "OPAR");
		symbolMap.put(")", "CPAR");
		symbolMap.put("[", "OBRACKET");
		symbolMap.put("]", "CBRACKET");
		symbolMap.put(",", "COMMA");
		symbolMap.put(".", "DOT");
		symbolMap.put(";", "SEMICOLON");
		symbolMap.put(":", "COLON");
		symbolMap.put("{", "OBRACE");
		symbolMap.put("}", "CBRACE");
		
	}
	
	public boolean contains(String word){
		return symbolMap.containsKey(word);
	}
	
	public String get(String word){
		return symbolMap.get(word);
	}
		
}
