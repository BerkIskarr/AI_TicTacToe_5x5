import java.util.*;

// The Ai component of the game, makes move by using minimax alpha pruning
class AIplayer {
    List<Point> availablePoints; // The empty cells in which AI can make move

    // Stores potential moves and their scores to determine the best move
    List<PointsAndScores> rootsChildrenScores;
    Board b = new Board();

    public AIplayer() {
    }

    // Among the potential moves returns the point of which score the highest
    public Point returnBestMove() {
        // If there are no scored moves available, cannot make move
        if (rootsChildrenScores.isEmpty()) {
            return null;
        }

        // Initialize variables with the lowest possible value to find the move with the highest score
        int MAX = Integer.MIN_VALUE;
        int best = -1;

        // To find the best score iterate through all scored moves
        for (int i = 0; i < rootsChildrenScores.size(); ++i) {
            // If the current score is higher than the maximum found so far
            if (MAX < rootsChildrenScores.get(i).score) {
                // Update the MAX
                MAX = rootsChildrenScores.get(i).score;
                // Will need the index to return the best point
                best = i;
            }
        }
        return rootsChildrenScores.get(best).point;
    }

    // Returns the minimum score in the list
    // Did not use since already handled inside of minimax
    public int returnMin(List<Integer> list) {
        int min = Integer.MAX_VALUE;
        int index = -1;
        // Iterate through the list to find the minimum value
        for (int i = 0; i < list.size(); ++i) {
            if (list.get(i) < min) {
                min = list.get(i);
                // Will need the index to return the min value
                index = i;
            }
        }
        return list.get(index);
    }

    // Returns the maximum score in the list
    // Did not use since already handled inside of minimax
    public int returnMax(List<Integer> list) {
        int max = Integer.MIN_VALUE;
        int index = -1;
        // Iterate through the list to find the maximum value
        for (int i = 0; i < list.size(); ++i) {
            if (list.get(i) > max) {
                // Assign as max
                max = list.get(i);
                index = i;
            }
        }
        return list.get(index);
    }

    // Resets the list of potential moves and starts the minimax
    public void callMinimax(int depth, int turn, Board b, int alpha, int beta) {
        rootsChildrenScores = new ArrayList<>();
        minimax(depth, turn, b, alpha, beta);
    }

    // Recursive method used by the AI to simulate and evaluate all possible moves from the current game state to determine the best move.
    public int minimax(int depth, int turn, Board b, int alpha, int beta) {
        if (b.hasXWon()) return 1; // Ai win
        if (b.hasOWon()) return -1; // User win
        List<Point> pointsAvailable = b.getAvailablePoints();
        if (pointsAvailable.isEmpty()) return 0; // Draw

        // Dynamic depth adjustment based on the stage of the game for more efficient and accurate ai moves
        int dynamicDepth = calculateDynamicDepth(b, depth);

        // Switch to heuristic evaluation for board states at or beyond a depth of 4 to balance depth of analysis with computational efficiency.
        if (dynamicDepth >= 4)
            return calculate_heuristic(b, turn);

        // Shuffle for variability
        if (depth <= 1) {
            Collections.shuffle(pointsAvailable);
        }

        for (Point point : pointsAvailable) {
            b.placeAMove(point, turn); // try a move
            int currentScore;

            // If AI turn (maximizing player)
            if (turn == 1) {
                currentScore = minimax(depth + 1, 2, b, alpha, beta);
                // Update alpha if higher
                alpha = Math.max(alpha, currentScore);
                // At the root level, store each move and its score to identify the best move later.
                if (depth == 0)
                    rootsChildrenScores.add(new PointsAndScores(currentScore, point));
            }
            // If it's the human player's turn (minimizing player)
            else {
                // Recursively call minimax to evaluate this move.
                currentScore = minimax(depth + 1, 1, b, alpha, beta);
                // Update beta if lower
                beta = Math.min(beta, currentScore);
            }
            // Undo the move to restore the board state for the next iteration.
            b.placeAMove(point, 0);

            // Alpha-beta pruning: stop exploring further branches if the best score possible is already less than the worst score for the opponent.
            if (alpha >= beta) break;
        }

        // Return the best score for the current player: alpha for AI, beta for human.
        return turn == 1 ? alpha : beta;
    }

    // Calculates dynamic depth adjustment based on the stage of the game for more efficient and accurate ai moves
    private int calculateDynamicDepth(Board b, int currentDepth) {
        int emptySpaces = b.getAvailablePoints().size();
        // Increase depth as the number of empty spaces decreases
        if (emptySpaces > 16) return currentDepth; // Early game: shallower depth
        else if (emptySpaces <= 16 && emptySpaces > 8) return currentDepth + 1; // Mid-game: slightly deeper
        else return currentDepth + 2; // Late game: deepest analysis
    }

    /*
    Evaluates the current game board for the given player turn using a heuristic scoring system.
    Points for each:
    Center: 10 points
    4 player 1 empty: 100 points
    3 player 2 empty: 75 points
    4 Opponent 1 empty: 50 points
    3 Opponent 2 empty: 25 points
     */
    public int calculate_heuristic(Board b, int turn) {
        int heuristic_score = 0;

        // Center control
        if (b.board[2][2] == turn) heuristic_score += 10; // Increased center value

        // Rows and Columns Evaluation
        for (int i = 0; i < 5; i++) {
            int count_row = 0, count_column = 0;
            int count_empty_row = 0, count_empty_column = 0;
            int count_opponent_row = 0, count_opponent_column = 0;
            // count for both opponent and the player
            for (int j = 0; j < 5; j++) {

                if (b.board[i][j] == turn) count_row++;
                else if (b.board[i][j] == 0) count_empty_row++;
                else count_opponent_row++;

                if (b.board[j][i] == turn) count_column++;
                else if (b.board[j][i] == 0) count_empty_column++;
                else count_opponent_column++;
            }

            // Adjusted scoring for potential line completions and blocks
            heuristic_score += evaluateLinePotential(count_row, count_empty_row, count_opponent_row);
            heuristic_score += evaluateLinePotential(count_column, count_empty_column, count_opponent_column);
        }

        // Diagonals Evaluation
        int count_main_diagonal = 0, count_enemy_main_diagonal = 0, count_empty_main_diagonal = 0;
        int count_reversed_diagonal = 0, count_enemy_reversed_diagonal = 0, count_empty_reversed_diagonal = 0;

        for(int i = 0; i < 5; i++) {
            // Main Diagonal
            if (b.board[i][i] == turn) count_main_diagonal++;
            else if (b.board[i][i] == 0) count_empty_main_diagonal++;
            else count_enemy_main_diagonal++;

            // Reversed Diagonal
            if (b.board[i][4 - i] == turn) count_reversed_diagonal++;
            else if (b.board[i][4 - i] == 0) count_empty_reversed_diagonal++;
            else count_enemy_reversed_diagonal++;
        }

        // Evaluate main and reversed diagonals
        heuristic_score += evaluateLinePotential(count_main_diagonal, count_empty_main_diagonal, count_enemy_main_diagonal);
        heuristic_score += evaluateLinePotential(count_reversed_diagonal, count_empty_reversed_diagonal, count_enemy_reversed_diagonal);


        return heuristic_score;
    }

    // method to evaluate line potential
    // Points are explained on top
    private int evaluateLinePotential(int count_player, int count_empty, int count_opponent) {
        int score = 0;
        // Checking for a win or near-win condition
        if (count_player > 0 && count_opponent == 0) {
            // Line has potential for the player
            if (count_player == 4 && count_empty == 1) {
                // Direct win condition
                score += 100;
            } else if (count_player == 3 && count_empty == 2) {
                // Strong position but not immediate win
                score += 75;
            }
        } else if (count_opponent > 0 && count_player == 0) {
            // Line has potential for the opponent
            if (count_opponent == 4 && count_empty == 1) {
                // Immediate threat of opponent winning
                score += 50;
            } else if (count_opponent == 3 && count_empty == 2) {
                // Strong position of opponent
                score += 25;
            }
        }
        return score;
    }
}