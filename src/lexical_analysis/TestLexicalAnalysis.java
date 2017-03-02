package lexical_analysis;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TestLexicalAnalysis {
	
	LexicalAnalysis analyzer;

	@Before
	public void setUp() {
		analyzer = new LexicalAnalysis();
	}

	@Test
	public void tokenizerTest() {
		String aux = "";
		aux = analyzer.tokenizeString("public static void main(String[] args) {").keySet().toString();
		assertEquals(aux, "[public, static, void, main, (, String, [, ], args, ), {]");
		
		aux = analyzer.tokenizeString("4+708-23.495,-432.54").keySet().toString();
		assertEquals(aux, "[4, +, 708, -, 23.495, ,, -432.54]");
		
		aux = analyzer.tokenizeString("-46.9*-90.1").keySet().toString();
		assertEquals(aux, "[-46.9, *, -90.1]");
		
		aux = analyzer.tokenizeString("4+1 15 + 45, -3+-2 9 + -3").keySet().toString();
		System.out.println(aux);
		assertEquals(aux, "[-46.9, *, -90.1]");
	}

}
