import java.util.LinkedList;
import java.util.List;

public class Scores {
    private List<Integer> scores;

    public Scores() {
        scores = new LinkedList<Integer>();
    }

    /**
     * Mainly used for testing purposes
     * 
     * @return a copy of the scores LinkedList
     */
    public LinkedList<Integer> getScores() {
        LinkedList<Integer> scoresCopy = new LinkedList<Integer>();
        scoresCopy.addAll(scores);
        return scoresCopy;
    }

    /**
     * Inserts the passed in score in the correct index in the LinkedList so the LinkedList
     * is ascending
     * 
     * @param score time elapsed (the lower times at the front of the list)
     */
    public void insertScore(int score) {
        int index = 0;
        int target = scores.size() + 1;
        for (Integer x : scores) {
            if (score <= x) {
                target = index;
                break;
            }
            index++;
        }
        if (target == scores.size() + 1) {
            scores.add(score);
        } else {
            scores.add(target, score);
        }
    }

    /**
     * Gets first three indices of the scores LinkedList to display top three scores
     * Also handles the case with less than three scores
     * 
     * @return LinkedList of the top three scores.
     */
    public LinkedList<Integer> getTopThree() {

        LinkedList<Integer> topThree = new LinkedList<Integer>();
        if (scores.size() >= 3) {
            topThree.add(scores.get(0));
            topThree.add(scores.get(1));
            topThree.add(scores.get(2));
        } else {
            topThree.addAll(scores);
        }

        return topThree;
    }

}