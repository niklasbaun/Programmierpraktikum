package jsonifier;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class Main {

	public static void main(String[] args) throws URISyntaxException, IOException {
		Tokenizer translator = new Tokenizer();
		String data =
				"var bool = false\n"+
						"var num = 0\n"+
						"testlabel: var num = num + 1\n"+
						"draw circle #000000FF #00FFFFAA 2 20 20 10 20 0\n"+
						"if num > 5\n" +
						"then\n"+
						"jmp testlabel"
				;
	    ArrayList<Token> tokenlist = translator.tokenizeProgram(data);

	    System.out.println(tokenlist);


	}
}
