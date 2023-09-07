import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Player {
    boolean isCollisionTop, isCollisionBottom, isCollisionLeft, isCollisionRight, collides = false;
    boolean facingLeft = false;
    float jumpingPower = 35.f;

    Vec2 pos;
    Vec2 posLastFrame;
    Vec2 gravity;
    Vec2 maxSpeed;

    float movementSpeed;

    int numberAnimationStates = 0;
    int displayedAnimationState = 0;
    int moveCounter = 0;
    float jumpPower = 50.f;

    BoundingBox boundingBox;

    boolean jumping, walkingLeft, walkingRight = false;


    // Tiles for movement animation
    protected ArrayList<BufferedImage> tilesWalk;
    protected ArrayList<BufferedImage> tilesLife;

    Level l;

    Player(Level l) {
        this.pos = new Vec2(40, 500);
        this.posLastFrame = new Vec2(0, 0);
        this.gravity = new Vec2(0, 1.5f);
        this.maxSpeed = new Vec2(5, 10);
        this.movementSpeed = 7.5f;

        this.l = l;
        tilesWalk = new ArrayList<BufferedImage>();
        tilesLife = new ArrayList<BufferedImage>();
        try {
            // Tiles for movement animation
            BufferedImage imageWalk;
            imageWalk = ImageIO.read(new File(Platformer.BasePath + "Player/p1_walk/PNG/p1_walk01.png"));
            tilesWalk.add(imageWalk);
            imageWalk = ImageIO.read(new File(Platformer.BasePath + "Player/p1_walk/PNG/p1_walk02.png"));
            tilesWalk.add(imageWalk);
            imageWalk = ImageIO.read(new File(Platformer.BasePath + "Player/p1_walk/PNG/p1_walk03.png"));
            tilesWalk.add(imageWalk);
            imageWalk = ImageIO.read(new File(Platformer.BasePath + "Player/p1_walk/PNG/p1_walk04.png"));
            tilesWalk.add(imageWalk);
            imageWalk = ImageIO.read(new File(Platformer.BasePath + "Player/p1_walk/PNG/p1_walk05.png"));
            tilesWalk.add(imageWalk);
            imageWalk = ImageIO.read(new File(Platformer.BasePath + "Player/p1_walk/PNG/p1_walk06.png"));
            tilesWalk.add(imageWalk);
            imageWalk = ImageIO.read(new File(Platformer.BasePath + "Player/p1_walk/PNG/p1_walk07.png"));
            tilesWalk.add(imageWalk);
            imageWalk = ImageIO.read(new File(Platformer.BasePath + "Player/p1_walk/PNG/p1_walk08.png"));
            tilesWalk.add(imageWalk);
            imageWalk = ImageIO.read(new File(Platformer.BasePath + "Player/p1_walk/PNG/p1_walk09.png"));
            tilesWalk.add(imageWalk);
            imageWalk = ImageIO.read(new File(Platformer.BasePath + "Player/p1_walk/PNG/p1_walk10.png"));
            tilesWalk.add(imageWalk);
            imageWalk = ImageIO.read(new File(Platformer.BasePath + "Player/p1_walk/PNG/p1_walk11.png"));
            tilesWalk.add(imageWalk);

        } catch (IOException e) {
            e.printStackTrace();
        }
        numberAnimationStates = tilesWalk.size();
        boundingBox = new BoundingBox(pos.x, pos.y, pos.x + tilesWalk.get(0).getWidth(), pos.y + tilesWalk.get(0).getHeight());
    }

    private void move(int deltaX) {
        if (deltaX < 0) {
            pos.x = pos.x - movementSpeed / 4;
        } else if (deltaX > 0) {
            pos.x = pos.x + movementSpeed / 4;
        }
    }

    public BufferedImage getPlayerImage() {
        BufferedImage b = getNextImage();
        if (facingLeft) {
            AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
            tx.translate(-b.getWidth(null), 0);
            AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
            b = op.filter(b, null);
        }
        return b;
    }

    private BufferedImage getNextImage() {
        //check if the player is moving
        if(!walkingLeft && !walkingRight){
            //player is not moving
            displayedAnimationState = 0;
            return tilesWalk.get(displayedAnimationState);
        } else {
            moveCounter++;
            if (moveCounter >= 3) {
                displayedAnimationState++;
                moveCounter = 0;
            }
            if (displayedAnimationState > numberAnimationStates - 1) {
                displayedAnimationState = 0;
            }
            return tilesWalk.get(displayedAnimationState);
        }
    }

    /**
     * gravity and wind resistance on player
     * if player is not on ground -> fall
     */
    public void update() {

        // Check if walking and call move()
        if (walkingLeft) {
            move(-1);
            facingLeft = true;
        }
        if (walkingRight) {
            move(1);
            facingLeft = false;
        }

        if (jumping && isCollisionBottom) {
            pos.y -= jumpPower;
            playSound(Platformer.BasePath + "Sound/jump1.wav");
        }

        // Save old position
        Vec2 pos_lastFrame_temp = pos;

        // Add gravity and move according to the actual speed
        pos = pos.add(pos.sub(posLastFrame));

        // Get saved old Position back
        posLastFrame = pos_lastFrame_temp;

        //apply gravity
        pos = pos.add(gravity);

        // Calculate difference in X
        float diffX = pos.x - posLastFrame.x;

        // Factor to damp the energy
        float damping = 0.02f;
        if (collides) {
            damping = 0.2f;
        }

        // Generate a damped version of the difference
        pos.x = posLastFrame.x + diffX * (1.0f - damping);

        // Check weather speed is under maxSpeed
        if (pos.x - posLastFrame.x > maxSpeed.x)
            pos.x = posLastFrame.x + maxSpeed.x;

        if (pos.x - posLastFrame.x < -maxSpeed.x)
            pos.x = posLastFrame.x - maxSpeed.x;

        if (pos.y - posLastFrame.y > maxSpeed.y)
            pos.y = posLastFrame.y + maxSpeed.y;

        if (pos.y - posLastFrame.y < -maxSpeed.y)
            pos.y = posLastFrame.y - maxSpeed.y;

        // Check world boundaries
        if (pos.x < 0)
            pos.x = 0;
        if (pos.x > l.lvlSize.x - Tile.tileSize)
            pos.x = l.lvlSize.x - Tile.tileSize;

        updateBoundingBox();
    }

    public void updateBoundingBox () {
        // update BoundingBox
        boundingBox.min.x = pos.x;
        boundingBox.min.y = pos.y;

        boundingBox.max.x = pos.x + tilesWalk.get(0).getWidth();
        boundingBox.max.y = pos.y + tilesWalk.get(0).getHeight();
    }

    public void playSound(String path){
        File lol = new File(path);
        try{
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(lol));
            clip.start();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}


