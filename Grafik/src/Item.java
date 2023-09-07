import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Item {

    BoundingBox boundingBox;
    public static ArrayList<BufferedImage> images = new ArrayList<>();

    public int imageIndex;

    public Item(BoundingBox boundingBox, int i) {
        this.boundingBox = boundingBox;
        this.imageIndex = i;
    }
}
