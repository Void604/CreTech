# Java Tic Tac Toe Game

A simple Tic Tac Toe game implemented in Java using Swing for the GUI.

## Features

- Interactive 3x3 game board
- Two-player gameplay (X and O)
- Win detection for horizontal, vertical, and diagonal matches
- Draw detection when the board is full
- Game state tracking and turn management
- Option to restart the game

## How to Run

1. Ensure you have Java Development Kit (JDK) installed
2. Compile the Java files:
   ```
   javac src/*.java -d bin
   ```
3. Run the application:
   ```
   java -cp bin TicTacToeMain
   ```

## Game Rules

1. The game is played on a 3x3 grid
2. Players take turns placing their symbol (X or O) on an empty cell
3. The first player to get 3 of their symbols in a row (horizontally, vertically, or diagonally) wins
4. If all cells are filled and no player has won, the game ends in a draw

## Project Structure

- `TicTacToeMain.java`: Entry point of the application
- `TicTacToeFrame.java`: Main frame that sets up the UI components
- `GamePanel.java`: Panel that renders the game board and handles mouse input
- `GameModel.java`: Model class that represents the game state and logic
- `Player.java`: Class representing a player (X or O)