package game;

import javax.swing.*;
import java.awt.*;

public class GameWindow extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private GameMenu menu;
    private GamePanel gamePanel;

    public GameWindow() {
        setTitle("Dinosaur Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        
        // Set window icon
        try {
            ImageIcon icon = new ImageIcon("resources/khunglong.png");
            setIconImage(icon.getImage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        menu = new GameMenu(this);
        
        mainPanel.add(menu, "Menu");
        add(mainPanel);
        
        setPreferredSize(new Dimension(800, 300));
        pack();
        setLocationRelativeTo(null);
    }

    public void startGame(int difficulty) {
        if (gamePanel != null) {
            mainPanel.remove(gamePanel);
            if (gamePanel.gameTimer != null) {
                gamePanel.gameTimer.stop();
            }
        }
        
        gamePanel = new GamePanel(difficulty, this);
        mainPanel.add(gamePanel, "Game");
        cardLayout.show(mainPanel, "Game");
        gamePanel.requestFocusInWindow();
    }

    public void showMenu() {
        if (gamePanel != null && gamePanel.gameTimer != null) {
            gamePanel.gameTimer.stop();
        }
        cardLayout.show(mainPanel, "Menu");

    }


}