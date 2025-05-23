import javax.swing.SwingUtilities;

/**
 * Main class that launches the Tic Tac Toe game.
 */
public class TicTacToeMain {
    public static void main(String[] args) {
        // Launch the game UI on the Event Dispatch Thread
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                TicTacToeFrame gameFrame = new TicTacToeFrame();
                gameFrame.setVisible(true);
            }
        });
    }
}