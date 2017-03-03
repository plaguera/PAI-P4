package lexical_analysis;

import java.util.Arrays;
import java.util.List;

public class Operators {
	
	private List<String> operatorArray;
	
	public Operators(){
		
		operatorArray = Arrays.asList(	"=", "+", "-", "*", "/", "%", "++", "--", "!", "==", "!=", ">", ">=", "<", "<=", "&&", "||", "~",
				"<<", ">>", ">>>", "&", "^", "|", "+=", "-=", "*=", "/=", "%=", "<<=", ">>=", "&=", "^=", "|=", "===");
		
	}
	
	public boolean contains(String word){
		return operatorArray.contains(word);
	}

}
