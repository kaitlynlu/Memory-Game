=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
CIS 120 Game Project README
PennKey: kaitlu
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=

===================
=: Core Concepts :=
===================

- List the four core concepts, the features they implement, and why each feature
  is an appropriate use of the concept. Incorporate the feedback you got after
  submitting your proposal.

  1. 2-D Arrays
  
  I used a 2-D array to represent the cards that the player can choose from and this is a 6x6
  array of integers where each entry in the array is an integer from 1-18 and there are two
  entries in the array that are the same number for a total of 36 entries in the array. I used an
  int array for this because I could use the integers as array indices in CardGraphics.java. The 
  arrays that are the same number denote the matching cards (and the values of each entry of the 
  array are changed each time the player plays a new game so the matching cards can be 
  in different places each time). I also used a 2-D array of integers that 
  represent the cards that the player plays where 0 will be the cards face down, 1 will be the cards 
  that the player flipped over on any given turn to try to match but don't match, and 2 will 
  be the cards that 
  a player flipped over during any given turn and matched. If the array is all 2's, then the player
  won the game. When the game starts, this array will be all 0’s.
  
  This is an appropriate use of 2-D arrays because my cards are arranged in a grid and 2-D arrays
  mimic the actual set up of a Memory game. Depending on what card the player wants to flip over,
  I can easily get the index of the array to check what value the card has.

  2. Collections
  
  I used a LinkedList of integers to store the player's high scores. Originally, I was going to 
  use a TreeSet but after getting feedback, I changed it to a LinkedList. The scores are based 
  on time elapsed so the lower the score the better.
  
  This is an appropriate use of Collections because it stores multiple pieces of data from 
  various games. This is a good use of LinkedLists in particular because since LinkedLists
  have indices, I could loop through the LinkedList and enter each score/time elapsed in the 
  correct index and make sure the LinkedList is still in ascending order. Then, to get the top
  three scores, I could access the first three entries in the LinkedList starting form the head 
  (and I also made sure to account for when there were fewer than three entries in the LinkedList
  of all scores as well).
  

  3. File I/O
  
  I used File I/O to store the game state so it could be saved and reloaded. 
  
  There is a save button that allows the user to save the current state of their board (how
  many cards they've matched and which cards were matched) and also the time elapsed which makes 
  two states of the game. I also save the values of the cards in each index so when the play reloads
  the game, the values of the cards face down don't change and the player can keep playing
  the same exact game they saved. The player can also choose where to save the file and save
  uses a BufferedWriter.
  
  There is also a load button where the user can choose a file to load their game in from. This
  uses a Buffered Reader and parses the files splitting each line by a dash (-) and taking the 
  relevant information to update the board array, cards array and time elapsed. 
  
  This is an appropriate use of File I/O because it stores two states of data and the player
  has the choice to load in any game that they saved. Also, even if the player exits the game 
  they can still load in previous games.

  4. Testable Component
  
  I had JUnit testing on different methods mainly in Memory.java and Scores.java to test various 
  functionalities of my game such as playing a turn or inserting a new score in the LinkedList. 
  I was also able to test for encapsulation and edge cases. Since my model (Memory.java) does not 
  depend on Java Swing GUI, I could test all the methods in that class alone to ensure that it 
  works as desired. 

=========================
=: Your Implementation :=
=========================

- Provide an overview of each of the classes in your code, and what their
  function is in the overall game.
  
  Game.java: This is the top-level frame and widget for the GUI and initializes the "view" part of 
  the MVC framework. This is the class that initializes the reset, save, load and start game 
  buttons and creates the status bar on the bottom and the high scores textArea (initialized
  with no scores to begin with). The game first shows the player the instructions and once the 
  player presses the start game button, the game frame appears, the instructions disappear, 
  the time starts to run and now the player can play the game. 
  
  GameBoard.java: This class is the controller and view of the MVC model and instantiates the 
  Memory object, Scores object and CardGraphics object. When the player starts the game, the 
  game board is reset. 
  This class handles updating the status bar at the bottom. The timer increases the time 
  elapsed by 1 shown at the bottom of the screen every second and when the player wins, it says
  "Congrats". 
  This class handles mouse click events and repainting the game board to 
  match how the player is interacting with the board. This class also has the methods to save and 
  reload the game. The initial game board before the player interacts with it is a 6x6 grid with the 
  image of the back of a card in every cell. Then, as the player clicks on cells, GameBoard.java
  calls a method in CardGraphics.java to update what the board looks like. When the game ends, it 
  calls methods in Scores.java to update the high scores.
  
  Memory.java: Memory.java is the model for the MVC framework and functions independently of the
  GUI. This class has the playTurn and checkWinner functionality and determines how the game is 
  reset. It also allows GameBoard.java to check certain indices of the board and card array and 
  also set the values at a certain index. 
  
  GameTest.java: This is my class that does JUnit testing on various methods in Memory.java and 
  Scores.java.
  
  Scores.java: This class creates the LinkedList of all scores. It has a method to insert a score
  in the correct location depending on the score (LinkedList is ascending) and a method to retrieve
  the first three scores from the front of the list (the top three best scores). This class 
  ensures that the list is encapsulated. 
  
  CardGraphics.java: This class instantiates 18 BufferedImages to draw the images of the 18 
  different fruits. It has a method that draws a different fruit depending on which card was 
  chosen by the player.


- Were there any significant stumbling blocks while you were implementing your
  game (related to your design, or otherwise)?

Since I was pretty unfamiliar with using Java Swing, it was difficult to incorporate the 
instructions in the beginning to allow the user to only start playing the game when they 
press the start game button. Also, in the traditional Memory game, the player flips two cards
at a time and views both of them and if they don't match, flips both of them over. This 
was difficult to implement while repainting the board with my initial design idea
of using 0's in the 2-D array if the card is flipped over and 1 if the cards were matched so I 
changed it to 1 is the cards were flipped over but not matched and 2 if they were matched. 
Then, the player could still see the two cards they flipped over even if they don't match 
and the cards would just be flipped back over afterwards. 


- Evaluate your design. Is there a good separation of functionality? How well is
  private state encapsulated? What would you refactor, if given the chance?

I separated each component of the MVC where Memory.java is able to stand alone without the GUI.
I also made sure the private state was always encapsulated since there are no getters for the
2D arrays and there is no way to modify the LinkedLists (which I tested for in GameTest.java)
I would not refactor anything except maybe it would be interesting to let the player
insert their name to be displayed with their high score. 


========================
=: External Resources :=
========================

- Cite any external resources (libraries, images, tutorials, etc.) that you may
  have used while implementing your game.
  
  Images Used: 
  
  Back of card: https://casino4you.com/wp-content/uploads/2015/03/peekredback.jpg
  
  Strawberry: https://www.isons.com/wp-content/uploads/2016/10/Chandler_Strawberry.SS_.99187142.jpg
  Banana: http://weknowyourdreams.com/images/banana/banana-02.jpg
  HoneyDew: https://bonnieplants.com/wp-content/uploads/honeydew-cantaloupe.jpg
  Watermelon: https://media.istockphoto.com/photos/watermelon-on-a-white-background-isolated-
  picture-id1000868444
  Cherry: http://weknowyourdreams.com/images/cherry/cherry-09.jpgc
  Grapes: http://www.hdsfoods.co.uk/wp-content/uploads/2014/01/grapes-green-e1390145885426.jpg
  Apple: https://jooinn.com/images/red-apple-fruit-1.jpg
  Orange: https://www.quanta.org/orange/orange.jpg
  Cantaloupe: https://i5.walmartimages.com/asr/fb4c18a5-9367-4770-b99f-7518c72db482.
  5609c32e87a3110b734aad048bf9fe35.jpeg
  Mango: https://momobud.sg/wp-content/uploads/2017/03/Keitt-mango-3.jpg
  Pear: https://wholesale.heartlandvapes.com/media/catalog/product/cache/2/image/800x800/
  3a9e366d1177e2f98d7ae0709f7d6caf/p/e/pear-flavoring_3.jpg
  Peach: http://www.freshpoint.com/wp-content/uploads/commodity-peach.jpg
  Blueberry: https://images.homedepot-static.com/productImages/1ff72619-5306-481a-94dc-27c11799a665
  /svn/fruit-plants-blutif05g-64_1000.jpg
  Kiwi: https://www.organicgarden.co.in/wp-content/uploads/2018/12/kiwi-1.jpg
  Pineapple: http://www.picserver.org/pictures/pineapple01-lg.jpg
  Grapefruit: http://ripeme.com/wp-content/uploads/Grapefruit11.jpg
  Lemon: https://www.almanaturals.net/sites/default/files/product-images/lemon.jpg
  Raspberry: https://thekitchensurvival.com/wp-content/uploads/2017/11/Raspberry-Benefits.jpg
  Blackberry: http://upload.wikimedia.org/wikipedia/commons/c/cc/Blackberry_fruit.jpg
  
  
