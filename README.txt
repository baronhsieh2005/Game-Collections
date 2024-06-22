=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
CIS 1200 Game Project README
PennKey: _______
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=

===================
=: Core Concepts :=
===================

- List the four core concepts, the features they implement, and why each feature
  is an appropriate use of the concept. Incorporate the feedback you got after
  submitting your proposal.

  1. 2D Array: The 2D Array is used to represent the game board of the Minesweeper game. The
     board is modeled by a 2D array of Cells objects with each grid of the array being
     occupied by a single Cell object that have different states and behaviors (flagged, has mine...etc.)
     This is an appropriate use of the 2D Array because the game board is
     a 2D grid of cells with rows and columns, and using a 2D array to represent
     the game board allows for easy access to individual cells and their properties using their position
     in the grid.

  2. Recursion: Recursion is used in my project for modeling the cell revealing method so that when
     a cell is clicked by the user, if the cell that is clicked is not revealed, then it first reveals the
     cell, then if the cell is a bomb, the method stops the recursion. Then if the cell has no adjacent mines,
     the method then takes in the adjacent neighbors of the cell as its argument
     and repeats the same process for these neighboring cells. Using recursion in this scenario makes the iteration
     process significantly faster and also makes the code cleaner compared to using for loops.

  3. File I/O: File I/O is used in my project the implement the save/load functionality for my minesweeper game.
     First, when the save button is pressed, then the Bufferedwriter would write the key instances and states of
     each cell in the board into a file. Then, when load is pressed, then the Bufferedreader would read the same
     file that the Bufferwriter writes to fetch the information of all cells and the load function would load
     these information into the gameboard, recreating the saved gameboard. This is an appropriate use of File I/              

  4. JUnit Testable Component: The Minesweeper class is a JUnit testable component in my project. The cell class is
     tested to ensure that the cell states and behaviors are handled correctly. The Minesweeper
     class contains the game logic and rules of the Minesweeper game, and it can be tested independently from the GUI
     to ensure that the game logic works correctly. This is an appropriate use of a JUnit testable component because
     it allows for easy testing of the game logic without having to interact with the GUI, and it ensures that the game
     functions correctly even if the GUI is not working properly.

===============================
=: File Structure Screenshot :=
===============================
- Include a screenshot of your project's file structure. This should include
  all of the files in your project, and the folders they are in. You can
  upload this screenshot in your homework submission to gradescope, named 
  "file_structure.png".

=========================
=: Your Implementation :=
=========================

- Provide an overview of each of the classes in your code, and what their
  function is in the overall game.

    1. Cell: This class represents a single cell in the Minesweeper game. It contains all the possible states
         and behaviors of a cell in the game, such as whether it has a mine, whether it is flagged, whether it is
         revealed, and the number of adjacent mines. It also contains methods to reveal the cell, flag the cell,
         and check if the cell is a mine.

    2. Minesweeper: This class represents the Minesweeper game itself. It has the model of the game that
       can be ran independently from the GUI for unit testing purposes. In the Minesweeper class, the playTurn
       function simulates the gameplay of clicking and revealing the cells while checking all the states of
       clicked cell to decide the outcome of the action for the game. Additionally, the class has functions
       to initiate/reinitiate the board, handles the recursive revealing of cells, checks the win and loss conditions
       of the game, reveals all the mines on the board, and defines the save and load behaviors of the game.

    3. GameBoard: This class represents the game board of the Minesweeper game. It is the view of the game
       and handles the GUI elements so the visuals of the game can be created, updated, and presented to the user.

    4. RunMineSweeper: This class is the class that runs the Minesweeper game.


- Were there any significant stumbling blocks while you were implementing your
  game (related to your design, or otherwise)?

  One significant stumbling block I encountered when implementing my game was
  setting up the GUI to draw out the board. Initially, I actually
  had a draw function for my Cell object that relied on the row, column, and
  cell_size variable to draw out the cell with its corresponding states (has mines,
  numValue, isRevealed, flagged, etc.) while in GameBoard I drew out the board by
  iterating through every cell in gameboard and drawing them out individually.
  However, doing so, for some reason the string representing the numValue of a cell keeps
  being drawn one block above the cell it is supposed to represent. I tried moderating the
  position of the string, but it either ends up at an even more incorrect location or just
  straight up all disappears. In the end, I decided to just draw the board in GameBoard similarly
  to how it was drawn in the example TicTacToe GameBoard class, and the string is finally drawn out
  perfectly after some minor modifications to its position.


- Evaluate your design. Is there a good separation of functionality? How well is
  private state encapsulated? What would you refactor, if given the chance?
  I feel like my design has a good separation of functionality and UI, with the private states encapsulated
  into the Cell and Minesweeper classes. The Cell class encapsulates the states and behaviors of a single cell
  and the Minesweeper class encapsulates the game logic and rules of the Minesweeper game. The GameBoard class
  single-handedly handles the GUI elements and the visuals of the game. If given the chance, I would refactor
  the Minesweeper class by improving the playTurn function so the actionevent would look much cleaner and concise.



========================
=: External Resources :=
========================

- Cite any external resources (images, tutorials, etc.) that you may have used 
  while implementing your game.

    I used the following resources to help me implement my game:
    - https://logos.fandom.com/wiki/Microsoft_Minesweeper
