import java.awt.*;

public class Shape {
    enum type {triangle, circle, quad};
    private Color color;
    private Color fillColor;
    private double lineWidth;
    private double posX;
    private double posY;
    private double scaleX;
    private double scaleY;
    private double rotation;
    private type content = null;

    //constructor
    public Shape(Color color, Color fillColor, double lineWidth, double posX, double posY, double scaleX, double scaleY, double rotation) {
        this.color = color;
        this.fillColor = fillColor;
        this.lineWidth = lineWidth;
        this.posX = posX;
        this.posY = posY;
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        this.rotation = rotation;
    }

    //getter, setter Content
    public void setContent(type content) {
        this.content = content;
    }
    public type getContent() {
        return content;
    }

    /**
     * convert the class to a json object
     * @return json object
     */
    public String toJson() {
        String json = "{\n" +
                "  \"type\": \"" + content + "\",\n" +
                "  \"color\": \"" + color + "\",\n" +
                "  \"fillColor\": \"" + fillColor + "\",\n" +
                "  \"lineWidth\": " + lineWidth + ",\n" +
                "  \"posX\": " + posX + ",\n" +
                "  \"posY\": " + posY + ",\n" +
                "  \"scaleX\": " + scaleX + ",\n" +
                "  \"scaleY\": " + scaleY + ",\n" +
                "  \"rotation\": " + rotation + "\n" +
                "}";
        return json;
    }
}
