package org.cis1200;

import javax.swing.*;

public class Game {
    /**
     * Main method run to start and run the game. Initializes the runnable game
     * class of your choosing and runs it. IMPORTANT: Do NOT delete! You MUST
     * include a main method in your final submission.
     */
    public static void main(String[] args) {
        // Set the game you want to run here

        Runnable game2 = new org.cis1200.Minesweeper.RunMinesweeper();
        ImageIcon icon = new ImageIcon("src/main/java/org/cis1200/Minesweeper/MineIcon.png");
        String intro = " Welcome to Minesweeper!\n \n Minesweeper is " +
                "a simple single-player puzzle game, featuring" +
                " a grid of clickable cells\n with hidden mines scattered " +
                "across the board. The goal of the game is to " +
                "\n uncover all the cells" +
                " that do not contain mines without detonating any of the " +
                "hidden mines \n beforehand.\n \n Instructions: " +
                "\n Uncover all the cells that doesn't contain one of the " +
                "15 mines in this 10 x 10 board to win! \n" +
                " -  If you reveal a mine, BOOM! You lose! (Mines are " +
                "indicated by red circle)\n" +
                " -  Left click to reveal cell \n" +
                " -  Right click to set flag on cell (Flags are " +
                "indicated by blue circle) \n" +
                " -  Safely revealed cells sometimes contain numbers... " +
                "Those numbers indicate the number of mines adjacent to that cell!\n" +
                " -  Press the reset button to restart a new game of Minesweeper\n" +
                " -  In this iteration of minesweeper, you can save and " +
                "load the game by pressing the correspoding buttons!\n\n";

        JOptionPane.showInternalOptionDialog(null,
                intro, "Minesweeper", JOptionPane.YES_OPTION,
                JOptionPane.INFORMATION_MESSAGE, icon, new Object[] {"Play"}, "Play");

        SwingUtilities.invokeLater(game2);
    }
}
