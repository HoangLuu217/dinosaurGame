package entities;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Cactus extends Obstacle {
    private BufferedImage image;
    private boolean isBig;
    private static final int BIG_WIDTH = 30;
    private static final int SMALL_WIDTH = 20;
    private static final int BIG_HEIGHT = 60;
    private static final int SMALL_HEIGHT = 40;

    public Cactus(int x, int y) {
        super(x, y); // Use the ground position directly
        isBig = Math.random() < 0.5;
        try {
            String imageFile = isBig ? "resources/cactusbig.png" : "resources/cactusmini.png";
            image = ImageIO.read(new File(imageFile));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update() {
        x -= speed;
    }

    @Override
    public void draw(Graphics g) {
        int width = isBig ? BIG_WIDTH : SMALL_WIDTH;
        int height = isBig ? BIG_HEIGHT : SMALL_HEIGHT;
        // Draw from ground position upward
        g.drawImage(image, x, y - height, width, height, null);
    }

    @Override
    public boolean intersects(Dinosaur dinosaur) {
        int width = isBig ? BIG_WIDTH : SMALL_WIDTH;
        int height = isBig ? BIG_HEIGHT : SMALL_HEIGHT;
        Rectangle cactusRect = new Rectangle(x, y - height, width, height);
        return cactusRect.intersects(dinosaur.getBounds());
    }
}
