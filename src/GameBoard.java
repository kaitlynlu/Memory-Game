
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.LinkedList;

import javax.imageio.ImageIO;
import javax.swing.*;

@SuppressWarnings("serial")
public class GameBoard extends JPanel {

    private Memory m; // model for the game
    private JLabel status; // current status text
    private int timeElapsed = 0;
    private Scores s;
    private JTextArea scores;
    private CardGraphics cg;

    // Game constants
    public static final String IMG_FILE = "files/back of card.png";

    public static final int BOARD_WIDTH = 600;
    public static final int BOARD_HEIGHT = 600;
    public static final int INTERVAL = 1000;

    private static BufferedImage img;

    /**
     * Initializes the game board.
     */
    public GameBoard(JLabel statusInit, JTextArea scoresInit) {
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        setFocusable(true);

        m = new Memory(); // initializes model for the game
        s = new Scores(); // initializes the scores for the game
        status = statusInit; // initializes the status JLabel
        scores = scoresInit; // initializes the scores JLabel

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                Point p = e.getPoint();

                // updates the model given the coordinates of the mouseclick
                int p1 = p.x / 100;
                int p2 = p.y / 100;
                m.playTurn(p1, p2);

                updateWhenWinner(); // updates the status when there is a winner
                repaint(); // repaints the game board
            }
        });

        cg = new CardGraphics();

        /*
         * reading the image
         */
        try {
            if (img == null) {
                img = ImageIO.read(new File(IMG_FILE));
            }
        } catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
        }

        /*
         * Creating the timer used for high scores
         */
        Timer timer = new Timer(INTERVAL, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tick();
            }
        });
        timer.start();

    }

    /**
     * (Re-)sets the game to its initial state.
     */
    public void reset() {
        timeElapsed = 0;
        status.setText("Time Elapsed: " + timeElapsed + " seconds");
        m.reset();
        repaint();

        // Makes sure this component has keyboard/mouse focus
        requestFocusInWindow();
    }

    /**
     * Displays the time elapsed in seconds at the bottom of the screen
     */
    void tick() {
        if (!m.checkWinner()) {
            int newTime = timeElapsed++;
            status.setText("Time Elapsed: " + newTime + " seconds");

            // update the display
            repaint();
        }

    }

    /**
     * Updates the JLabel when the user wins. If the user wins, they get a message
     * that says congrats and their score (if in the top 3), will be updated in the
     * high score text area
     */
    private void updateWhenWinner() {
        boolean winner = m.checkWinner();
        if (winner) {
            s.insertScore(timeElapsed);

            status.setText("Congrats!!!");

            // sets the JTextArea to update the top three scores
            LinkedList<Integer> top = s.getTopThree();
            String scoresStr = "Top Three Scores: \n";
            int index = 1;
            for (int x : top) {
                scoresStr += index + ", " + x + " seconds \n";
                index++;
            }
            scores.setText(scoresStr);
        }

    }

    /**
     * Saves the game state to a file of the user's choosing. The user's progress in
     * the game is saved in 36 lines then the value of the cards for each cell is
     * saved next to the user's progress separated with a dash (-). The last line is
     * the time elapsed
     * 
     * @param boardFilePath the file that the user wants to save the game to
     */
    public void save(String boardFilePath) {
        File boardFile = Paths.get(boardFilePath).toFile();
        BufferedWriter bwBoard;

        try {
            bwBoard = new BufferedWriter(new FileWriter(boardFile, false));

            for (int i = 0; i < m.getBoardLength(); i++) {
                for (int j = 0; j < m.getBoardLength(); j++) {
                    if (m.getCell(i, j) == 1) {
                        bwBoard.write(0 + "-" + m.getCard(i, j) + "\n");
                    } else {
                        bwBoard.write(m.getCell(i, j) + "-" + m.getCard(i, j) + "\n");
                    }
                }
            }
            bwBoard.write(timeElapsed + "");
            bwBoard.close();

        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error", "Save Game Error", 
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * loads the file back in parsing and separating the lines based on dashes to
     * get the correct information
     * 
     * @param boardFilePath the file the user wants to load their game in from
     *                      Throws an exception if the file does not exist or the
     *                      file is buggy
     */
    public void loadGame(String boardFilePath) {
        File boardFile = Paths.get(boardFilePath).toFile();
        BufferedReader brBoard;
        try {
            brBoard = new BufferedReader(new FileReader(boardFile));
            for (int i = 0; i < m.getBoardLength(); i++) {
                for (int j = 0; j < m.getBoardLength(); j++) {
                    String[] line = brBoard.readLine().split("-");
                    m.setCard(i, j, Integer.parseInt(line[1]));
                    m.setCell(i, j, Integer.parseInt(line[0]));
                }
            }
            timeElapsed = Integer.parseInt(brBoard.readLine());
            m.setFirstTurn();
            repaint();

        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "File Does Not Exist :(", "Load Game Error",
                    JOptionPane.INFORMATION_MESSAGE);
            
            //dealing with files in the wrong format below
        } catch (NullPointerException e3) {
            reset();
            JOptionPane.showMessageDialog(null, "File Empty. Cannot Load. Game will be reset", 
                    "Load Game Error",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e2) {
            e2.printStackTrace();
        } catch (Exception e) {
            reset();
            JOptionPane.showMessageDialog(null, "File Corrupted. Cannot Load. "
                        + "Game will be reset", 
                        "Load Game Error",
                        JOptionPane.INFORMATION_MESSAGE);

        } 

    }
    
    /*
     * For testing purposes
     */
    public int getCell(int c, int r) {
        return m.getCell(c, r);
    }
    
    /*
     * For testing purposes
     */
    public int getCard(int c, int r) {
        return m.getCard(c, r);
    }

    /**
     * Draws the game board.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draws board grid
        for (int i = 100; i < 600; i += 100) {
            g.drawLine(i, 0, i, 600);
            g.drawLine(0, i, 600, i);
        }

        // the backs of the cards
        for (int j = 5; j < 600; j += 100) {
            for (int i = 10; i < 600; i += 100) {
                g.drawImage(img, i, j, 80, 90, null);
            }
        }

        // always draw what the person picks but if it doesn't match, the drawing
        // goes away and if it does match, the drawing stays
        changeGrid(g);
    }

    /*
     * calls the loadImages method to load in the fruit images as the user is
     * pressing the cards
     */
    public void changeGrid(Graphics g) {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                int state = m.getCell(j, i);
                int card = m.getCard(j, i);
                if (state == 1 || state == 2) {
                    cg.loadImages(g, i, j, card);

                }
            }
        }
    }

    /**
     * Returns the size of the game board.
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
    }
}