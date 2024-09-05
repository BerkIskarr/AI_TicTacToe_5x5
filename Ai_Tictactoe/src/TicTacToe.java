import java.util.*;

// Class responsible for main in which sets up all the objects and functions
public class TicTacToe {

    public static void main(String[] args) {
        // Initiate the objects
        AIplayer AI= new AIplayer(); 
        Board b = new Board();
        Point p = new Point(0, 0);
        // Did not use, since never needed
        Random rand = new Random();

        // Display the initial look of the board all points being empty
        b.displayBoard();
        int choice = -12345;
        // To check if the entered input is valid, 1 and 2 are being only valid inputs, for error handling, if yes continues
        boolean isValid = false;
        while(!isValid) {
            try {
                System.out.println("\n" + "-".repeat(40) + "\n");
                System.out.println("Who makes first move? (1)Computer (2)User: ");

                // Read the user's choice
                choice = b.scan.nextInt();
                // If either 1 or 2 entered means valid and can leave the loop
                if (choice == 1 || choice == 2) isValid = true;
                else {
                    // In case another number is entered
                    System.out.println("\n"+"-".repeat(40)+"\n");
                    System.out.println("Invalid move. The cell is already occupied or out of range. Try Again!!");
                }
            // In case another data type entered
            }catch (InputMismatchException e) {
                System.out.println("\n"+"-".repeat(40)+"\n");
                System.out.println("Invalid input. Please enter numbers only. Try Again!!");

                b.scan.next(); // Consume the incorrect input
            }
        }
        // For better design
        System.out.println("\n"+"-".repeat(40)+"\n");
        // If Ai's turn
        if(choice == 1){
            // Do the minimax
            AI.callMinimax(0, 1, b, Integer.MIN_VALUE, Integer.MAX_VALUE);

        // Display all the point and scores
	    for (PointsAndScores pas : AI.rootsChildrenScores) {
	        System.out.println("Point: " + pas.point + " Score: " + pas.score);
	    }
            // Ai makes the initial move
            b.placeAMove(AI.returnBestMove(), 1);
            b.displayBoard();
        }

        // Till the game ends
        while (!b.isGameOver()) {
            // Again for error handling
            isValid = false;
            Point userMove = new Point(-1, -1); // Initialize with an invalid point

            while (!isValid) {
                System.out.println("\n"+"-".repeat(40)+"\n");
                System.out.println("Your move: row (1-5) column (1-5)");

                try {
                    int row = b.scan.nextInt() - 1; // Read and adjust row for 0-based index
                    int col = b.scan.nextInt() - 1; // Read and adjust column for 0-based index
                    userMove.x = row;
                    userMove.y = col;
                    if (row >= 0 && row < 5 && col >= 0 && col < 5 && b.getState(userMove) == 0) {
                        isValid = true; // Valid move
                    } else {
                        System.out.println("\n"+"-".repeat(40)+"\n");
                        System.out.println("Invalid move. The cell is already occupied or out of range. Try Again!!");

                    }
                } catch (InputMismatchException e) {
                    System.out.println("\n"+"-".repeat(30)+"\n");
                    System.out.println("Invalid input. Please enter numbers only. Try Again!!");

                    b.scan.next(); // Consume the incorrect input
                }
            }System.out.println("\n"+"-".repeat(40)+"\n");

            // Place the user's move on the board
	        b.placeAMove(userMove, 2);

            b.displayBoard();
            
            if (b.isGameOver()) {
                break;
            }

            AI.callMinimax(0, 1, b, Integer.MIN_VALUE, Integer.MAX_VALUE);

            for (PointsAndScores pas : AI.rootsChildrenScores) {
                System.out.println("Point: " + pas.point + " Score: " + pas.score);
            }
            b.placeAMove(AI.returnBestMove(), 1); // Check if the game is over after the user's move
            b.displayBoard();
        }
        // Determine and display the game outcome
        if (b.hasXWon()) {
            System.out.println("Unfortunately, you lost!");
        } else if (b.hasOWon()) {
            System.out.println("You win!");
        } else {
            System.out.println("It's a draw!");
        }
    }    
}