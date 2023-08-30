package jsonifierLsg;

import java.io.*;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class Main {
	private static final String FILEPATH = "D:\\Marburg\\Sem02\\Programmierpraktikum\\Code\\ProgrammiersprachenLsg";
	private static final String FILENAME = "expressions";

	public static void main(String[] args) throws URISyntaxException, IOException {
		Tokenizer translator = new Tokenizer();
		String data = "";
		BufferedReader reader = new BufferedReader(new FileReader(new File(FILEPATH+ "\\"+FILENAME+".txt")));
		String line;
		while((line = reader.readLine()) != null){
			data += line + "\n";
		}
	    ArrayList<Token> tokenlist = translator.tokenizeProgram(data);
		Interpreter interpreter = new Interpreter();
		interpreter.interpret(tokenlist);
		String json = interpreter.getShape().toJson();
		BufferedWriter writer = new BufferedWriter(new FileWriter(new File(FILEPATH+File.pathSeparator+FILENAME+".json")));
		writer.write(json);
		writer.flush();
		writer.close();
	}
}
