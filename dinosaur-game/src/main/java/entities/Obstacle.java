package entities;

import java.awt.*;

public abstract class Obstacle {
    protected int x, y;
    protected int speed = 5;

    public Obstacle(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public abstract void update();
    public abstract void draw(Graphics g);
    public abstract boolean intersects(Dinosaur dinosaur);

    public int getX() {
        return x;
    }
}