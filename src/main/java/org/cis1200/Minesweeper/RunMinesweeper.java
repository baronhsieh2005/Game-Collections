package org.cis1200.Minesweeper;

import javax.swing.*;
import java.awt.*;

public class RunMinesweeper implements Runnable {
    @Override
    public void run() {
        final JFrame frame = new JFrame("Minesweeper");
        frame.setLocation(300, 300);

        final JPanel status_panel = new JPanel();
        frame.add(status_panel, BorderLayout.SOUTH);
        final JLabel status = new JLabel("Minesweeper!",  SwingConstants.CENTER);
        status_panel.add(status);

        final GameBoard gameBoard = new GameBoard(status);
        frame.add(gameBoard, BorderLayout.CENTER);


        // Reset button
        final JPanel control_panel = new JPanel();
        frame.add(control_panel, BorderLayout.NORTH);

        // Note here that when we add an action listener to the reset button, we
        // define it as an anonymous inner class that is an instance of
        // ActionListener with its actionPerformed() method overridden. When the
        // button is pressed, actionPerformed() will be called.
        final JButton reset = new JButton("Reset");
        reset.addActionListener(e -> gameBoard.boardSetup());
        reset.setBackground(Color.lightGray);
        reset.setOpaque(true);
        control_panel.add(reset);

        final JButton instructions = new JButton("Instructions");
        instructions.addActionListener(e -> gameBoard.instructions());
        instructions.setBackground(Color.lightGray);
        instructions.setOpaque(true);
        control_panel.add(instructions);


        final JButton save = new JButton("Save");
        save.addActionListener(e -> gameBoard.saveGame());
        save.setBackground(Color.lightGray);
        save.setOpaque(true);
        control_panel.add(save);

        final JButton load = new JButton("Load");
        load.addActionListener(e -> gameBoard.loadGame());
        load.setBackground(Color.lightGray);
        load.setOpaque(true);
        control_panel.add(load);

        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Start the game
        gameBoard.boardSetup();
    }
}
