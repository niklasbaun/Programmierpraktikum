package jsonifier;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
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
						"draw circle #000000FF #00FFFFAA 2 20 20 10 20 0\n" +
						"if num > 5\n" +
						"then\n"+
						"jmp testlabel"
				;
		String test1 = "draw circle #000000ff #00ffffaa 2 20 20 10 20 0\n" +
				"draw triangle #000000ff #f0daaaaa 2 20 20 10 20 90\n" +
				"draw quad #000000ff #00ffffaa 2 20 20 10 20 90\n";

	    ArrayList<Token> tokenlistTest1 = translator.tokenizeProgram(test1);
		System.out.println(tokenlistTest1);
		String json = Interpreter.interpret(tokenlistTest1);
	    System.out.println(json);
		//save the json string to a file
		new File("D:\\Marburg\\Sem02\\Programmierpraktikum\\Code\\Programmiersprachen\\test.json").createNewFile();
		BufferedWriter writer = new BufferedWriter(new FileWriter("D:\\Marburg\\Sem02\\Programmierpraktikum\\Code\\Programmiersprachen\\test.json"));
		writer.write(json);
		writer.close();
		/**
		String test2 = "var lineColor = #000000ff\n" +
				"var degree = 0\n" +
				"draw circle lineColor #00ffffaa 2 20 20 10 20 degree\n" +
				"var degree = 90\n" +
				"draw triangle lineColor #f0daaaaa 2 20 20 10 20 degree\n" +
				"draw quad lineColor #00ffffaa 2 20 20 10 20 degree";
		ArrayList<Token> tokenlistTest2 = translator.tokenizeProgram(test2);
		String json2 = Interpreter.interpret(tokenlistTest2);
		System.out.println(json2);
		//save the json string to a file
		new File("D:\\Marburg\\Sem02\\Programmierpraktikum\\Code\\Programmierpsrachen\\test2.json").createNewFile();
		BufferedWriter writer2 = new BufferedWriter(new FileWriter("D:\\Marburg\\Sem02\\Programmierpraktikum\\Code\\Programmierpsrachen\\test2.json"));
		writer2.write(json2);
		writer2.close();
		 **/
	}
}
