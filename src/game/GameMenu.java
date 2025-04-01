package game;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;

public class GameMenu extends JPanel {
    private GameWindow gameWindow;
    private JComboBox<String> difficultyCombo;
    private Icon gifIcon;
    private Clip musicClip;

    public GameMenu(GameWindow window) {
        this.gameWindow = window;
        setLayout(new BorderLayout(0, 0));
        setBackground(new Color(240, 240, 240));

        // Load animated GIF
        try {
            gifIcon = new ImageIcon(new ImageIcon("resources/khunglong.gif").getImage()
                .getScaledInstance(800, 300, Image.SCALE_DEFAULT));
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Single panel for full image with custom size
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (gifIcon != null) {
                    // Center the gif if panel is larger
                    int x = (getWidth() - gifIcon.getIconWidth()) / 2;
                    int y = (getHeight() - gifIcon.getIconHeight()) / 2;
                    gifIcon.paintIcon(this, g, x, y);
                }
            }
        };
        mainPanel.setPreferredSize(new Dimension(800, 300));
        mainPanel.setLayout(new BorderLayout());

        // Control panel (semi-transparent) at bottom
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        controlPanel.setBackground(new Color(255, 255, 255, 0));
        
        // Difficulty selector
        JPanel difficultyPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        difficultyPanel.setOpaque(false);
        
        JLabel diffLabel = new JLabel("Độ khó: ");
        diffLabel.setFont(new Font("Arial", Font.BOLD, 18));
        diffLabel.setForeground(Color.white);
        String[] difficulties = {"Dễ", "Trung bình", "Khó"};
        difficultyCombo = new JComboBox<>(difficulties);
        difficultyCombo.setPreferredSize(new Dimension(100, 25));
        difficultyCombo.setBackground(Color.WHITE);

        difficultyPanel.add(diffLabel);
        difficultyPanel.add(difficultyCombo);

        // Start button with improved styling
        JButton startButton = new JButton("Bắt đầu");
        startButton.setFont(new Font("Arial", Font.BOLD, 16));
        startButton.setBackground(new Color(34, 139, 34));
        startButton.setForeground(Color.WHITE);
        startButton.setFocusPainted(false);
        startButton.setPreferredSize(new Dimension(120, 40));
        startButton.addActionListener(e -> {
            int difficulty = difficultyCombo.getSelectedIndex() + 1;
            gameWindow.startGame(difficulty);
        });

        // Add components to control panel
        controlPanel.add(difficultyPanel);
        controlPanel.add(startButton);

        // Add control panel to bottom of main panel
        mainPanel.add(controlPanel, BorderLayout.SOUTH);

        // Add main panel to frame
        add(mainPanel, BorderLayout.CENTER);

    }


}