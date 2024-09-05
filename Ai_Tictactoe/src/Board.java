import java.util.*;

// Class representing each cell on the board
class Point {
    int x, y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
    // Increase point's display with 1-based indexing since index starts from 0
    @Override
    public String toString() {
        return "[" + (x+1) + ", " + (y+1) + "]";
    }
}

// Associates a score with a specific point on the board.
class PointsAndScores {
    int score;
    Point point;

    PointsAndScores(int score, Point point) {
        this.score = score;
        this.point = point;
    }
}

// Manages the game board, including tracking available moves, checking win conditions, and displaying the board state.
class Board {
    List<Point> availablePoints;
    Scanner scan = new Scanner(System.in);

    // Board as 2D array
    int[][] board = new int[5][5];

    // construction
    public Board() {
    }

    // Check if the game has ended by checking if any player has won or, for draw, any empty cell left
    public boolean isGameOver() {
        return (hasXWon() || hasOWon() || getAvailablePoints().isEmpty());
    }

    // Checks if Ai has won by checking each line and diagonal
    public boolean hasXWon() {
        boolean left_to_right_diagonal = true;
        boolean right_to_left_diagonal = true;
        // Check both diagonals
        for (int i = 0; i < 5; i++) {
            if (board[i][i] != 1)
                left_to_right_diagonal = false;
            if (board[i][4 - i] != 1)
                right_to_left_diagonal = false;
        }
        // If won in diagonals return win for ai
        if(left_to_right_diagonal || right_to_left_diagonal)
            return true;

        // Check each row and column to check if win
        // Nested loop to check each cell
        for (int i = 0; i < 5; i++) {
            boolean check_row_win = true;
            boolean check_column_win = true;
            for (int j = 0; j < 5; j++) {
                if (board[j][i] != 1)
                    check_column_win = false;
                if (board[i][j] != 1)
                    check_row_win = false;
            }
            // If win
            if (check_row_win || check_column_win)
                return true;
        }
        // Could not win
        return false;

    }

    // Checks if user has won by checking each line and diagonal
    public boolean hasOWon() {
        boolean left_to_right_diagonal = true;
        boolean right_to_left_diagonal = true;
        // Check both diagonals
        for (int i = 0; i < 5; i++) {
            if (board[i][i] != 2)
                left_to_right_diagonal = false;
            if (board[i][4 - i] != 2)
                right_to_left_diagonal = false;
        }
        // If won in diagonals return win for user
        if(left_to_right_diagonal || right_to_left_diagonal)
            return true;

        // Check each row and column to check if win
        // Nested loop to check each cell
        for (int i = 0; i < 5; i++) {
            boolean check_row_win = true;
            boolean check_column_win = true;
            for (int j = 0; j < 5; j++) {
                if (board[j][i] != 2)
                    check_column_win = false;
                if (board[i][j] != 2)
                    check_row_win = false;
            }
            // User won
            if (check_row_win || check_column_win)
                return true;
        }
        return false;

    }

    // Returns a list of all unoccupied cells
    public List<Point> getAvailablePoints() {
        availablePoints = new ArrayList<>();
        // Iterates through the board to find empty spots
        for (int i = 0; i < 5; ++i) {
            for (int j = 0; j < 5; ++j) {
                // If empty
                if (board[i][j] == 0) {
                    availablePoints.add(new Point(i, j));
                }
            }
        }
        return availablePoints;
    }

    // Returns the state (0, 1, or 2)
    public int getState(Point point){
    	return board[point.x][point.y];
    }

    // Place a move on the board for the player (ai or user)
    public void placeAMove(Point point, int player) {
        board[point.x][point.y] = player;   
    }

    // Displays the current state of the board
    public void displayBoard() {

        System.out.println();
        // Iterates through board and prints depending on who occupied
        for (int i = 0; i < 5; ++i) {
            for (int j = 0; j < 5; ++j) {
                // If AI
 		        if (board[i][j]==1)
                    System.out.print("X ");
                // If User
                else if (board[i][j]==2)
                    System.out.print("O ");
                // If empty
                else
                    System.out.print(". ");
            }
            System.out.println();
        }
    }

}
