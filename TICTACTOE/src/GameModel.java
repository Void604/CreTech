/**
 * The model class that represents the game state and logic.
 */
public class GameModel {
    // Represents the 3x3 game board
    private Player[][] board;
    
    // Players X and O
    private final Player playerX;
    private final Player playerO;
    
    // Current player
    private Player currentPlayer;
    
    // Game state
    private boolean gameOver;
    private Player winner;
    private int[] winningLine; // [startRow, startCol, endRow, endCol]
    
    // Listener for game state changes
    private GameListener gameListener;
    
    /**
     * Constructor initializes the game.
     */
    public GameModel() {
        playerX = new Player('X');
        playerO = new Player('O');
        resetGame();
    }
    
    /**
     * Resets the game to its initial state.
     */
    public void resetGame() {
        // Initialize empty board
        board = new Player[3][3];
        
        // Reset game state
        currentPlayer = playerX; // X goes first
        gameOver = false;
        winner = null;
        winningLine = null;
        
        // Notify listeners
        if (gameListener != null) {
            gameListener.onGameStateChanged();
        }
    }
    
    /**
     * Attempts to make a move at the specified position.
     * 
     * @param row The row (0-2)
     * @param col The column (0-2)
     * @return true if the move was successful, false otherwise
     */
    public boolean makeMove(int row, int col) {
        // Check if game is over or cell is already occupied
        if (gameOver || board[row][col] != null) {
            return false;
        }
        
        // Make the move
        board[row][col] = currentPlayer;
        
        // Check for win or draw
        checkGameStatus();
        
        // Switch player if game is not over
        if (!gameOver) {
            switchPlayer();
        }
        
        // Notify listeners
        if (gameListener != null) {
            gameListener.onGameStateChanged();
        }
        
        return true;
    }
    
    /**
     * Gets the player at the specified cell.
     * 
     * @param row The row (0-2)
     * @param col The column (0-2)
     * @return The player (X or O) at the cell, or null if empty
     */
    public Player getCell(int row, int col) {
        return board[row][col];
    }
    
    /**
     * Gets the current player whose turn it is.
     * 
     * @return The current player
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }
    
    /**
     * Checks if the game is over (win or draw).
     * 
     * @return true if the game is over, false otherwise
     */
    public boolean isGameOver() {
        return gameOver;
    }
    
    /**
     * Gets the winner of the game.
     * 
     * @return The winning player, or null if no winner
     */
    public Player getWinner() {
        return winner;
    }
    
    /**
     * Gets the winning line coordinates.
     * 
     * @return Array with [startRow, startCol, endRow, endCol] or null if no winning line
     */
    public int[] getWinningLine() {
        return winningLine;
    }
    
    /**
     * Sets a listener for game state changes.
     * 
     * @param listener The listener to set
     */
    public void setGameListener(GameListener listener) {
        this.gameListener = listener;
    }
    
    /**
     * Switches the current player.
     */
    private void switchPlayer() {
        currentPlayer = (currentPlayer == playerX) ? playerO : playerX;
    }
    
    /**
     * Checks if the game has ended (win or draw).
     */
    private void checkGameStatus() {
        // Check rows
        for (int row = 0; row < 3; row++) {
            if (checkLine(row, 0, row, 1, row, 2)) {
                winningLine = new int[]{row, 0, row, 2};
                return;
            }
        }
        
        // Check columns
        for (int col = 0; col < 3; col++) {
            if (checkLine(0, col, 1, col, 2, col)) {
                winningLine = new int[]{0, col, 2, col};
                return;
            }
        }
        
        // Check diagonals
        if (checkLine(0, 0, 1, 1, 2, 2)) {
            winningLine = new int[]{0, 0, 2, 2};
            return;
        }
        
        if (checkLine(0, 2, 1, 1, 2, 0)) {
            winningLine = new int[]{0, 2, 2, 0};
            return;
        }
        
        // Check for draw
        boolean boardFull = true;
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (board[row][col] == null) {
                    boardFull = false;
                    break;
                }
            }
            if (!boardFull) break;
        }
        
        if (boardFull) {
            gameOver = true;
            winner = null; // Draw
        }
    }
    
    /**
     * Checks if three positions form a winning line.
     */
    private boolean checkLine(int row1, int col1, int row2, int col2, int row3, int col3) {
        Player p1 = board[row1][col1];
        Player p2 = board[row2][col2];
        Player p3 = board[row3][col3];
        
        if (p1 != null && p1 == p2 && p2 == p3) {
            gameOver = true;
            winner = p1;
            return true;
        }
        
        return false;
    }
    
    /**
     * Interface for listening to game state changes.
     */
    public interface GameListener {
        void onGameStateChanged();
    }
}