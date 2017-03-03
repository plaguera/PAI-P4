package lexical_analysis;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.LinkedHashMap;

public class LexicalAnalysis {
	
	private List<Token>	tokens;
	private Keywords	keywords;
	private Symbols		symbols;
	private Operators	operators;
	
	Pattern tokenizerPattern;
	Matcher matcher;
	
	private boolean comment;
	private int eof;
	
	public LexicalAnalysis(){
		tokens = new ArrayList<Token>();
		
		tokenizerPattern = Pattern.compile("([/][*])|([*][/])|(//).*|"
				+ "[\"'].*[\"']|"
				+ "(?<![a-zA-Z_0-9])[-]?[\\s]*[0-9]+(\\.[0-9]+)?(E[-]?[0-9]+)?|"
		 		+ "(\\+\\+)|--|===|==|\\+=|-=|\\*=|/=|%=|<<=|>>=|&=|^=|\\|=|!=|>=|<=|&&|\\|\\||<<|>>>|>>|"
		 		+ "[=+*/%!><~^&|\\-]|"
		 		+ "[(){}:;.,\\[\\]]|"
		 		+ "[a-zA-Z_0-9]+");
		
		keywords = new Keywords();
		symbols = new Symbols();
		operators = new Operators();
        comment = false;
		
	}
	
	public LexicalAnalysis(String filePath){
		this();
		analyzeFile(filePath);
	}
	
	/**
	 * Lee los datos del archivo filePath, y los tokeniza y analiza línea a línea. Luego almacena los tokens resultantes en (tokens)
	 * @param filePath
	 */
	public void analyzeFile(String filePath){
		
		int lineNumber = 0;
		
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

			String currentLine;
			
			while ((currentLine = br.readLine()) != null) {
				
				lineNumber++;
				
				for(Map.Entry<Integer, String> entry : tokenizeString(currentLine).entrySet())
					if(!isComment(entry.getValue()) && !comment)
						tokens.add(parseWord(entry.getValue(), lineNumber, entry.getKey()+1));
					
				tokens.add(new Token("EOF", "", lineNumber, eof));
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Recoge un string tokenizado y lo clasifica según el tipo al que corresponde
	 * @param word		string tokenizado
	 * @param line		donde se encontró
	 * @param column	donde se encontró
	 * @return
	 */
	public Token parseWord(String word, int line, int column){
		Token newToken = new Token();
		
		if(isKeyword(word))
			newToken = new Token(keywords.get(word), word, line, column);
				
		else if(isInt(word))
			newToken = new Token("INT", word, line, column);
		
		else if(isFloat(word))
			newToken = new Token("FLOATING", word, line, column);
		
		else if(isID(word))
			newToken = new Token("ID", word, line, column);
		
		else if(isSymbol(word))
			newToken = new Token(symbols.get(word), word, line, column);
		
		else if(isOperator(word))
			newToken = new Token("OPERATOR", word, line, column);
		
		else if(isConstant(word))
			newToken = new Token("CONSTANT", word, line, column);
		
		else
			newToken = new Token("TOKEN_ERROR", word, line, column);
		
		//System.out.println(newToken);
		
		return newToken;
	}
	
	/**
	 * Versión de parseToken para usar en los test, ya que facilita la lectura de las pruebas
	 * @param word
	 * @return
	 */
	public Token parseToken(String word){
		return parseWord(word, 0, 0);
	}
	
	/**
	 * Mediante el uso de expresiones regulares el string (word) se divide en tokens y se almacenan en un mapa
	 * si no se encuentra dentro de un comentario
	 * @param word línea de texto a tokenizar
	 * @return mapa cuyas claves son el número de columna de cada token y cuyo valor es el token
	 */
	public Map<Integer, String> tokenizeString(String word){
		
		Map<Integer, String> tokenColumn = new LinkedHashMap<Integer, String>();
		
		matcher = tokenizerPattern.matcher(word);

		while (matcher.find()){
			String token = matcher.group().trim();
			if(token.equals("/*"))
				comment = true;
			else if(token.equals("*/") && comment)
				comment = false;
			else if(!comment && !isComment(token))
				tokenColumn.put(matcher.start(), token);
			eof = matcher.end();
		}
				
		return tokenColumn;
	}
	
	/**
	 * Comprueba si el string (word) es un número entero
	 * @param word
	 * @return
	 */
	public boolean isInt(String word){
		return word.matches("-?[0-9]+(E[-]?[0-9]+)?");
	}
	
	/**
	 * Comprueba si el string (word) es un número de punto flotante
	 * @param word
	 * @return
	 */
	public boolean isFloat(String word){
		return word.matches("-?[0-9]+.[0-9]+(E[-]?[0-9]+)?");
	}
	
	/**
	 * Comprueba si el string (word) es un identificador
	 * @param word
	 * @return
	 */
	public boolean isID(String word){
		return word.matches("[a-zA-Z][a-zA-Z_0-9]*");
	}
	
	/**
	 * Comprueba si el string (word) es una constante ("..."), ('...')
	 * @param word
	 * @return
	 */
	public boolean isConstant(String word){
		return word.matches("[\"'].*[\"']");
	}
	
	/**
	 * Comprueba si el string (word) es un operador
	 * @param word
	 * @return
	 */
	public boolean isOperator(String word){
		return operators.contains(word);
	}
	
	/**
	 * Comprueba si el string (word) es un símbolo
	 * @param word
	 * @return
	 */
	public boolean isSymbol(String word){
		return symbols.contains(word);
	}
	
	/**
	 * Comprueba si el string (word) es una palabra clave
	 * @param word
	 * @return
	 */
	public boolean isKeyword(String word){
		return keywords.contains(word);
	}
	
	/**
	 * Comprueba si el string (word) es un comentario
	 * @param word
	 * @return
	 */
	public boolean isComment(String word){
		return word.matches("(//).*");
	}
	
	/**
	 * Imprime todos los token analizados en output.txt dentro del workspace, se debe ejecutar después de analyzeFile
	 */
	public void dumpToFile(){
		
		try (BufferedWriter bw = new BufferedWriter(new FileWriter("output.txt"))) {

			for(Token token : tokens)
				bw.write(token.toString() + '\n');
							
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
