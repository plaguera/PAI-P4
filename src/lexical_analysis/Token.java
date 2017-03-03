package lexical_analysis;

/**
 * Clase que representa un token, es decir, una palabra o letra que tiene un determinado tipo (name).
 * @author Pedro Miguel Lagüera Cabrera
 * Mar 3, 2017
 * Token.java
 */
public class Token {

	private String type;
	private String lexema;
	private int line;
	private int column;
	
	public Token(String name_p, String lexema_p, int line_p, int column_p){
		type = name_p;
		lexema = lexema_p;
		line = line_p;
		column = column_p;
	}
	
	public Token(){
		this("", "", -1, -1);
	}
	
	/**
	 * @return the name
	 */
	public String getType() {
		return type;
	}

	/**
	 * @return the lexema
	 */
	public String getLexema() {
		return lexema;
	}

	/**
	 * @return the line
	 */
	public int getLine() {
		return line;
	}

	/**
	 * @return the column
	 */
	public int getColumn() {
		return column;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.type = name;
	}

	/**
	 * @param lexema the lexema to set
	 */
	public void setLexema(String lexema) {
		this.lexema = lexema;
	}

	/**
	 * @param line the line to set
	 */
	public void setLine(int line) {
		this.line = line;
	}

	/**
	 * @param column the column to set
	 */
	public void setColumn(int column) {
		this.column = column;
	}
	
	/**
	 * @return a string representation of the value of this object
	 */
	public String toString(){
		return line + "\t" + column + "\t" + type + "\t" + lexema;
	}
	
}
