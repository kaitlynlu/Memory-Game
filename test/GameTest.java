import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedList;

/** 
 *  You can use this file (and others) to test your
 *  implementation.
 */

public class GameTest {
    Scores s;
    Memory m;
    Memory m1;
    GameBoard gb;
    
    @BeforeEach
    public void setUp() {
        s = new Scores();
        m = new Memory();
        m1 = new Memory();
        gb = new GameBoard(null, null);

    }
    
    @Test
    public void getScores() {
        LinkedList<Integer> expectedScores = new LinkedList<Integer>();
        LinkedList<Integer> score = s.getScores();
        assertEquals(expectedScores, score);
        expectedScores.add(6);
        s.insertScore(6);
        assertEquals(expectedScores, s.getScores());
    }
    
    @Test
    public void getScoresEncapsulation() {
        LinkedList<Integer> expectedScores = new LinkedList<Integer>();
        expectedScores.add(2);
        
        s.insertScore(2);
        LinkedList<Integer> score = s.getScores();
        score.add(10);
        assertEquals(expectedScores, s.getScores());
    }
    

    @Test
    public void insertScoresScoresNotInOrder() {
        LinkedList<Integer> expectedScores = new LinkedList<Integer>();
        expectedScores.add(2);
        expectedScores.add(5);
        expectedScores.add(7);
        
        s.insertScore(5);
        s.insertScore(7);
        s.insertScore(2);
        
        assertEquals(expectedScores, s.getScores());
    }
    
    @Test
    public void insertScoresDescendingOrder() {
        LinkedList<Integer> expectedScores = new LinkedList<Integer>();
        expectedScores.add(5);
        expectedScores.add(7);
        
        s.insertScore(7);
        s.insertScore(5);
        
        assertEquals(expectedScores, s.getScores());
    }
    
    @Test
    public void insertScoresScoresInOrderAscending() {
        LinkedList<Integer> expectedScores = new LinkedList<Integer>();
        expectedScores.add(3);
        expectedScores.add(4);
        expectedScores.add(5);
        expectedScores.add(6);
        
        s.insertScore(3);
        s.insertScore(4);
        s.insertScore(5);
        s.insertScore(6);
        
        assertEquals(expectedScores, s.getScores());
    }
    
    @Test
    public void insertScoresSameScores() {
        LinkedList<Integer> expectedScores = new LinkedList<Integer>();
        expectedScores.add(2);
        expectedScores.add(2);
        expectedScores.add(2);
        
        s.insertScore(2);
        s.insertScore(2);
        s.insertScore(2);
        
        assertEquals(expectedScores, s.getScores());
    }
    
    @Test
    public void lessThanThreeScoresTopThree() {
        LinkedList<Integer> expectedScores = new LinkedList<Integer>();
        expectedScores.add(2);
        expectedScores.add(5);
        
        s.insertScore(5);
        s.insertScore(2);
        
        assertEquals(expectedScores, s.getTopThree());
    }
    
    @Test
    public void moreThanThreeScoresTopThree() {
        LinkedList<Integer> expectedScores = new LinkedList<Integer>();
        expectedScores.add(2);
        expectedScores.add(3);
        expectedScores.add(5);
        
        s.insertScore(5);
        s.insertScore(2);
        s.insertScore(10);
        s.insertScore(12);
        s.insertScore(3);
        
        assertEquals(expectedScores, s.getTopThree());
    }
    
    @Test
    public void topThreeEncapsulation() {
        LinkedList<Integer> expectedScores = new LinkedList<Integer>();
        expectedScores.add(2);
        expectedScores.add(3);
        expectedScores.add(5);
        
        s.insertScore(5);
        s.insertScore(2);
        s.insertScore(10);
        s.insertScore(12);
        s.insertScore(3);
        
        LinkedList<Integer> top = s.getTopThree();
        top.add(10);
        
        assertEquals(expectedScores, s.getTopThree());
    }
    
    
    @Test
    public void saveFile() {
        
        gb.save("files/testFile.txt");
        //first entry should be 0-__
        try {
            @SuppressWarnings("resource")
            BufferedReader br = new BufferedReader(new FileReader("files/testFile.txt"));
            String[] line = br.readLine().split("-");
            assertEquals("0", line[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    @Test
    public void loadFile() {
        
        gb.loadGame("files/testLoad.txt");
        //make sure the array is updated correctly
        assertEquals(0, gb.getCell(0, 0));
        assertEquals(2, gb.getCell(1, 2));
        assertEquals(8, gb.getCard(0, 0));
        assertEquals(14, gb.getCard(1, 0));

    }
    
    @Test
    public void getCell() {
        assertEquals(0, m.getCell(3, 2));
    }
    
    @Test
    public void setCell() {
        assertEquals(0, m.getCell(3, 2));
        m.setCell(3, 2, 2);
        assertEquals(2, m.getCell(3, 2));
    }
    
    @Test
    public void setCellOutOfBound() {
        m.setCell(9, 5, 2);
        boolean same = true; 
        
        //all entries should still be 0
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                if (m.getCell(i, j) != 0) {
                    same = false;
                }
            }
        }
        
        assertTrue(same);
    }
    
    @Test
    public void setCard() {
        m.setCard(0, 0, 10);
        assertEquals(10, m.getCard(0, 0));
    }
    
    
    @Test
    public void playTurnNormalMatching() {
        //make the first and second card match by changing the values of card
        m.setCard(0, 0, 0);
        m.setCard(1, 1, 0);
        
        
        m.playTurn(0, 0);
        assertEquals(1, m.getCell(0, 0));
        assertEquals(0, m.getCell(1, 1));

        
        m.playTurn(1, 1);
        //since they match, turn both to 2
        assertEquals(2, m.getCell(0, 0));
        assertEquals(2, m.getCell(1, 1));

    }
    
    @Test
    public void playTurnNormalNotMatching() {
        //make the first and second card not match by changing the values of card
        m.setCard(0, 0, 0);
        m.setCard(1, 1, 1);
         
        m.playTurn(0, 0);
        assertEquals(1, m.getCell(0, 0));
        assertEquals(0, m.getCell(1, 1));
        
        m.playTurn(1, 1);
        //since they don't match, the values are turned to 1 instead of 2
        assertEquals(1, m.getCell(0, 0));
        assertEquals(1, m.getCell(1, 1));

    }

    @Test
    public void playTurnTryCardAlreadyFlippedAndMatch() {
        //make the first and second card match by changing the values of card
        m.setCard(0, 0, 0);
        m.setCard(1, 1, 0);
   
      //turns are played and they match
        m.playTurn(0, 0);
        m.playTurn(1, 1);
        
        int expected = m.getCell(0, 0);
        int expected1 = m.getCell(1, 1);
        
        m.playTurn(0, 0);
        //board should not have changed because both cards are currently flipped to the front
        assertEquals(expected, m.getCell(0, 0));
        assertEquals(expected1, m.getCell(1, 1));

    }
    
    @Test
    public void playTurnTryCardAlreadyFlippedAndNotMatched() {
        //make the first and second card not match by changing the values of card
        m.setCard(0, 0, 0);
        m.setCard(1, 1, 1);
   
      //turns are played and they match
        m.playTurn(0, 0);
        m.playTurn(1, 1);

        int expected = m.getCell(0, 0);
        int expected1 = m.getCell(1, 1);
        
        m.playTurn(0, 0);
        //board should not have changed because both cards are currently flipped to the front
        assertEquals(expected, m.getCell(0, 0));
        assertEquals(expected1, m.getCell(1, 1));

    }
    
    @Test
    public void playTurnDoesNotChangeCardMatchings() {
        //make sure the actual matchings (cards[][]) do not change
        int expected = m.getCard(0, 0);
        int expected1 = m.getCard(2, 3);
                
        m.playTurn(0, 0);
        assertEquals(expected, m.getCard(0, 0));
        m.playTurn(2, 3);
        assertEquals(expected1, m.getCard(2, 3));
 
    }
    
    @Test
    public void playTurnSwitchTurns() {
      
        assertTrue(m.getTurn());
        m.playTurn(0, 0);

        //should be second turn after playing
        assertFalse(m.getTurn());
        
        m.playTurn(1, 1);

        //should be first turn again
        assertTrue(m.getTurn());
        
        m.playTurn(5, 2);
        //second turn
        assertFalse(m.getTurn());
          
    }
    
    @Test
    public void checkWinnerNoWinner() {
        assertFalse(m.checkWinner());
        
        //make the first and second card match by changing the values of card
        m.setCard(0, 0, 0);
        m.setCard(1, 1, 0);
        m.playTurn(0, 0);
        m.playTurn(1, 1);
        
        assertFalse(m.checkWinner()); // still no winner despite one match
       

    }
    
    @Test
    public void checkWinnerExists() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                m.setCell(i, j, 2);
            }
        }
        
        assertTrue(m.checkWinner()); // all 2 so winner
    }
    
    @Test
    public void resetBoard() {
        m.setCard(0, 0, 0);
        m.setCard(1, 1, 0);
        m.playTurn(0, 0);
        m.playTurn(1, 1);
        //those cells are now set as 2
        
        m.reset();
        //reset back to 0
        assertEquals(0, m.getCell(0, 0));
        assertEquals(0, m.getCell(1, 1));
    }
    
    @Test
    public void resetChangeCards() {
        //check cards are diff each time we reset (though there exists a very very slim chance
        //they are the same since its a random process)
        ArrayList<Integer> first = new ArrayList<Integer>();
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                first.add(m.getCard(i, j));
            }
        }
        m.reset();
        
        ArrayList<Integer> second = new ArrayList<Integer>();
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                second.add(m.getCard(i, j));
            }
        }
        
        
        assertFalse(first.equals(second));
    }
    

}
