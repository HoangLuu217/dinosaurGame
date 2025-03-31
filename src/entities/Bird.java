package entities;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Bird extends Obstacle {
    private BufferedImage image;
    
    public Bird(int x, int y) {
        super(x, y - 100); // Position bird higher above ground
        try {
            image = ImageIO.read(new File("resources/bird.png"));
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
        g.drawImage(image, x, y, 50, 40, null);
    }
    
    @Override
    public boolean intersects(Dinosaur dinosaur) {
        Rectangle birdRect = new Rectangle(x, y, 50, 40);
        return birdRect.intersects(dinosaur.getBounds());
    }
}