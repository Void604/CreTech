import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

public class GamePanel extends JPanel {
    private static final Color BOARD_COLOR = new Color(255, 255, 255);
    private static final Color LINE_COLOR = new Color(224, 229, 236);
    private static final Color X_COLOR = new Color(75, 101, 232);    // Blue
    private static final Color O_COLOR = new Color(252, 92, 101);    // Red
    private static final Color HOVER_COLOR = new Color(224, 229, 236, 100);
    private static final int LINE_WIDTH = 4;
    private static final int SYMBOL_STROKE_WIDTH = 8;
    private static final float SYMBOL_SIZE_RATIO = 0.6f;
    
    private final GameModel gameModel;
    private int hoverRow = -1;
    private int hoverCol = -1;
    
    public GamePanel(GameModel gameModel) {
        this.gameModel = gameModel;
        setBackground(BOARD_COLOR);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleMouseClick(e);
            }
            
            @Override
            public void mouseMoved(MouseEvent e) {
                updateHoverPosition(e);
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                hoverRow = -1;
                hoverCol = -1;
                repaint();
            }
        };
        
        addMouseListener(mouseAdapter);
        addMouseMotionListener(mouseAdapter);
    }
    
    private void handleMouseClick(MouseEvent e) {
        if (gameModel.isGameOver()) {
            return;
        }
        
        int cellSize = getCellSize();
        int boardOffset = getBoardOffset();
        
        int row = (e.getY() - boardOffset) / cellSize;
        int col = (e.getX() - boardOffset) / cellSize;
        
        if (row >= 0 && row < 3 && col >= 0 && col < 3) {
            gameModel.makeMove(row, col);
        }
    }
    
    private void updateHoverPosition(MouseEvent e) {
        if (gameModel.isGameOver()) {
            hoverRow = -1;
            hoverCol = -1;
            repaint();
            return;
        }
        
        int cellSize = getCellSize();
        int boardOffset = getBoardOffset();
        
        int row = (e.getY() - boardOffset) / cellSize;
        int col = (e.getX() - boardOffset) / cellSize;
        
        if (row >= 0 && row < 3 && col >= 0 && col < 3 && gameModel.getCell(row, col) == null) {
            hoverRow = row;
            hoverCol = col;
        } else {
            hoverRow = -1;
            hoverCol = -1;
        }
        
        repaint();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Draw board background with shadow
        drawBoardBackground(g2d);
        
        // Draw the game board
        drawBoard(g2d);
        
        // Draw hover effect
        drawHoverEffect(g2d);
        
        // Draw the X and O symbols
        drawSymbols(g2d);
        
        // Draw winning line if game is over
        if (gameModel.isGameOver() && gameModel.getWinner() != null) {
            drawWinningLine(g2d);
        }
        
        g2d.dispose();
    }
    
    private void drawBoardBackground(Graphics2D g2d) {
        int cellSize = getCellSize();
        int boardSize = cellSize * 3;
        int boardOffset = getBoardOffset();
        
        // Draw shadow
        g2d.setColor(new Color(0, 0, 0, 20));
        g2d.fillRoundRect(boardOffset + 5, boardOffset + 5, boardSize, boardSize, 15, 15);
        
        // Draw board background
        g2d.setColor(BOARD_COLOR);
        g2d.fillRoundRect(boardOffset, boardOffset, boardSize, boardSize, 15, 15);
    }
    
    private void drawBoard(Graphics2D g2d) {
        int cellSize = getCellSize();
        int boardSize = cellSize * 3;
        int boardOffset = getBoardOffset();
        
        g2d.setColor(LINE_COLOR);
        g2d.setStroke(new BasicStroke(LINE_WIDTH));
        
        // Draw vertical lines
        for (int i = 1; i < 3; i++) {
            int x = boardOffset + i * cellSize;
            g2d.drawLine(x, boardOffset, x, boardOffset + boardSize);
        }
        
        // Draw horizontal lines
        for (int i = 1; i < 3; i++) {
            int y = boardOffset + i * cellSize;
            g2d.drawLine(boardOffset, y, boardOffset + boardSize, y);
        }
    }
    
    private void drawHoverEffect(Graphics2D g2d) {
        if (hoverRow >= 0 && hoverCol >= 0 && !gameModel.isGameOver()) {
            int cellSize = getCellSize();
            int boardOffset = getBoardOffset();
            
            int x = boardOffset + hoverCol * cellSize;
            int y = boardOffset + hoverRow * cellSize;
            
            g2d.setColor(HOVER_COLOR);
            g2d.fillRect(x, y, cellSize, cellSize);
        }
    }
    
    private void drawSymbols(Graphics2D g2d) {
        int cellSize = getCellSize();
        int boardOffset = getBoardOffset();
        int padding = (int) (cellSize * (1 - SYMBOL_SIZE_RATIO) / 2);
        
        g2d.setStroke(new BasicStroke(SYMBOL_STROKE_WIDTH, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                int x = boardOffset + col * cellSize + padding;
                int y = boardOffset + row * cellSize + padding;
                int symbolSize = cellSize - 2 * padding;
                
                Player player = gameModel.getCell(row, col);
                if (player != null) {
                    if (player.getSymbol() == 'X') {
                        drawX(g2d, x, y, symbolSize);
                    } else {
                        drawO(g2d, x, y, symbolSize);
                    }
                }
            }
        }
    }
    
    private void drawX(Graphics2D g2d, int x, int y, int size) {
        g2d.setColor(X_COLOR);
        
        // Draw X with animation-like effect
        int margin = size / 8;
        g2d.drawLine(x + margin, y + margin, x + size - margin, y + size - margin);
        g2d.drawLine(x + size - margin, y + margin, x + margin, y + size - margin);
    }
    
    private void drawO(Graphics2D g2d, int x, int y, int size) {
        g2d.setColor(O_COLOR);
        
        // Draw O with slight padding for better appearance
        int margin = size / 8;
        g2d.drawOval(x + margin, y + margin, size - 2 * margin, size - 2 * margin);
    }
    
    private void drawWinningLine(Graphics2D g2d) {
        int cellSize = getCellSize();
        int boardOffset = getBoardOffset();
        int[] winningLine = gameModel.getWinningLine();
        
        if (winningLine != null) {
            g2d.setColor(new Color(46, 213, 115));
            g2d.setStroke(new BasicStroke(SYMBOL_STROKE_WIDTH, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            
            int startRow = winningLine[0];
            int startCol = winningLine[1];
            int endRow = winningLine[2];
            int endCol = winningLine[3];
            
            int x1 = boardOffset + startCol * cellSize + cellSize / 2;
            int y1 = boardOffset + startRow * cellSize + cellSize / 2;
            int x2 = boardOffset + endCol * cellSize + cellSize / 2;
            int y2 = boardOffset + endRow * cellSize + cellSize / 2;
            
            g2d.drawLine(x1, y1, x2, y2);
        }
    }
    
    private int getCellSize() {
        int minDimension = Math.min(getWidth(), getHeight());
        return minDimension / 3;
    }
    
    private int getBoardOffset() {
        int cellSize = getCellSize();
        int boardSize = cellSize * 3;
        
        return Math.min((getWidth() - boardSize) / 2, (getHeight() - boardSize) / 2);
    }
}