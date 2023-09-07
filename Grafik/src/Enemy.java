import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Enemy {
    boolean collides, isCollisionTop, isCollisionBottom, isCollisionLeft, isCollisionRight = false;
    boolean isFacingLeft = false;

    float movementSpeed;

    BoundingBox boundingBox;
    int lifes = 1;

    public static ArrayList<BufferedImage> images = new ArrayList<>();

    public int imageIndex;
    int aniCounter = 0;
    Level l;

    Vec2 gravity;

    public Enemy(BoundingBox boundingBox, int i, int lifes, float movementSpeed, Level l) {
        this.boundingBox = boundingBox;
        this.imageIndex = i;
        this.lifes = lifes;
        this.movementSpeed = movementSpeed;
        this.l = l;
        this.gravity = new Vec2(0, 1.5f);

        //load images

    }

    public void update() {
        //apply gravity
        boundingBox.min = boundingBox.min.add(gravity);
        movement();
    }

    /**
     * movement of the enemy
     * moves on the x-axis on a platform from end to end
     */
    private void movement() {
        //move 3 tiles to the right
        int i = 0;
        while (i < 210) {
            boundingBox.min.x += movementSpeed;
            i += movementSpeed;
            isFacingLeft = false;
        } //turn around and move 3 to the left
        while (i > 0) {
            boundingBox.min.x -= movementSpeed;
            i -= movementSpeed;
            isFacingLeft = true;
        }
        getEnemyImage();
    }


    /**
     * get the current image of the enemy
     */
    private void getEnemyImage() {
        BufferedImage b = getNextImage();
        if (isFacingLeft) {
            AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
            tx.translate(-b.getWidth(null), 0);
            AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
            b = op.filter(b, null);
        }
        getNextImage();
    }

    /**
     * get the next image of the enemy (for animation)
     *
     * @return image
     */
    private BufferedImage getNextImage() {
        aniCounter++;
        //if the player jumps on top of the enemy, the enemy dies
        if (isCollisionTop) {
            lifes -= 1;
        }
        if (lifes <= 0) {
            aniCounter = 0;
            //show dead image
            return images.get(0);

        } else {
            return images.get(aniCounter % images.size());
        }
    }
}
