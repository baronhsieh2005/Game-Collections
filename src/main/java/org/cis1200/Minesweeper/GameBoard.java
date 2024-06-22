package org.cis1200.Minesweeper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class GameBoard extends JPanel {
    private Minesweeper ms;
    private JLabel status;
    private int rows = 10;
    private int cols = 10;
    public static final int BOARD_WIDTH = 400;
    public static final int BOARD_HEIGHT = 400;


    public GameBoard(JLabel statusInIt) {
        ms = new Minesweeper();
        status = statusInIt;
        setPreferredSize(new Dimension(BOARD_WIDTH, 430));
        setBackground(Color.lightGray);
        boardSetup();
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                if (ms.playable()) {
                    int x = e.getX() / Cell.CELL_SIZE;
                    int y = e.getY() / Cell.CELL_SIZE;
                    if (ms.getCell(y, x) != null) {
                        if (e.getButton() == MouseEvent.BUTTON1) { // Left click
                            if (!ms.getCell(y, x).isFlagged()) {
                                ms.revealArea(y, x);
                            }
                        } else if (e.getButton() == MouseEvent.BUTTON3) { // Right click
                            if (!ms.getCell(y, x).isFlagged() &&
                                    ms.findFlagNumber() < ms.getMaxFlags()) {
                                ms.getCell(y, x).setFlagged();
                            } else {
                                ms.getCell(y, x).removeFlag();
                            }
                        }
                        updateStatus();
                        repaint();
                    }
                }
            }
        });
    }

    public void boardSetup() {
        ms.boardSetup();
        status.setText("Minesweeper!");
        repaint();
        requestFocusInWindow();
    }

    public void instructions() {
        final JFrame frame = new JFrame("Instructions");
        frame.setLocation(150, 150);
        final JPanel instructions_panel = new JPanel();
        frame.add(instructions_panel, BorderLayout.CENTER);
        final JLabel instructionLabel = new JLabel("",  SwingConstants.CENTER);
        instructions_panel.add(instructionLabel);
        instructionLabel.setText("<html>Instructions:<br/>" +
                "Uncover all the cells that doesn't contain one of the 15 " +
                "mines in this 10 x 10 board to win!  <br/>" +
                "-  If you reveal a mine, BOOM! You lose! " +
                "(Mines are indicated by red circle)<br/>" +
                "-  Left click to reveal cell <br/> " +
                "-  Right click to set flag on cell " +
                "(Flags are indicated by blue circle) <br/>" +
                "-  Safely revealed cells sometimes contain numbers... " +
                "Those numbers indicate the number of mines adjacent to that cell! <br/>" +
                "-  Press the reset button to restart a new game of Minesweeper <br/" +
                "-  In this iteration of minesweeper, you can save and load the " +
                "game by pressing the correspoding buttons! <br/> <br/> <html>");
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }

    public void updateStatus() {
        status.setText("Remaining Flags: " + (ms.getMaxFlags() - ms.findFlagNumber()));
        if (ms.checkLoseCondition()) {
            status.setText("BOOM! You Lose.");
        } else if (ms.checkWinCondition()) {
            status.setText("You Win!");
        }
    }

    public void saveGame() {
        ms.saveGame("save.txt");
    }

    public void loadGame() {
        try {
            ms.loadGame("save.txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        updateStatus();
        repaint();
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int unitWidth = BOARD_WIDTH / 10;
        int unitHeight = BOARD_HEIGHT / 10;
        for (int i = 0; i <= 10; i++) {
            g.drawLine(unitWidth * i, 0, unitWidth * i, BOARD_HEIGHT);
        }
        for (int i = 0; i <= 10; i++) {
            g.drawLine(0, unitHeight * i, BOARD_WIDTH, unitHeight * i);
        }
        for (int x = 0; x < cols; x++) {
            for (int y = 0; y < rows; y++) {
                Cell cell = ms.getCell(y, x);
                if (cell.isRevealed()) {
                    if (cell.isMine()) {
                        g.setColor(Color.black);
                        g.drawOval(10 + 40 * x, 10 + 40 * y, 20, 20);
                        g.setColor(Color.red);
                        g.fillOval(10 + 40 * x, 10 + 40 * y, 20, 20);
                    } else {
                        if (cell.getNumValue() != 0) {
                            g.setColor(Color.black);
                            g.drawString(
                                    Integer.toString(cell.getNumValue()), 16 + 40 * x, 24 + 40 * y);
                        }
                    }
                } else {
                    if (cell.isFlagged()) {
                        g.setColor(Color.gray);
                        g.fillRect(4 + 40 * x, 4 + 40 * y, 32, 32);
                        g.setColor(Color.black);
                        g.drawOval(10 + 40 * x, 10 + 40 * y, 20, 20);
                        g.setColor(Color.blue);
                        g.fillOval(10 + 40 * x, 10 + 40 * y, 20, 20);
                    } else {
                        g.setColor(Color.gray);
                        g.fillRect(4 + 40 * x, 4 + 40 * y, 32, 32);
                    }
                }
            }
        }
    }

}
