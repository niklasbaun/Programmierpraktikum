package jsonifier;
import com.google.gson.FieldNamingStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Field;

public class Shape {
    enum type {triangle, circle, quad};
    private type type;
    private String color;
    private String fillColor;
    private double lineWidth;
    private double posX;
    private double posY;
    private double scaleX;
    private double scaleY;
    private double rotation;
    private Shape content = null;

    //constructor
    public Shape(type type, String color, String fillColor, double lineWidth, double posX, double posY, double scaleX, double scaleY, double rotation) {
        this.type = type;
        //check if color string starts with #
        if (color.charAt(0) == '#') {
            this.color = color;
        } else {
            System.err.println("No valid Color");
            throw new IllegalArgumentException();
        }
        //check if fillColor string starts with #
        if (fillColor.charAt(0) == '#') {
            this.fillColor = fillColor;
        } else {
            System.err.println("No valid Color");
            throw new IllegalArgumentException();
        }
        this.lineWidth = lineWidth;
        this.posX = posX;
        this.posY = posY;
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        this.rotation = rotation;
    }

    //getter, setter Content
    public void setContent(Shape content) {
        this.content = content;
    }
    public Shape getContent() {
        return content;
    }

    /**
     * convert the class to a json object
     * @return json object
     */
    public String toJson() {
        Gson builder = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        builder.toJson(this);
        return builder.toJson(this);
    }
}
