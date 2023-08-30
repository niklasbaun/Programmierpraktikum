package jsonifier;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;
import java.util.*;

import static jsonifier.Shape.type.circle;

public class Interpreter {

    /**
     * interpret the tokens gotten from json and work with them
     *
     * @param tokens list of tokens
     * @return json string
     */
    public static String interpret(List<Token> tokens) {

        //result map
        Map<Integer, List<Token>> results = new HashMap<>();
        //create json string
        String result = "";
        //create a snippet list
        List<Token> snippet = new ArrayList<>();
        for (Token token : tokens) {
            //check if token is linebreak
            if (token.getTokenType() == TokenType.LINEBREAK) {
                //finish snippet and add to map
                results.put(results.size(), snippet);
                //clear snippet
                snippet.clear();
            } else {
                snippet.add(token);
            }
        }
        //list for shape objects
        List<Shape> shapes = new ArrayList<>();
        //foreach snippet in the result map check if it is a draw statement
        for(int i = 0; i < results.size(); i++){
            if (!results.get(i).isEmpty() && Objects.equals(results.get(i).get(0).toString(), "draw") ){
                //get specific tokens and create a shape object
                Shape.type type = null;
                if (Objects.equals(results.get(i).get(1).toString(), "circle")) {
                    type = circle;
                } else if (Objects.equals(results.get(i).get(1).toString(), "triangle")) {
                    type = Shape.type.triangle;
                } else if (Objects.equals(results.get(i).get(1).toString(), "quad")) {
                    type = Shape.type.quad;
                }
                //check if color is valid input
                String trueColor = "";
                String trueFillColor = "";
                //check if rgba value is true
                if(results.get(i).get(2).getTokenType() == TokenType.RGBA){
                    trueColor = results.get(i).get(2).toString();
                }
                if(results.get(i).get(3).getTokenType() == TokenType.RGBA){
                    trueFillColor = results.get(i).get(3).toString();
                }
                Shape shape = new Shape(type,
                        trueColor,
                        trueFillColor,
                        Double.parseDouble(results.get(i).get(4).toString()),
                        Double.parseDouble(results.get(i).get(5).toString()),
                        Double.parseDouble(results.get(i).get(6).toString()),
                        Double.parseDouble(results.get(i).get(7).toString()),
                        Double.parseDouble(results.get(i).get(8).toString()),
                        Double.parseDouble(results.get(i).get(9).toString())
                );
                //shape to shapes
                shapes.add(shape);
            }
        }

        //connect the shapes into each other
        for (Shape shape: shapes){
            //if there is a next shape, set it as content
            if (shapes.indexOf(shape) + 1 != shapes.size()){
                shape.setContent(shapes.get(shapes.indexOf(shape) + 1));
            }
        }
        return shapes.get(0).toJson();
    }
}
