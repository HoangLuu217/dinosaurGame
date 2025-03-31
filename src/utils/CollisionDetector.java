package utils;

import entities.Dinosaur;
import entities.Obstacle;
import java.awt.Rectangle;

public class CollisionDetector {
    public static boolean checkCollision(Dinosaur dinosaur, Obstacle obstacle) {
        Rectangle dinosaurBounds = dinosaur.getBounds();
        return obstacle.intersects(dinosaur);
    }
}