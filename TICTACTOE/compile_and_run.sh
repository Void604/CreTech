#!/bin/bash

# Create bin directory if it doesn't exist
mkdir -p bin

# Compile Java files
javac -d bin src/*.java

# Check if compilation was successful
if [ $? -eq 0 ]; then
    echo "Compilation successful! Running the game..."
    java -cp bin TicTacToeMain
else
    echo "Compilation failed. Please fix the errors and try again."
fi