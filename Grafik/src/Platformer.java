import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serial;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Platformer extends JFrame {

    public static final String BasePath = "D:\\Marburg\\Sem02\\Programmierpraktikum\\Code\\Grafik\\assets\\";
    @Serial
    private static final long serialVersionUID = 5736902251450559962L;

    private Level l = null;
    private Player p = null;
    BufferStrategy bufferStrategy;

    public Platformer() {
        //exit program when window is closed
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(new File("./"));
        fc.setDialogTitle("Select input image");
        FileFilter filter = new FileNameExtensionFilter("Level image (.bmp)", "bmp");
        fc.setFileFilter(filter);
        int result = fc.showOpenDialog(this);
        File selectedFile = new File("");
        addKeyListener(new KeyListener(this));
        createBufferStrategy(2);
        bufferStrategy = this.getBufferStrategy();


        if (result == JFileChooser.APPROVE_OPTION) {
            selectedFile = fc.getSelectedFile();
            System.out.println("Selected file: " + selectedFile.getAbsolutePath());
        } else {
            dispose();
            System.exit(0);
        }

        try {
            l = new Level(selectedFile.getAbsolutePath());
            p = new Player(l);
            l.player = p;

            this.setBounds(0, 0, 1000, 12 * 70);
            this.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //call updateGameStateAndRepaint every 10ms
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                updateGameStateAndRepaint();
            }
        }).start();

        //on start play background music
        playSound("D:\\Marburg\\Sem02\\Programmierpraktikum\\Code\\Grafik\\assets\\Sound\\soundtrack.wav");
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

    /**
     * win the game
     * if player reached flag item
     * @throws IOException
     */
    private void win() throws IOException {
        //player reached the end of the level

    }

    /**
     * restart the game
     * @throws IOException
     */
    private void restart() throws IOException {
        p.pos.x = 40;
        p.pos.y = 0;
        l.offsetX = 0;
        l.initLevel();
    }

    /**
     * update the game state and repaint
     */
    private void updateGameStateAndRepaint() {
        l.update();
        p.update();
        checkCollision();
        repaint();
    }

    /**
     * paint the level and the player
     * @param g graphics
     */
    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = null;
        try {
            g2 = (Graphics2D) bufferStrategy.getDrawGraphics();
            draw(g2);
        } finally {
            if (g2 != null){
                g2.dispose();
            }
        }
        bufferStrategy.show();
    }

    /**
     * draw the level and the player
     * @param g2d graphics
     */
    private void draw(Graphics2D g2d) {
        BufferedImage level = (BufferedImage) l.getResultingImage();
        if (l.offsetX > level.getWidth() - 1000)
            l.offsetX = level.getWidth() - 1000;
        BufferedImage bi = level.getSubimage((int) l.offsetX, 0, 1000, level.getHeight());
        g2d.drawImage(bi, 0, 0, this);
        g2d.drawImage(getPlayer().getPlayerImage(), (int) (getPlayer().pos.x - l.offsetX), (int) getPlayer().pos.y, this);
    }

    /**
     * get the player
     * @return player
     */
    public Player getPlayer() {
        return this.p;
    }

    private void checkCollision() {
        float playerPosX = p.pos.x;

        p.isCollisionBottom = false;
        p.isCollisionLeft = false;
        p.isCollisionRight = false;
        p.isCollisionTop = false;
        p.collides = false;
        // Collision
        for (int i = 0; i < l.tiles.size(); i++) {

            Tile tile = l.tiles.get(i);

            p.updateBoundingBox();
            Vec2 overlapSize = tile.bb.OverlapSize(p.boundingBox);

            float epsilon = 8.f; // experiment with this value. If too low,the player might get stuck when walking over the
            // ground. If too high, it can cause glitching inside/through walls

            if (overlapSize.x >= 0 && overlapSize.y >= 0 && Math.abs(overlapSize.x + overlapSize.y) >= epsilon) {

                //check if overlap with water tile
                if(tile.imageIndex == 1){
                    //player is in the water
                    try {
                        restart();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    if (Math.abs(overlapSize.x) > Math.abs(overlapSize.y)) {// Y overlap correction

                        if (p.boundingBox.min.y + p.boundingBox.max.y > tile.bb.min.y + tile.bb.max.y) {
                            // player comes from below
                            p.pos.y += overlapSize.y;
                            p.posLastFrame.y = p.pos.y;
                            p.isCollisionTop = true;
                        } else {
                            // player comes from above
                            p.pos.y -= overlapSize.y;
                            p.posLastFrame.y = p.pos.y;
                            p.isCollisionBottom = true;
                        }
                    } else { // X overlap correction
                        if (p.boundingBox.min.x + p.boundingBox.max.x > tile.bb.min.x + tile.bb.max.x) {
                            // player comes from right
                            p.pos.x += overlapSize.x;
                            p.posLastFrame.x = p.pos.x;
                            p.isCollisionLeft = true;
                        } else {
                            // player comes from a left
                            p.pos.x -= overlapSize.x;
                            p.posLastFrame.x = p.pos.x;
                            p.isCollisionRight = true;
                        }
                    }

                    p.updateBoundingBox();
                }
            }
        }
    }

    /**
     * KeyListener for the game
     */
    public class KeyListener extends KeyAdapter {
        Platformer p;

        public KeyListener(Platformer p) {
            super();
            this.p = p;
        }

        @Override
        public void keyPressed(KeyEvent event) {
            int keyCode = event.getKeyCode();
            Player player = p.getPlayer();

            if (keyCode == KeyEvent.VK_ESCAPE) {
                //exit program
                dispose();
                System.exit(0);
            }
            //go down
            if (keyCode == KeyEvent.VK_S) {
                //wants to fall faster
                player.gravity.y = 42f;
            }
            //go left
            if (keyCode == KeyEvent.VK_A) {
                p.getPlayer().walkingLeft = true;
            }
            //go right
            if (keyCode == KeyEvent.VK_D) {
                p.getPlayer().walkingRight = true;
            }
            if(keyCode == KeyEvent.VK_SPACE){
                player.jumping = true;
            }
            if (keyCode == KeyEvent.VK_R) {
                try {
                    restart();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            updateGameStateAndRepaint();
        }

        @Override
        public void keyReleased(KeyEvent event) {
            int keyCode = event.getKeyCode();
            Player player = p.getPlayer();

            if (keyCode == KeyEvent.VK_A) {
                p.getPlayer().walkingLeft = false;
            }
            if (keyCode == KeyEvent.VK_S) {
                player.gravity.y = 1.5f;
            }
            if (keyCode == KeyEvent.VK_D) {
                p.getPlayer().walkingRight = false;
            }
            if (keyCode == KeyEvent.VK_SPACE) {
                p.getPlayer().jumping = false;
            }
        }
    }

}
