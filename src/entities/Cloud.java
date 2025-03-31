package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class Cloud {
    private int x, y;
    private BufferedImage image;
    private static final int CLOUD_WIDTH = 100;  // Increased from default
    private static final int CLOUD_HEIGHT = 60;  // Increased from default
    private static final int CLOUD_SPEED = 5; // Increased from 2 to 3 for faster movement

    public Cloud(int x, int y) {
        this.x = x;
        this.y = y;
        try {
            image = ImageIO.read(new File("resources/cloud.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update() {
        x -= CLOUD_SPEED;
    }

    public void draw(Graphics g) {
        g.drawImage(image, x, y, CLOUD_WIDTH, CLOUD_HEIGHT, null);
    }

    public int getX() {
        return x;
    }
}
