package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class Fence {
    private int x, y;
    private BufferedImage image;
    private static final int FENCE_WIDTH = 80;
    private static final int FENCE_HEIGHT = 40;
    private static final int FENCE_SPEED = 5; // Same speed as obstacles

    public Fence(int x, int y) {
        this.x = x;
        this.y = y;
        try {
            image = ImageIO.read(new File("resources/hangrao.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update() {
        x -= FENCE_SPEED;
    }

    public void draw(Graphics g) {
        g.drawImage(image, x, y, FENCE_WIDTH, FENCE_HEIGHT, null);
    }

    public int getX() {
        return x;
    }
}