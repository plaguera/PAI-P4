package lexical_analysis;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.LinkedHashMap;

public class LexicalAnalysis {
	
	private List<Token> 			tokens;
	private Keywords				keywords;
	private Symbols 				symbols;
	private List<String> 			operators;
	
	Pattern tokenizerPattern;
	Matcher matcher;
	
	private boolean comment;
	
	public LexicalAnalysis(){
		tokens = new ArrayList<Token>();
		
		tokenizerPattern = Pattern.compile("(/\\*).*(\\*/)|(/\\*).*|.*(\\*/)|(//).*|[\"'].*[\"']|(?<![a-zA-Z_0-9])[-]?[\\s]*[0-9]+(\\.[0-9]+)?(E[-]?[0-9]+)?"
		 		+ "|\\+\\+|--|==|!=|>=|<=|&&|\\|\\||<<|>>>|>>|[=+*/%!><~^&|\\-]|[(){}:;.,\\[\\]]|[a-zA-Z_0-9]+");
		
		keywords = new Keywords();
		symbols = new Symbols();
		operators = Arrays.asList(	"=", "+", "-", "*", "/", "%", "++", "--", "!", "==", "!=", ">", ">=", "<", "<=", "&&", "||", "~", "<<", ">>", ">>>", "&", "^", "|");

        comment = false;
		
	}
	
	public LexicalAnalysis(String filePath){
		this();
		fileToString(filePath);
	}
	
	public void fileToString(String filePath){
		
		int lineNumber = 0;
		
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

			String cl;
			
			while ((cl = br.readLine()) != null) {
				
				lineNumber++;
				
				if(cl.contains("*/"))
					comment = false;
				
				if(!comment)
					for(Map.Entry<String, Integer> entry : tokenizeString(cl).entrySet())
						tokens.add(parseWord(entry.getKey(), lineNumber, entry.getValue()+1));
				
				if(cl.contains("/*") && !cl.contains("*/"))
					comment = true;
					
				
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Token parseWord(String word, int line, int column){
		Token newToken = new Token();
				
		if(isInt(word))
			newToken = new Token("INT", word, line, column);
		
		else if(isFloat(word))
			newToken = new Token("FLOATING", word, line, column);
		
		else if(isID(word))
			newToken = new Token("ID", word, line, column);
		
		else if(isSymbol(word))
			newToken = new Token(symbols.get(word), word, line, column);
		
		else if(isOperator(word))
			newToken = new Token("OPERATOR", word, line, column);
		
		else if(isKeyword(word))
			newToken = new Token(keywords.get(word), word, line, column);
		
		else if(isConstant(word))
			newToken = new Token("CONSTANT", word, line, column);
		
		else
			newToken = new Token("TOKEN", "ERROR", line, column);
		
		System.out.println(newToken);
		
		return newToken;
	}
	
	public Map<String, Integer> tokenizeString(String word){
		
		Map<String, Integer> tokenColumn = new LinkedHashMap<String, Integer>();
		
		matcher = tokenizerPattern.matcher(word) ;  

		 while (matcher.find())
			 tokenColumn.put(matcher.group(), matcher.start());
		
		return tokenColumn;
	}
	
	public boolean isInt(String word){
		return word.matches("-?[0-9]+(E[-]?[0-9]+)?");
	}
	
	public boolean isFloat(String word){
		return word.matches("-?[0-9]+.[0-9]+(E[-]?[0-9]+)?");
	}
	
	public boolean isID(String word){
		return word.matches("[a-zA-Z][a-zA-Z_0-9]*");
	}
	
	public boolean isConstant(String word){
		return word.matches("[\"'].*[\"']");
	}
	
	public boolean isOperator(String word){
		return operators.contains(word);
	}
	
	public boolean isSymbol(String word){
		return symbols.contains(word);
	}
	
	public boolean isKeyword(String word){
		return keywords.contains(word);
	}
	
	public void dumpToFile(){
		
		try (BufferedWriter bw = new BufferedWriter(new FileWriter("output.txt"))) {

			for(Token token : tokens)
				bw.write(token.toString() + '\n');
							
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
