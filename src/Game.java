
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 *
 */
public class Game implements Runnable {
    public void run() {

        // Top-level frame in which game components live
        final JFrame frame = new JFrame("Memory");
        frame.setLocation(100, 100);

        // Status panel
        final JPanel status_panel = new JPanel();
        frame.add(status_panel, BorderLayout.SOUTH);
        final JLabel status = new JLabel("Welcome!");
        status_panel.add(status);

        // High Scores
        final JPanel highScores = new JPanel();
        frame.add(highScores, BorderLayout.EAST);
        final JTextArea scores = new JTextArea("Top Three Scores:\n");
        highScores.add(scores);

        // Game board
        final GameBoard board = new GameBoard(status, scores);
        status.setVisible(false);

        // Instructions
        final JPanel instructions = new JPanel();
        frame.add(instructions, BorderLayout.CENTER);
        final JTextArea instr = new JTextArea("Welcome to Memory! \n\n" 
                + "You will flip over two cards at a time by pressing on the card. \n" 
                + "If the cards match, those cards"
                + " will stay face up. \n" + "If they don't, the cards will be flipped back over "
                        + "once you flip over your next card.\n"
                + "It is your job to remember what cards are in what location. \n"
                + "Try to match all the cards in the least amount of time possible!\n\n"
                + "You can also save your games and load them later! \n(Note that if you flipped "
                + "over two cards that were not matched, those won't be saved)\n\n"
                + "Time will start when you press Start\n\n" + "Good luck and have fun!", 36, 42);
        JScrollPane scrollPane = new JScrollPane(instr);
        instructions.add(scrollPane);

        // start button allows the user to interact with the game board and starts the
        // time
        final JButton start = new JButton("Start");
        start.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                instructions.setVisible(false);
                status.setVisible(true);
                frame.add(board, BorderLayout.CENTER);
                // Starts the game and starts the time
                board.reset();
            }
        });

        instructions.add(start);

        // Panel that holds reset, save and load
        final JPanel control_panel = new JPanel();
        frame.add(control_panel, BorderLayout.NORTH);

        // Reset Functionality
        final JButton reset = new JButton("Start Over");
        reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                board.reset();
            }
        });

        control_panel.add(reset);

        // Save button functionality
        final JButton saveGame = new JButton("Save Game");
        saveGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // allows the users to insert a file name to save it to
                String saveFile = JOptionPane.showInputDialog(null,
                        "Enter a file name to save " + "your game (ex: newFile)", "", 
                        JOptionPane.PLAIN_MESSAGE);
                if (saveFile != null) {
                    board.save("files/" + saveFile + ".txt");
                }
            }
        });
        control_panel.add(saveGame);

        // load button functionality
        final JButton loadGame = new JButton("Load Game");
        loadGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // allows the users to load in any file -- exceptions handled in loadGame
                String loadFile = JOptionPane.showInputDialog(null,
                        "What file do you want to " + "load from? (ex: newFile)", "", 
                        JOptionPane.PLAIN_MESSAGE);
                if (loadFile != null) {
                    board.loadGame("files/" + loadFile + ".txt");
                }
            }
        });
        control_panel.add(loadGame);

        // Put the frame on the screen
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }

    /**
     * Main method run to start and run the game. Initializes the GUI elements
     * specified in Game and runs it. IMPORTANT: Do NOT delete! You MUST include
     * this in your final submission.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Game());
    }
}