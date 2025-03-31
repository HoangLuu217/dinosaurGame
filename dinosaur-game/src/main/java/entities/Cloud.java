package entities;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Cloud {
    private int x, y;
    private int speed = 2;
    private BufferedImage image;
    
    public Cloud(int x, int y) {
        this.x = x;
        this.y = y;
        try {
            image = ImageIO.read(new File("cloud.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void update() {
        x -= speed;
    }
    
    public void draw(Graphics g) {
        g.drawImage(image, x, y, 70, 40, null);
    }
    
    public int getX() {
        return x;
    }
}