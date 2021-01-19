import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class CardGraphics {
    public static final String[] IMG_FILE_CARDS = { "files/strawberry.jpeg", "files/banana.jpeg", 
        "files/honeydew.jpeg",
        "files/watermelon.jpeg", "files/cherry.jpeg", "files/grapes.jpeg", "files/apple.jpeg", 
        "files/orange.jpeg",
        "files/mango.jpeg", "files/pear.jpeg", "files/peach.jpeg", "files/lemon.jpeg", 
        "files/blueberry.jpeg",
        "files/kiwi.jpeg", "files/pineapple.jpeg", "files/raspberry.jpeg", 
        "files/blackberry.jpeg",
        "files/cantaloupe.jpeg" };

    private static BufferedImage imgFruit1;
    private static BufferedImage imgFruit2;
    private static BufferedImage imgFruit3;
    private static BufferedImage imgFruit4;
    private static BufferedImage imgFruit5;
    private static BufferedImage imgFruit6;
    private static BufferedImage imgFruit7;
    private static BufferedImage imgFruit8;
    private static BufferedImage imgFruit9;
    private static BufferedImage imgFruit10;
    private static BufferedImage imgFruit11;
    private static BufferedImage imgFruit12;
    private static BufferedImage imgFruit13;
    private static BufferedImage imgFruit14;
    private static BufferedImage imgFruit15;
    private static BufferedImage imgFruit16;
    private static BufferedImage imgFruit17;
    private static BufferedImage imgFruit18;

    private BufferedImage[] imgArray = { imgFruit1, imgFruit2, imgFruit3, imgFruit4, imgFruit5, 
        imgFruit6, imgFruit7,
        imgFruit8, imgFruit9, imgFruit10, imgFruit11, imgFruit12, imgFruit13, imgFruit14, 
        imgFruit15, imgFruit16,
        imgFruit17, imgFruit18 };

    /**
     * Uses the bufferedimages to load in images when the player plays the game 
     * Loads the correct image 
     * 
     * @param g allows us to draw an image 
     * @param i where to draw card
     * @param j where to draw card
     * @param card the value of the card that the player picked -- this determines which fruit
     * gets drawn 
     */
    public void loadImages(Graphics g, int i, int j, int card) {

        try {
            if (imgArray[card - 1] == null) {
                imgArray[card - 1] = ImageIO.read(new File(IMG_FILE_CARDS[card - 1]));
            }
        } catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
        }
        g.drawImage(imgArray[card - 1], 10 + 100 * j, 5 + 100 * i, 80, 90, null);
    }

}