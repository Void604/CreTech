/**
 * Represents a player in the Tic Tac Toe game.
 */
public class Player {
    private final char symbol; // 'X' or 'O'
    
    /**
     * Constructor initializes a player with the given symbol.
     * 
     * @param symbol The player's symbol ('X' or 'O')
     */
    public Player(char symbol) {
        this.symbol = symbol;
    }
    
    /**
     * Gets the player's symbol.
     * 
     * @return The player's symbol ('X' or 'O')
     */
    public char getSymbol() {
        return symbol;
    }
    
    @Override
    public String toString() {
        return String.valueOf(symbol);
    }
}