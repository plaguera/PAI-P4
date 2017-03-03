package lexical_analysis;

public class ALexico {

	public static void main(String[] args) {
		
		if(args.length == 1){
			LexicalAnalysis a = new LexicalAnalysis(args[0]);
			a.dumpToFile();
		}
		
		else
			System.out.println("java ALexico <ruta_archivo>");
		
	}
}
