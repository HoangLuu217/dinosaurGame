package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import entities.*;

public class GamePanel extends JPanel implements ActionListener, KeyListener {
    private static final int PANEL_WIDTH = 800;
    private static final int PANEL_HEIGHT = 300;
    private static final int GROUND_Y = 250; // Ground level
    private static final int GROUND_HEIGHT = 30; // Height of gray ground
    private static final int DINO_BASE_HEIGHT = 50; // Height from ground to dinosaur feet
    private static final int DINO_TOTAL_HEIGHT = 60; // Total dinosaur height
    private static final int BIRD_OFFSET = 30; // Bird flies just above dinosaur head
    private static final int BIRD_HEIGHT = 40; // Bird height
    private static final int BASE_OFFSET = 0; // Height from gray ground to feet
    private static final int DINO_OFFSET = 150; // Increased to raise dinosaur higher
    
    // Minimum spacing between obstacles to make game playable
    private static final int MIN_OBSTACLE_DISTANCE = 300; // Increased minimum distance
    private static final int MAX_OBSTACLE_DISTANCE = 500; // Increased maximum distance
    private static final int MIN_FENCE_DISTANCE = 200; // Minimum distance between fences
    private static final int MIN_MOUNTAIN_DISTANCE = 400; // Minimum distance between mountains
    private static final Color SKY_COLOR = new Color(200, 255, 200); // Light green background
    
    private long lastObstacleTime = 0;
    private int currentObstacleInterval;
    private long lastFenceTime = 0; // Track last fence spawn time
    private int currentFenceInterval = MIN_FENCE_DISTANCE;
    private long lastMountainTime = 0;
    private int currentMountainInterval = MIN_MOUNTAIN_DISTANCE;
    
    private GameWindow gameWindow;
    private Dinosaur dinosaur;
    private ArrayList<Obstacle> obstacles;
    private ArrayList<Cloud> clouds;
    private ArrayList<Mountain> mountains;
    private ArrayList<Fence> fences;
    public Timer gameTimer;
    private int score = 0;
    private boolean isGameOver = false;
    private int difficulty;
    private int baseSpeed;
    private static final Color LIGHT_BROWN = new Color(160, 82, 45, 150); // Darker brown with transparency
    private Color groundColor = new Color(128, 128, 128); // Keep original gray ground

    public GamePanel(int difficulty, GameWindow window) {
        this.difficulty = difficulty;
        this.gameWindow = window;
        this.baseSpeed = 5 + (difficulty - 1) * 2;
        
        currentObstacleInterval = MIN_OBSTACLE_DISTANCE;
        
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        setBackground(SKY_COLOR); // Changed from Color.WHITE to light green
        setFocusable(true);
        addKeyListener(this);

        initialize();
    }

    public void initialize() {
        // Use consistent positioning
        dinosaur = new Dinosaur(50, GROUND_Y - DINO_OFFSET);
        obstacles = new ArrayList<>();
        clouds = new ArrayList<>();
        mountains = new ArrayList<>();
        fences = new ArrayList<>();
        score = 0;
        isGameOver = false;
        lastObstacleTime = System.currentTimeMillis();
        currentObstacleInterval = MIN_OBSTACLE_DISTANCE;
        
        // Create and start timer only in initialize
        gameTimer = new Timer(1000/60, this);
        gameTimer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // Draw mountains first (background)
        for (Mountain mountain : mountains) {
            mountain.draw(g);
        }
        
        // Draw light brown gradient area above ground
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(LIGHT_BROWN);
        g2d.fillRect(0, GROUND_Y - 60, PANEL_WIDTH, 80); // Draw light brown area above ground
        
        // Draw clouds next
        for (Cloud cloud : clouds) {
            cloud.draw(g);
        }
        
        // Draw fences before ground
        for (Fence fence : fences) {
            fence.draw(g);
        }
        
        // Draw gray ground
        g.setColor(groundColor);
        g.fillRect(0, GROUND_Y, PANEL_WIDTH, GROUND_HEIGHT);
        
        // Draw clouds first (behind everything)
        if (clouds != null) {
            for (Cloud cloud : clouds) {
                cloud.draw(g);
            }
        }
        
        // Draw obstacles
        if (obstacles != null) {
            for (Obstacle obstacle : obstacles) {
                obstacle.draw(g);
            }
        }
        
        // Draw dinosaur last (on top of clouds and obstacles)
        if (dinosaur != null) {
            dinosaur.draw(g);
        }
        
        // Draw score with adjusted position (moved left)
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Score: " + score, 20, 30); // Changed from 700 to 20 for x-coordinate
        
        if (isGameOver) {
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 40));
            g.drawString("Game Over!", 300, 150);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString("Press SPACE to return to menu", 280, 200);
        }
    }

    private void spawnObstacle() {
        long currentTime = System.currentTimeMillis();
        
        // Check distance from last obstacle
        if (!obstacles.isEmpty()) {
            Obstacle lastObstacle = obstacles.get(obstacles.size() - 1);
            if (PANEL_WIDTH - lastObstacle.getX() < MIN_OBSTACLE_DISTANCE) {
                return;
            }
        }
        
        if (currentTime - lastObstacleTime >= currentObstacleInterval) {
            if (Math.random() < 0.5) { // Reduced spawn chance to 50%
                if (Math.random() < 0.7) {
                    // Position cactus at same height as dinosaur feet
                    obstacles.add(new Cactus(PANEL_WIDTH, GROUND_Y - BASE_OFFSET));
                } else {
                    // Position bird higher above ground
                    obstacles.add(new Bird(PANEL_WIDTH, GROUND_Y ));
                }
                lastObstacleTime = currentTime;
                currentObstacleInterval = MIN_OBSTACLE_DISTANCE + 
                    (int)(Math.random() * (MAX_OBSTACLE_DISTANCE - MIN_OBSTACLE_DISTANCE));
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!isGameOver) {
            updateSpeed();
            dinosaur.update();
            spawnObstacle();
            spawnCloud();
            spawnMountain();
            spawnFence();
            
            // Update obstacles
            for (int i = obstacles.size() - 1; i >= 0; i--) {
                Obstacle obstacle = obstacles.get(i);
                obstacle.update();
                
                if (obstacle.getX() < -50) {
                    obstacles.remove(i);
                    score += 10;
                } else if (obstacle.intersects(dinosaur)) {
                    isGameOver = true;
                    
                    if (gameWindow != null) {
                       
                    }
                }
            }
            
            // Update clouds
            for (int i = clouds.size() - 1; i >= 0; i--) {
                Cloud cloud = clouds.get(i);
                cloud.update();
                if (cloud.getX() < -100) {
                    clouds.remove(i);
                }
            }
            
            // Update mountains
            for (int i = mountains.size() - 1; i >= 0; i--) {
                Mountain mountain = mountains.get(i);
                mountain.update();
                if (mountain.getX() < -150) {
                    mountains.remove(i);
                }
            }
            
            // Update fences
            for (int i = fences.size() - 1; i >= 0; i--) {
                Fence fence = fences.get(i);
                fence.update();
                if (fence.getX() < -80) {
                    fences.remove(i);
                }
            }
        }
        repaint();
    }

    private void spawnCloud() {
        // Increased cloud spawn chance significantly
        if (Math.random() < 0.25) { // Increased from 0.15 to 0.25
            int cloudY = 10 + (int)(Math.random() * 60); // Adjusted height range for clouds
            clouds.add(new Cloud(PANEL_WIDTH - 100, cloudY)); // Start clouds 100px earlier
        }
    }

    private void spawnMountain() {
        long currentTime = System.currentTimeMillis();
        
        // Check distance from last mountain
        if (!mountains.isEmpty()) {
            Mountain lastMountain = mountains.get(mountains.size() - 1);
            if (PANEL_WIDTH - lastMountain.getX() < MIN_MOUNTAIN_DISTANCE) {
                return;
            }
        }
        
        if (currentTime - lastMountainTime >= currentMountainInterval) {
            if (Math.random() < 0.1) { // 10% chance to spawn mountain
                mountains.add(new Mountain(PANEL_WIDTH, GROUND_Y - 150));
                lastMountainTime = currentTime;
                // Random interval between mountains
                currentMountainInterval = MIN_MOUNTAIN_DISTANCE + 
                    (int)(Math.random() * 300); // Add 0-300 pixels of extra spacing
            }
        }
    }

    private void spawnFence() {
        long currentTime = System.currentTimeMillis();
        
        // Check distance from last fence
        if (!fences.isEmpty()) {
            Fence lastFence = fences.get(fences.size() - 1);
            if (PANEL_WIDTH - lastFence.getX() < MIN_FENCE_DISTANCE) {
                return;
            }
        }
        
        if (currentTime - lastFenceTime >= currentFenceInterval) {
            if (Math.random() < 0.3) { // 30% chance to spawn fence
                fences.add(new Fence(PANEL_WIDTH, GROUND_Y - 30));
                lastFenceTime = currentTime;
                // Random interval between fences
                currentFenceInterval = MIN_FENCE_DISTANCE + 
                    (int)(Math.random() * 200); // Add 0-200 pixels of extra spacing
            }
        }
    }

    private void updateSpeed() {
        int speedIncrease = (score / 100) * 2; // Slower speed increase
        baseSpeed = 5 + (difficulty - 1) * 2 + speedIncrease;
        for (Obstacle obstacle : obstacles) {
            obstacle.setSpeed(baseSpeed);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            if (isGameOver) {
                gameTimer.stop(); // Dừng timer khi game over
                gameWindow.showMenu();
            } else if (!dinosaur.isJumping()) {
                dinosaur.jump();
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
    
    public void startGame() {
        // Reset game state
        score = 0;
        isGameOver = false;
        obstacles.clear();
        clouds.clear();
        // Use same height offset as initialize
        dinosaur = new Dinosaur(50, GROUND_Y - DINO_OFFSET); // Same position as initialize
        lastObstacleTime = System.currentTimeMillis();
        currentObstacleInterval = MIN_OBSTACLE_DISTANCE;
        
        // Restart timer
        if (!gameTimer.isRunning()) {
            gameTimer.start();
        }
    }
}