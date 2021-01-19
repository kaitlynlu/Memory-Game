
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * This class is a model for TicTacToe.
 * 
 * This game adheres to a Model-View-Controller design framework. This framework
 * is very effective for turn-based games. We STRONGLY recommend you review
 * these lecture slides, starting at slide 8, for more details on
 * Model-View-Controller:
 * https://www.seas.upenn.edu/~cis120/current/files/slides/lec37.pdf
 * 
 * This model is completely independent of the view and controller. This is in
 * keeping with the concept of modularity! We can play the whole game from start
 * to finish without ever drawing anything on a screen or instantiating a Java
 * Swing object.
 * 
 * Run this file to see the main method play a game of TicTacToe, visualized
 * with Strings printed to the console.
 */
public class Memory {

    private int[][] board;
    private int[][] cards;
    private int firstCard;
    private int secondCard;
    private boolean firstTurn;
    private int firstCol;
    private int firstRow;

    /**
     * Constructor sets up game state.
     */
    public Memory() {
        reset();
    }

    /**
     * playTurn allows players to play a turn. if it is the first turn, then set the 2D 
     * array location to 1. if it is not the first turn then check if the player found a matching 
     * pair with what they chose in the first term. If it was matching, set both cells of 
     * the array to 2 and if not, set the second turn location to 1 
     * 
     * 
     * @param c column to play in
     * @param r row to play in
     * 
     *          
     */
    public void playTurn(int c, int r) {
        if (board[r][c] == 0) {
            if (firstTurn) {

                for (int i = 0; i < board.length; i++) {
                    for (int j = 0; j < board[i].length; j++) {
                        if (board[i][j] == 1) {
                            // sets the values that were 1 back to 0 so they disappear from 
                            //the board
                            board[i][j] = 0;
                        }
                    }
                }
                // store the state of the first card flipped over
                board[r][c] = 1;
                firstCol = c;
                firstRow = r;
                firstCard = cards[r][c];

            } else { // if not the first turn then have to check if they are the same
                secondCard = cards[r][c];
                if (firstCard == secondCard) {
                    board[r][c] = 2;
                    board[firstRow][firstCol] = 2;
                } else {
                    board[r][c] = 1;
                }

            }
            // if the player hasn't won switch turns 
            if (!checkWinner()) {
                firstTurn = !firstTurn;
            }
        }
    }

    /**
     * checkWinner checks whether the game has reached a win condition.
     * 
     * @return true if the player wins
     */
    public boolean checkWinner() {
        // Check if the 2D board array is all 2
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] != 2) {
                    return false;
                }
            }
        }
        return true;
    }

    

    /**
     * reset (re-)sets the game state to start a new game. The 2D array for the
     * board is set to all 0s The 2D array of the cards is shuffled randomly
     */
    public void reset() {
        board = new int[6][6];
        cards = new int[6][6];

        int[] nums = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 
            1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11,
            12, 13, 14, 15, 16, 17, 18 };

        List<Integer> numbers = new LinkedList<>();
        for (Integer n : nums) {
            numbers.add(n);
        }
        Collections.shuffle(numbers);

        int t = 0;
        for (int i = 0; i < cards.length; i++) {
            for (int j = 0; j < cards[i].length; j++) {
                cards[i][j] = numbers.get(t);
                t++;
            }
        }
        setFirstTurn();
    }

    public void setFirstTurn() {
        firstTurn = true;
    }
    
    //gets whether or not its first turn for testing purposes
    public boolean getTurn() {
        return firstTurn;
    }

    /**
     * getCell is a getter for the contents of the cell specified by the method
     * arguments.
     * 
     * @param c column to retrieve
     * @param r row to retrieve
     * @return an integer denoting the contents of the corresponding cell on the
     *         game board. 0 = empty, 1 = Picked card but not matched, 2 = Cards
     *         that were matched
     */
    public int getCell(int c, int r) {
        return board[r][c];
    }
    

    /**
     * getCard is a getter for the contents of the cell specified of the cards 2D
     * array.
     * 
     * @param c column to retrieve
     * @param r row to retrieve
     * @return an integer denoting the contents of the corresponding cell of the
     *         cards 2D array Result will be an integer
     */
    public int getCard(int c, int r) {
        return cards[r][c];
    }

    /**
     * setCell sets the values of the cells of the board 2D array outside of
     * Memory.java to ensure encapsulation. This is used to load the user's saved
     * games
     * 
     * @param c column to set
     * @param r row to set
     * @param x the value to set that column and row to
     */
    public void setCell(int c, int r, int x) {
        if (r <= board.length && c <= board.length) {
            board[r][c] = x;
        } 
    }

    /**
     * setCard sets the values of the cells of the cards 2D array outside of
     * Memory.java to ensure encapsulation. This is used to load the user's saved
     * games
     * 
     * @param c column to set
     * @param r row to set
     * @param x the value to set that column and row to
     */
    public void setCard(int c, int r, int x) {
        if (r <= cards.length && c <= cards.length) {
            cards[r][c] = x;
        } 
    }
    
    public int getBoardLength() {
        return board.length;
    }
}
    
    
   