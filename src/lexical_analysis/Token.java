package lexical_analysis;

public class Token {

	private String name;
	private String lexema;
	private int line;
	private int column;
	
	public Token(String name_p, String lexema_p, int line_p, int column_p){
		name = name_p;
		lexema = lexema_p;
		line = line_p;
		column = column_p;
	}
	
	public Token(){
		this("", "", -1, -1);
	}
	
	public String getName(){
		return name;
	}
	
	public String getLexema(){
		return lexema;
	}
	
	public int getLine(){
		return line;
	}
	
	public int getColumn(){
		return column;
	}
	
	public String toString(){
		return line + "\t" + column + "\t" + name + "\t" + lexema;
	}
	
}
