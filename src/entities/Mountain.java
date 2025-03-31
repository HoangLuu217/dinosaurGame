package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class Mountain {
    private int x, y;
    private BufferedImage image;
    private static final int MOUNTAIN_WIDTH = 150;
    private static final int MOUNTAIN_HEIGHT = 100;
    private static final int MOUNTAIN_SPEED = 4; // Slower than clouds

    public Mountain(int x, int y) {
        this.x = x;
        this.y = y + 1;
        try {
            image = ImageIO.read(new File("resources/nui.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update() {
        x -= MOUNTAIN_SPEED;
    }

    public void draw(Graphics g) {
        g.drawImage(image, x, y, MOUNTAIN_WIDTH, MOUNTAIN_HEIGHT, null);
    }

    public int getX() {
        return x;
    }
}