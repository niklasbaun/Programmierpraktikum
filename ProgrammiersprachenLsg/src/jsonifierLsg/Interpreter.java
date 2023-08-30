package jsonifierLsg;

import java.util.*;

public class Interpreter {
	private Shape shape;
	private Map<String, Object> variables = new HashMap<>();
	private Map<String, Integer> jumpMarks = new HashMap<>();
	List<List<Token>> lines = new ArrayList<>();


	Shape getShape(){
		return shape;
	}

	/**
	 * interpret the tokens gotten from text file and work with them
	 * @param tokens list of tokens
	 * @return json string
	 */

	String interpret(List<Token> tokens){
		//split tokens into lines
		List<Token> redLine = new ArrayList<>();
		for(Token token : tokens){
			//split at linebreak
			if(token.type==TokenType.LINEBREAK&&!redLine.isEmpty()){
				lines.add(redLine);
				redLine = new ArrayList<>();
			}
			else{
				redLine.add(token);
			}
		}
		if(!redLine.isEmpty()){
			lines.add(redLine);
		}
		//add then, else and fi to jump marks
for(int i = 0; i < lines.size(); i++){
			Token firstToken = lines.get(i).get(0);
			List<Token> restList = lines.get(i).subList(1, lines.get(i).size());
			if(firstToken.getTokenType().equals(TokenType.KEYWORD)){
				switch(firstToken.getContent().toString()){
					case "then" -> {
						jumpMarks.put("then", i);
					}
					case "else" -> {
						jumpMarks.put("else", i);
					}
					case "fi" -> {
						jumpMarks.put("fi", i);
					}
				}
			}
		}
		//variable for bool expression
		Boolean bool = false;
		Boolean thenB = false;
		//iterate over lines
		for(int i = 0; i < lines.size(); i++){
			Token firstToken = lines.get(i).get(0);
			List<Token> restList = lines.get(i).subList(1, lines.get(i).size());
			//check what the keyword is or what type it is
			switch(firstToken.getTokenType()){
				//if keyword, do what is asked
				case KEYWORD -> {
					switch(firstToken.getContent().toString()){
						case "draw" ->{
							this.parseDraw(restList);
						}
						case "var" ->{
							this.parseVar(restList);
						}
						//if keyword is "if"
						case "if" ->{
							//check specified var
							bool = boolValue(restList);

						}
						//if keyword "then" or "else" do whatever is
						case "then", "else" -> {
							//if true and then -> do what is asked
							if(bool && Objects.equals(firstToken.getContent().toString(), "then")){
								//set then to true
								thenB = true;
								//jump to then
								continue;
							//if it is else block
							} else if (!bool && Objects.equals(firstToken.getContent().toString(), "else")){
								//do stuff in "else" block
								//goto next line
								continue;
							//else if then block is through and at else statement jump to fi
							} else if (thenB && Objects.equals(firstToken.getContent().toString(), "else")){
								//goto "fi"
								Integer lineNum = jumpMarks.get("fi");
								i = lineNum;
							//else if else block is wanted and at then jump to else
							} else if (!thenB && Objects.equals(firstToken.getContent().toString(), "then")){
								//goto "else"
								if(jumpMarks.containsKey("else")){
									Integer lineNum = jumpMarks.get("else");
									i = lineNum;
								} else {
									Integer lineNum = jumpMarks.get("fi");
									i = lineNum;
								}
							}
						}
						case "fi" -> {
							//do stuff in "fi" block
							bool = false;
							thenB = false;
							//goto next line
							continue;
						}
						case "jmp" ->{
							//reset bools
							bool = false;
							thenB = false;
							//check jump mark
							Integer lineNum = jumpMarks.get(restList.get(0).getContent().toString());
							//jump to line
							if(lineNum!=null){
								i = lineNum;
							}
						}
					};
				}
				//if it is a special char or a symbol, do what is asked
				case CHARSPECIAL -> {

				}
				case SYMBOLCPD -> {
				}
				case LABEL -> {
					//give name and line num
					setJumpMarks(firstToken, i);
				}
				default -> {}
			}
		}
		System.out.println(jumpMarks);

		if(shape!=null){
			return shape.toJson();
		}
		return "";
	}

	/**
	 * method to set jump marks
	 * @param label name of token
	 */
	void setJumpMarks(Token label, int lineNum){
		//add line number and element to map
		//remove :
		String newLbl = label.getContent().toString().replace(":", "");
		jumpMarks.put(newLbl, lineNum);
	}

	/**
	 * check for bool var and return what value it has
	 * @param restList list with statements
	 * @return bool true, false
	 */
	boolean boolValue(List<Token> restList){
		//check if the value is directly true or false
		if(Objects.equals(restList.get(0).getContent().toString(), "true") && restList.get(0).getTokenType() == TokenType.BOOLEAN){
			return true;
		} else if (Objects.equals(restList.get(0).getContent().toString(), "false") && restList.get(0).getTokenType() == TokenType.BOOLEAN){
			return false;
		}
		//check if it is a bool expression
		if(listContainsExpression(restList) && parseArithExpression(restList) instanceof Boolean){
			return (Boolean) parseArithExpression(restList);
		}
		//check if it is a bool in the list is true or false
		Object expression = variables.get(restList.get(0).getContent().toString());
		if(expression instanceof Boolean){
			return (Boolean) expression;
		}
		return false;
	}

	/**
	 * check if a list of tokens contains an expression
	 * @param tokens list of tokens
	 * @return true if list contains expression
	 */
	boolean listContainsExpression(List<Token> tokens){
		for(Token token : tokens){
			//check if a token is a special char or a symbol
			if(token.getTokenType().equals(TokenType.CHARSPECIAL) || token.getTokenType().equals(TokenType.SYMBOLCPD)){
				return true;
			}
		}
		return false;
	}

	/**
	 * parse an expression (arithmetic)
	 * @param tokens list of tokens
	 * @return result of expression
	 */
	Object parseArithExpression(List<Token> tokens){
		//create new list
		List<Token> newTokens = new ArrayList<>();
		//check for "not" operator
		for (int i = 0; i < tokens.size(); i++) {
			//if one not is found, check if there are more behind it
			//if not a not operator add to a new list
			if(tokens.get(i).getContent().toString().equals("¬")){
				//check for more not operators
				//count them and check if they are even or odd
				int count = 0;
				for (int j = i; j < tokens.size(); j++) {
					if(tokens.get(j).getContent().toString().equals("¬")){
						count++;
					}
				}
				if (count%2==0){
					//if even at the bool which is behind the not operator
					newTokens.add(tokens.get(i+count));
				} else {
					//if odd number of not operators add negated operator
					newTokens.add(tokens.get(i+count).getContent().toString().equals("true") ? new Token("false", TokenType.BOOLEAN) : new Token("true", TokenType.BOOLEAN));
				}
				//skip to after counted not operators and bool
				i = i+count;
			//if not a not add to a new list
			} else if (!tokens.get(i).getContent().toString().equals("¬")){
				newTokens.add(tokens.get(i));
			}
		}
		//if content is only one object return this
		if(newTokens.size()==1){
			return parseIdentifier(newTokens.get(0));
		}
		Object value2 = parseIdentifier(newTokens.get(newTokens.size()-1));
		Object value1 = parseArithExpression(newTokens.subList(0, newTokens.size()-2));
		String operator = newTokens.get(newTokens.size()-2).getContent().toString();
		//arithmetical expression
		//add the two operands
		if(operator.equals("+")){
			return Integer.parseInt(value1.toString()) + Integer.parseInt(value2.toString());
		}
		//subtract two operands
		if(operator.equals("-")){
			return Integer.parseInt(value1.toString()) - Integer.parseInt(value2.toString());
		}
		//divide two numbers
		if(operator.equals("/")){
			return Integer.parseInt(value1.toString()) / Integer.parseInt(value2.toString());
		}
		//boolean expression
		//and operand
		if(operator.equals("∧")){
			return Boolean.parseBoolean(value1.toString()) && Boolean.parseBoolean(value2.toString());
		}
		//or, operand
		if(operator.equals("∨")){
			return Boolean.parseBoolean(value1.toString()) || Boolean.parseBoolean(value2.toString());
		}
		//if two is bigger then one
		if (operator.equals("<")){
			return Integer.parseInt(value1.toString()) < Integer.parseInt(value2.toString());
		}
		//if one is bigger then two
		if (operator.equals(">")){
			return Integer.parseInt(value1.toString()) > Integer.parseInt(value2.toString());
		}
		//if two operators equal eachother
		if (operator.equals("=")){
			return Integer.parseInt(value1.toString()) == Integer.parseInt(value2.toString());
		}
		//default answer is multiply
		return Integer.parseInt(value1.toString()) * Integer.parseInt(value2.toString());
	}

	/**
	 * parse a variable
	 * @param tokens list of tokens
	 */
	void parseVar(List<Token> tokens){
		//get the element to which the variable should be assigned
		Object content = tokens.get(2).getContent();
		//check if such an operator is in the sublist
		if(listContainsExpression(tokens.subList(2, tokens.size()))){
			content = parseArithExpression(tokens.subList(2, tokens.size()));
			//is possible to do one after the other?
		}
		variables.put(tokens.get(0).getContent().toString(), content);
	}

	String parseIdentifier(Token token){
		if(token.getTokenType().equals(TokenType.IDENTIFIER)){
			return variables.get(token.getContent().toString()).toString();
		}
		return token.getContent().toString();
	}

	/**
	 * parse a draw statement
	 * @param tokens list of tokens
	 */
	void parseDraw(List<Token> tokens){
		Token type = tokens.get(0);
		String contentType = parseIdentifier(type);
		Token color = tokens.get(1);
		String contentColor = parseIdentifier(color);
		Token fillColor = tokens.get(2);
		String contentFill = parseIdentifier(fillColor);
		Token lineWidth = tokens.get(3);
		Integer contentWidth = Integer.valueOf(parseIdentifier(lineWidth));
		Token posX = tokens.get(4);
		Integer contentX = Integer.valueOf(parseIdentifier(posX));
		Token posY = tokens.get(5);
		Integer contentY = Integer.valueOf(parseIdentifier(posY));
		Token scaleX = tokens.get(6);
		Integer contentScaleX = Integer.valueOf(parseIdentifier(scaleX));
		Token scaleY = tokens.get(7);
		Integer contentScaleY = Integer.valueOf(parseIdentifier(scaleY));
		Token rotation = tokens.get(8);
		Integer contentRot = Integer.valueOf(parseIdentifier(rotation));
		Shape parsedShape = new Shape(Type.valueOf(contentType.toUpperCase()), contentColor, contentFill, contentWidth, contentX, contentY, contentScaleX, contentScaleY, contentRot);
			if (this.shape == null) {
				this.shape = parsedShape;
			}
			else {
				Shape innerMost = this.shape;
				while (innerMost.content != null) {
					innerMost = innerMost.content;
				}
				innerMost.content = parsedShape;
			}
	}
}
