import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Item {
    boolean collected = false;

    BoundingBox boundingBox;
    public static ArrayList<BufferedImage> images = new ArrayList<>();

    public int imageIndex;
    int aniCounter = 0;

    public Item(BoundingBox boundingBox, int i) {
        this.boundingBox = boundingBox;
        this.imageIndex = i;
    }

    public void update() {
        getItemImage();
    }

    /**
     * get the current image of the item
     */
    private void getItemImage() {
        getNextImage();
    }

    /**
     * get the next image of the item (for animation)
     * @return image
     */
    private BufferedImage getNextImage() {
        if(!collected){
            aniCounter++;
            return images.get(aniCounter % images.size());
        } else {
            return null;
        }
    }
}
