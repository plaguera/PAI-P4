package lexical_analysis;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TestLexicalAnalysis {
	
	private LexicalAnalysis analyzer;
	private Token token;

	@Before
	public void setUp() {
		analyzer = new LexicalAnalysis();
		token = new Token();
	}

	@Test
	public void tokenizerTest() {
		String aux = "";
		aux = analyzer.tokenizeString("public static void main(String[] args) {").values().toString();
		assertEquals(aux, "[public, static, void, main, (, String, [, ], args, ), {]");
		
		//Operators
		aux = analyzer.tokenizeString("4+708-23.495,-432.54").values().toString();
		assertEquals(aux, "[4, +, 708, -, 23.495, ,, -432.54]");
		
		aux = analyzer.tokenizeString("-46.9*-90.1").values().toString();
		assertEquals(aux, "[-46.9, *, -90.1]");
		
		aux = analyzer.tokenizeString("2+3-9+=dia-=-70.6").values().toString();
		assertEquals(aux, "[2, +, 3, -, 9, +=, dia, -=, -70.6]");
		
		aux = analyzer.tokenizeString("2+3+dia+9 + 3 9 + 1++").values().toString();
		assertEquals(aux, "[2, +, 3, +, dia, +, 9, +, 3, 9, +, 1, ++]");
		
		aux = analyzer.tokenizeString("45E9 3E-92-45.4E2+-37.02494E-102").values().toString();
		assertEquals(aux, "[45E9, 3E-92, -, 45.4E2, +, -37.02494E-102]");
		
		aux = analyzer.tokenizeString("a==b\" 1<=b \"3>4 9&=a").values().toString();
		assertEquals(aux, "[a, ==, b, \" 1<=b \", 3, >, 4, 9, &=, a]");
		
		aux = analyzer.tokenizeString("4+1 15, -3+-2 9 + -3").values().toString();
		assertEquals(aux, "[4, +, 1, 15, ,, -3, +, -2, 9, +, -3]");
		
		//Comments
		aux = analyzer.tokenizeString("/*HOLAMUNDO*/").values().toString();
		assertEquals(aux, "[]");
		
		aux = analyzer.tokenizeString("13-var//HOLAMUNDO").values().toString();
		assertEquals(aux, "[13, -, var]");
		
		aux = analyzer.tokenizeString("/*HOLAMUNDO*/13-var").values().toString();
		assertEquals(aux, "[13, -, var]");
		
		aux = analyzer.tokenizeString("13-var/*HOLMUNDO*/").values().toString();
		assertEquals(aux, "[13, -, var]");
		
		
	}
	
	@Test
	public void parseTest(){
		token = analyzer.parseToken("public");
		assertEquals(token.getType(), "KWPUBLIC");
		
		token = analyzer.parseToken("-267E-854");
		assertEquals(token.getType(), "INT");
		
		token = analyzer.parseToken("-267.0394E-854");
		assertEquals(token.getType(), "FLOATING");
		
		token = analyzer.parseToken("+=");
		assertEquals(token.getType(), "OPERATOR");
		
		token = analyzer.parseToken("mAin10_3hola");
		assertEquals(token.getType(), "ID");
		
		token = analyzer.parseToken("\"HOLA MUNDO\"");
		assertEquals(token.getType(), "CONSTANT");
		
		token = analyzer.parseToken("{");
		assertEquals(token.getType(), "OBRACE");
		
		token = analyzer.parseToken("?");
		assertEquals(token.getType(), "TOKEN_ERROR");
	}

}
