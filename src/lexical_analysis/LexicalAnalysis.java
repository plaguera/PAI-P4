package lexical_analysis;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LexicalAnalysis {
	
	private List<Token> 			tokens;
	private List<String> 			keywords;
	private List<Character> 		symbols;
	private List<String> 			operators;
	private Map<Character, String> 	symbolTable;
	
	private boolean comment;
	
	public LexicalAnalysis(String filePath){
		tokens = new ArrayList<Token>();
		keywords = Arrays.asList(	"abstract", "continue", "for", 			"new", 			"switch",
									"assert", 	"default", 	"goto", 		"package", 		"synchronized",
									"boolean", 	"do", 		"if", 			"private", 		"this",
									"break", 	"double", 	"implements", 	"protected",	"throw",
									"byte", 	"else", 	"import", 		"public", 		"throws",
									"case", 	"enum", 	"instanceof",	"return", 		"transient",
									"catch", 	"extends", 	"int", 			"short", 		"try",
									"char", 	"final", 	"interface", 	"static", 		"void",
									"class", 	"finally", 	"long", 		"strictfp", 	"volatile",
									"const", 	"float", 	"native", 		"super", 		"while");
		symbols = Arrays.asList(	'(', ')','[',']',',','.',';',':','{','}');
		operators = Arrays.asList(	"=", "+", "-", "*", "/", "%", "++", "--", "!", "==", "!=", ">", ">=", "<", "<=", "&&", "||", "~", "<<", ">>", ">>>", "&", "^", "|");
		symbolTable = new HashMap<Character, String>() {{
            put('(', "OPAR");
            put(')', "CPAR");
            put('[', "OBRACKET");
            put(']', "CBRACKET");
            put(',', "COMMA");
            put('.', "DOT");
            put(';', "SEMICOLON");
            put(':', "COLON");
            put('{', "OBRACE");
            put('}', "CBRACE");
        }};
        comment = false;
		fileToString(filePath);
		
	}
	
	public void fileToString(String filePath){
		
		int lineNumber = 0;
		int columnNumber = 1;
		
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

			String cl;
			
			while ((cl = br.readLine()) != null) {
				
				lineNumber++;
				
				if(cl.contains("*/"))
					comment = false;
				
				if(!comment)
					for(String i : tokenizeString(cl))
						tokens.add(parseWord(i, lineNumber, 0));
					
				
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Token parseWord(String word, int line, int column){
		Token newToken = new Token();
		
		//System.out.println("WORD -> " + word);
		
		if(word.matches("[a-z]+") && keywords.contains(word)){
			newToken = new Token("KW" + word.toUpperCase(), word, line, column);
		}
		
		else if(word.matches("-?[0-9]+")){
			newToken = new Token("INT", word, line, column);
		}
		
		else if(word.matches("-?[0-9]+.[0-9]+")){
			newToken = new Token("FLOATING", word, line, column);
		}
		
		else if(word.matches("[a-zA-Z][a-zA-Z_0-9]*")){
			newToken = new Token("ID", word, line, column);
		}
		
		else if(symbols.contains(word.charAt(0)) && word.length() == 1){
			newToken = new Token(symbolTable.get(word.charAt(0)), word, line, column);
		}
		
		else if(operators.contains(word)){
			newToken = new Token("OPERATOR", word, line, column);
		}
		
		else if(word.matches("\".*\""))
			newToken = new Token("CONSTANT", word, line, column);
		
		else if(word.matches("\'.*\'"))
			newToken = new Token("CONSTANT", word, line, column);
		
		else
			newToken = new Token("TOKEN", "ERROR", line, column);
		
		System.out.println(newToken);
		
		return newToken;
	}
	
	public List<String> tokenizeString(String word){
		
		List<String> tokenArray = new ArrayList<String>();
		
		if(isWordParsed(word)){
			tokenArray.add(word);
			return tokenArray;
		}
							
		for(int i = 0; i < word.length(); i++){
			//System.out.println("CHECK SYMBOL ->" + word.charAt(i));
			
			char 	currentChar 	= word.charAt(i);
			String	currentChar_s	= Character.toString(currentChar);
			
			if(i < word.length()-1 && currentChar == '/' && word.charAt(i+1) == '*'){
				comment = true;
				return tokenArray;
			}
			
			else if(i < word.length()-1 && currentChar == '*' && word.charAt(i+1) == '/')
				tokenArray.clear();
			
			else if(i < word.length()-1 && currentChar == '/' && word.charAt(i+1) == '/')
				return tokenArray;
			
			else if(symbols.contains(currentChar))
				tokenArray.add(currentChar_s);
			
			else if(operators.contains(currentChar_s)){
				
				if(currentChar == '-' && word.substring(0, i).matches(".*[^a-zA-Z_0-9][\\s]*") && word.substring(i).matches("-[0-9]+.*")){
					String auxNumber = "";
					do {
						
						currentChar = word.charAt(i);
						auxNumber += currentChar;
						i++;
						
					} while(i < word.length() && Character.isDigit(word.charAt(i)));
					tokenArray.add(auxNumber);
					i--;
				}
				else if(i < word.length()-1){
					String nextChar_s = Character.toString(word.charAt(i+1));
					String doubleOperator = currentChar_s + nextChar_s;
					if(operators.contains(doubleOperator)){
						tokenArray.add(doubleOperator);
						i++;
					}
					else{
						tokenArray.add(currentChar_s);
					}
				}
				
			}
			
			else if(Character.isDigit(currentChar)){
				String auxNumber = "";
				while(i < word.length() && (Character.isDigit(word.charAt(i)) || currentChar == '.')) {
					
					currentChar = word.charAt(i);
					auxNumber += currentChar;
					i++;
					
				}
				tokenArray.add(auxNumber);
				i--;
			}
			
			else if(currentChar == '"'){
				String auxString = "" + currentChar;
				i++;
				while(i < word.length() && word.charAt(i) != '\"') {
					currentChar = word.charAt(i);
					auxString += currentChar;
					i++;
							
				}
				tokenArray.add(auxString +  "\"");
			}
			
			else if(currentChar == '\''){
				String auxString = "" + currentChar;
				i++;
				while(i < word.length() && word.charAt(i) != '\'') {
					currentChar = word.charAt(i);
					auxString += currentChar;
					i++;
							
				}
				tokenArray.add(auxString +  "\'");
			}

			else if(Character.isLetter(currentChar)){
				String auxString = "";
				while(i < word.length() && (Character.isLetter(word.charAt(i)) || word.charAt(i) == '_')) {
					
					currentChar = word.charAt(i);
					auxString += currentChar;
					i++;
							
				}	
				i--;
				tokenArray.add(auxString);
			}
			
			
				
		}
		return tokenArray;
	}
	
	public boolean isWordParsed(String word){
		return 	word.matches("-?[0-9]+") 	|| word.matches("-?[0-9]+.[0-9]+") ||
				symbols.contains(word) 		|| operators.contains(word)  ||
				keywords.contains(word) 	|| word.matches("[a-zA-Z][a-zA-Z_0-9]*") ||
				word.matches("\".*\"") 		|| word.matches("\'.*\'");
	}

}
