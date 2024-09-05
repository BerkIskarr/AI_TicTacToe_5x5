# Tic-Tac-Toe 5x5 with AI (Minimax with Alpha-Beta Pruning)

## Overview

This is a command-line Tic-Tac-Toe game for a 5x5 board where a user can play against an AI opponent. The AI uses the **Minimax** algorithm with **alpha-beta pruning** for optimal gameplay, making it a challenging opponent. The game allows the user to choose who plays first: the AI or the user.

## Features

- **Minimax Algorithm**: The AI uses the Minimax algorithm to evaluate all possible game states and choose the best move.
- **Alpha-Beta Pruning**: Improves the efficiency of the Minimax algorithm by eliminating unnecessary branches.
- **Heuristic Evaluation**: A scoring system evaluates the board for the AI to make decisions faster when a depth limit is reached.
- **Dynamic Depth Adjustment**: Adjusts the depth of the Minimax search dynamically based on the stage of the game for efficient play.
- **5x5 Board**: The game uses a 5x5 grid, providing more complexity than the traditional 3x3 version.
- **Player vs AI**: The user can play against an AI that adapts and makes optimal moves.

## How to Play

1. **Choose First Player**: At the start of the game, you'll be prompted to choose who makes the first move: the computer (AI) or you (the player).
2. **User Moves**: The user is prompted to make a move by entering the row and column of their desired move. The rows and columns are indexed from 1 to 5.
3. **AI Moves**: The AI will automatically calculate its best move using the Minimax algorithm and place it on the board.
4. **Game End**: The game continues until either the user or the AI wins, or the game ends in a draw.

### Win Condition
- The game is won when a player places five of their markers (X or O) in a row, column, or diagonal.

## Running the Game

### Prerequisites
- Java JDK 8 or higher installed on your machine.
- A terminal or command prompt to compile and run the game.

### Steps to Compile and Run:

1. **Clone the Repository** (or copy the files to your local machine):
   ```bash
   git clone <repository-url>
   cd <repository-folder>
    ```
### Example Gameplay
You will be prompted to choose whether the AI or the player goes first.
If you choose AI, it will make the first move, and you will see the board update.
After each move, the updated board will be displayed, and you will be asked for your next move.
   
## Code Explanation
### Classes
#### TicTacToe.java:

Contains the main method, initializes the game board, and manages the user and AI turns.
#### AIplayer.java:

Implements the AI using the Minimax algorithm with alpha-beta pruning. It calculates the optimal move for the AI based on the current state of the board.
#### Board.java:

Manages the game board state, checks for win conditions, and handles moves.
#### Point.java:

Represents a point on the board (row, column).
#### PointsAndScores.java:

Stores potential moves and their scores for the AI to determine the best move.
### Key Methods in AIplayer.java:
returnBestMove(): Selects the best move for the AI based on the scores calculated by the Minimax algorithm.
minimax(): Recursively evaluates all possible game states to determine the optimal move for the AI.
calculate_heuristic(): A heuristic evaluation that scores the board based on potential wins or threats for both players.
### Heuristic Scoring:
The AI evaluates different board configurations based on:
Center control: The center of the board is given a higher value.
Rows and Columns: It evaluates rows and columns based on how many of its own pieces or the opponent's pieces are present.
Diagonals: It evaluates both diagonals for potential wins or threats.
### Game Rules
The board is a 5x5 grid.
The player wins by placing five marks (O) in a row, column, or diagonal.
The AI wins by placing five marks (X) in a row, column, or diagonal.
If the board fills up with no winner, the game ends in a draw.
### Future Enhancements
Difficulty Levels: Add different levels of AI difficulty by adjusting the depth of Minimax.
GUI Version: Develop a graphical user interface for easier interaction.
Multi-Player: Extend the game to allow two players to play instead of player vs AI.
