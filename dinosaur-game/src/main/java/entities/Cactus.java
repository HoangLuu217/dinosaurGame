package entities;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Cactus extends Obstacle {
    private BufferedImage image;

    public Cactus(int x, int y, String imageFile) {
        super(x, y);
        try {
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
        g.drawImage(image, x, y, 30, 60, null);
    }

    @Override
    public boolean intersects(Dinosaur dinosaur) {
        Rectangle cactusRect = new Rectangle(x, y, 30, 60);
        return cactusRect.intersects(dinosaur.getBounds());
    }
}