package lexical_analysis;

public class Token {

	private String name;
	private String lexema;
	private int line;
	private int column;
	
	public Token(){
		this("", "", -1, -1);
	}
	
	public Token(String name_p, String lexema_p, int line_p, int column_p){
		name = name_p;
		lexema = lexema_p;
		line = line_p;
		column = column_p;
	}
	
	/*public String getName(){
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
	}*/
	
	public String toString(){
		return line + " " + column + " " + name + " " + lexema; // return hola;
	}
	public int fun(int a,char b,int c){
		System.out.println("HOLA MUNDO");
		return fun(a++,b--,32-24503);
	}
	
}
