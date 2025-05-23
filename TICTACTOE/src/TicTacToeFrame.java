import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToeFrame extends JFrame {
    private static final int WINDOW_WIDTH = 500;
    private static final int WINDOW_HEIGHT = 600;
    private static final Color BACKGROUND_COLOR = new Color(240, 243, 249);
    private static final Color HEADER_COLOR = new Color(47, 54, 64);
    private static final Color TEXT_COLOR = new Color(47, 54, 64);
    private static final Color BUTTON_COLOR = new Color(75, 101, 232);
    private static final Color BUTTON_HOVER_COLOR = new Color(60, 84, 201);
    
    private final GamePanel gamePanel;
    private JLabel statusLabel = null;
    private JButton restartButton;
    
    public TicTacToeFrame() {
        setTitle("Tic Tac Toe");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Set custom background color
        getContentPane().setBackground(BACKGROUND_COLOR);
        
        // Create a game model
        GameModel gameModel = new GameModel();
        
        // Set up the main layout with padding
        setLayout(new BorderLayout(20, 20));
        
        // Create header panel with game title
        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);
        
        // Create the game panel with a custom border
        gamePanel = new GamePanel(gameModel);
        this.restartButton = new JButton();
        gamePanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(224, 229, 236), 2),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        add(gamePanel, BorderLayout.CENTER);
        
        // Create the bottom panel with status and restart button
        JPanel bottomPanel = createBottomPanel(gameModel);
        add(bottomPanel, BorderLayout.SOUTH);
        
        // Set up the game listener
        gameModel.setGameListener(new GameModel.GameListener() {
            @Override
            public void onGameStateChanged() {
                updateStatus(gameModel);
                gamePanel.repaint();
            }
        });
    }
    
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setBackground(BACKGROUND_COLOR);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
        
        JLabel titleLabel = new JLabel("Tic Tac Toe");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
        titleLabel.setForeground(HEADER_COLOR);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        
        return headerPanel;
    }
    
    private JPanel createBottomPanel(GameModel gameModel) {
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout(10, 10));
        bottomPanel.setBackground(BACKGROUND_COLOR);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
        
        // Create status label with custom styling
        statusLabel = new JLabel("Player X's turn");
        statusLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        statusLabel.setForeground(TEXT_COLOR);
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        bottomPanel.add(statusLabel, BorderLayout.NORTH);
        
        // Create restart button with custom styling
        restartButton = createStyledButton("New Game");
        restartButton.addActionListener(e -> {
            gameModel.resetGame();
            gamePanel.repaint();
            updateStatus(gameModel);
        });
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(BACKGROUND_COLOR);
        buttonPanel.add(restartButton);
        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        return bottomPanel;
    }
    
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                if (getModel().isPressed()) {
                    g2.setColor(BUTTON_HOVER_COLOR);
                } else if (getModel().isRollover()) {
                    g2.setColor(BUTTON_HOVER_COLOR);
                } else {
                    g2.setColor(BUTTON_COLOR);
                }
                
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.dispose();
                
                super.paintComponent(g);
            }
        };
        
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setPreferredSize(new Dimension(150, 40));
        
        return button;
    }
    
    private void updateStatus(GameModel gameModel) {
        if (gameModel.isGameOver()) {
            Player winner = gameModel.getWinner();
            if (winner != null) {
                statusLabel.setText("Player " + winner.getSymbol() + " wins! 🎉");
            } else {
                statusLabel.setText("It's a draw! 🤝");
            }
        } else {
            statusLabel.setText("Player " + gameModel.getCurrentPlayer().getSymbol() + "'s turn");
        }
    }
}