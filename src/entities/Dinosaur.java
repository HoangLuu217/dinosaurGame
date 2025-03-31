package entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class Dinosaur {
    private int x;
    private int y;
    private boolean isJumping;
    private int jumpVelocity;
    private int initialY; // Store initial ground position
    private BufferedImage image;
    private static final int DINO_WIDTH = 50;
    private static final int DINO_HEIGHT = 50;
    private static final int JUMP_FORCE = -15; // Reduced jump force
    private static final int Y_OFFSET = 105; // Increased offset to lower dinosaur position

    public Dinosaur(int x, int y) {
        this.x = x;
        this.y = y + Y_OFFSET; // Use larger offset to move dinosaur lower
        this.initialY = this.y; // Save adjusted initial position
        isJumping = false;
        try {
            image = ImageIO.read(new File("resources/khunglong.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update() {
        if (isJumping) {
            y += jumpVelocity;
            jumpVelocity += 1; // Gravity
            
            if (y >= initialY) { // Check against initial position
                y = initialY;
                isJumping = false;
            }
        }
    }

    public void jump() {
        if (!isJumping) {
            jumpVelocity = JUMP_FORCE; // Use constant for jump force
            isJumping = true;
        }
    }

    public void draw(Graphics g) {
        g.drawImage(image, x, y, DINO_WIDTH, DINO_HEIGHT, null);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, DINO_WIDTH, DINO_HEIGHT);
    }

    public boolean isJumping() {
        return isJumping;
    }
}