package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import entities.*;

public class GamePanel extends JPanel implements ActionListener, KeyListener {
    private static final int PANEL_WIDTH = 800;
    private static final int PANEL_HEIGHT = 300;
    private static final int GROUND_Y = 250;
    
    private Dinosaur dinosaur;
    private ArrayList<Obstacle> obstacles;
    private ArrayList<Cloud> clouds;
    private Timer gameTimer;
    private int score = 0;
    private boolean isGameOver = false;

    public GamePanel() {
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        setBackground(Color.WHITE);
        setFocusable(true);
        addKeyListener(this);

        dinosaur = new Dinosaur(50, GROUND_Y);
        obstacles = new ArrayList<>();
        clouds = new ArrayList<>();
        
        gameTimer = new Timer(1000/60, this);
        gameTimer.start();
        
        spawnObstacle();
        spawnCloud();
    }

    private void spawnObstacle() {
        if (Math.random() < 0.5) {
            obstacles.add(new Cactus(PANEL_WIDTH, GROUND_Y, "cactusbig.png"));
        } else {
            obstacles.add(new Bird(PANEL_WIDTH, GROUND_Y - 50));
        }
    }

    private void spawnCloud() {
        clouds.add(new Cloud(PANEL_WIDTH, 50 + (int)(Math.random() * 100)));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // Draw ground
        g.setColor(Color.GRAY);
        g.drawLine(0, GROUND_Y + 30, PANEL_WIDTH, GROUND_Y + 30);
        
        // Draw game elements
        dinosaur.draw(g);
        for (Obstacle obstacle : obstacles) {
            obstacle.draw(g);
        }
        for (Cloud cloud : clouds) {
            cloud.draw(g);
        }
        
        // Draw score
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Score: " + score, 700, 30);
        
        if (isGameOver) {
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 40));
            g.drawString("Game Over!", 300, 150);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!isGameOver) {
            dinosaur.update();
            
            // Update obstacles
            for (int i = obstacles.size() - 1; i >= 0; i--) {
                Obstacle obstacle = obstacles.get(i);
                obstacle.update();
                
                if (obstacle.getX() < -50) {
                    obstacles.remove(i);
                    score += 10;
                    spawnObstacle();
                }
                
                if (obstacle.intersects(dinosaur)) {
                    isGameOver = true;
                }
            }
            
            // Update clouds
            for (int i = clouds.size() - 1; i >= 0; i--) {
                Cloud cloud = clouds.get(i);
                cloud.update();
                if (cloud.getX() < -100) {
                    clouds.remove(i);
                    spawnCloud();
                }
            }
        }
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE && !dinosaur.isJumping()) {
            dinosaur.jump();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
}