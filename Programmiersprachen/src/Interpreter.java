import jsonifier.Token;
import jsonifier.TokenType;

import java.util.List;

public class Interpreter {

    /**
     * interpret the tokens gotten from json and work with them
     * @param tokens list of tokens
     * @return json string
     */
    public String interpret(List<Token> tokens){
        //iterate through token list (10 tokens belong together)
        String json = "";
        for(int i = 0; i < tokens.size(); i+= 10){
            //look in 1st for keyword
            if(tokens.get(i).getTokenType() == TokenType.KEYWORD && tokens.get(i).getContent() == "draw"){
                //add draw to json String
                json += "draw, ";
            }
            //color
            if (tokens.get(i+1).getTokenType() == TokenType.RGBA){
                //add color to json String
                json += "color: " + tokens.get(i+1).getContent() + ", ";
            }
            //fillcolor
            if (tokens.get(i+2).getTokenType() == TokenType.RGBA){
                //add fillColor to json String
                json += "fillColor: " + tokens.get(i+2).getContent() + ", ";
            }
            //lineWidth
            if (tokens.get(i+3).getTokenType() == TokenType.NUMBER){
                //add lineWidth to json String
                json += "lineWidth: " + tokens.get(i+3).getContent() + ", ";
            }
            //posX
            if (tokens.get(i+4).getTokenType() == TokenType.NUMBER){
                //add posX to json String
                json += "posX: " + tokens.get(i+4).getContent() + ", ";
            }
            //posY
            if (tokens.get(i+5).getTokenType() == TokenType.NUMBER){
                //add posY to json String
                json += "posY: " + tokens.get(i+5).getContent() + ", ";
            }
            //scaleX
            if (tokens.get(i+6).getTokenType() == TokenType.NUMBER){
                //add scaleX to json String
                json += "scaleX: " + tokens.get(i+6).getContent() + ", ";
            }
            //scaleY
            if (tokens.get(i+7).getTokenType() == TokenType.NUMBER){
                //add scaleY to json String
                json += "scaleY: " + tokens.get(i+7).getContent() + ", ";
            }
            //rotation
            if (tokens.get(i+8).getTokenType() == TokenType.NUMBER){
                //add rotation to json String
                json += "rotation: " + tokens.get(i+8).getContent() + ", ";
            }
            //content
            if (tokens.get(i+9).getTokenType() == TokenType.SHAPETYPE){
                //add content to json String
                json += "content: " + tokens.get(i+9).getContent() + ", ";
            }
            else {
                //overwrite json with whole error
                json = "Error, something went wrong";
            }
        }
        return json;
    }

}
