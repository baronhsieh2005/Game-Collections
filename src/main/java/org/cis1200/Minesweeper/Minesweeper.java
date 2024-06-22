package org.cis1200.Minesweeper;

import java.io.*;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Minesweeper {
    private int rows = 10;
    private int cols = 10;
    private int numMines = 15;

    private int maxFlags = numMines;
    private boolean flagging;
    private Cell[][] gameBoard;
    private int remainingFlags;
    private boolean gameOver;

    public static final int BOARD_WIDTH = 400;
    public static final int BOARD_HEIGHT = 400;

    private List<Cell> mineCells = new LinkedList<>();

    public Minesweeper() {
        boardSetup();
    }

    // Used for testing the model/game state without involving GUI components
    // (like simulating mouse clicks).
    // Essentially click on a cell in the game board is calling playTurn
    // with the row and column of the cell.
    public void playTurn(int row, int col) {
        if (!gameOver) {
            Cell cell = gameBoard[row][col];
            if (!cell.isRevealed() && !cell.isFlagged() && !flagging) {
                cell.reveal();
                if (cell.isMine()) {
                    revealAllMines();
                    gameOver = true;
                } else if (cell.getNumValue() >= 0) {
                    revealArea(row, col);
                }
            } else if (flagging && !cell.isRevealed()) {
                if (!cell.isFlagged() && remainingFlags >= 1) {
                    cell.setFlagged();
                    remainingFlags--;
                } else if (cell.isFlagged()) {
                    cell.removeFlag();
                    remainingFlags++;
                }
            }
        }
    }

    public Cell[][] getGameBoard() {
        return gameBoard;
    }

    public void boardSetup() {
        gameOver = false;
        remainingFlags = numMines;
        flagging = false;
        gameBoard = new Cell[rows][cols];
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                gameBoard[j][i] = new Cell(j, i);
            }
        }
        placeMines();
        calculateAdjacentMines();
    }

    public boolean playable() {
        return !gameOver;
    }


    public Cell getCell(int row, int col) {
        if (row >= 0 && row < gameBoard.length && col >= 0 && col < gameBoard[0].length) {
            return gameBoard[row][col];
        }
        return null;
    }

    public int getMaxFlags() {
        return maxFlags;
    }

    public void setFlagging() {
        this.flagging = !flagging;
    }

    public void placeMines() {
        Random random = new Random();
        int minesPlaced = 0;
        while (minesPlaced < numMines) {
            int x = random.nextInt(cols);
            int y = random.nextInt(rows);
            if (!gameBoard[y][x].isMine()) {
                gameBoard[y][x].setMine();
                minesPlaced++;
                mineCells.add(gameBoard[y][x]);
            }
        }
    }

    public void calculateAdjacentMines() {
        for (int x = 0; x < cols; x++) {
            for (int y = 0; y < rows; y++) {
                gameBoard[y][x].calculateCellValue(gameBoard);
            }
        }
    }


    public void revealArea(int row, int col) {
        // Check if the given coordinates are out of bounds
        if (row < 0 || row >= gameBoard.length || col < 0 || col >= gameBoard[0].length) {
            return;
        }

        Cell cell = gameBoard[row][col];

        // If the cell is already revealed, there's nothing more to do here
        if (cell.isRevealed()) {
            return;
        }

        // Reveal this cell
        cell.reveal();

        if (mineCells.contains(cell)) {
            return;
        }

        // If the cell's value is 0, it has no adjacent mines
        // Recursively reveal surrounding cells
        if (cell.getNumValue() == 0) {
            revealArea(row - 1, col);
            revealArea(row + 1, col);
            revealArea(row, col - 1);
            revealArea(row, col + 1);
            revealArea(row - 1, col + 1);
            revealArea(row + 1, col - 1);
            revealArea(row - 1, col - 1);
            revealArea(row + 1, col + 1);
        }
    }


    public boolean checkWinCondition() {
        boolean win = true;
        for (int x = 0; x < cols; x++) {
            for (int y = 0; y < rows; y++) {
                Cell cell = gameBoard[y][x];
                if (!cell.isMine() && !cell.isRevealed()) {
                    win = false;
                }
            }
        }
        if (win) {
            gameOver = true;
            return true;
        }
        return false;
    }

    public boolean checkLoseCondition() {
        for (int x = 0; x < cols; x++) {
            for (int y = 0; y < rows; y++) {
                Cell cell = gameBoard[y][x];
                if (cell.isRevealed() && cell.isMine()) {
                    gameOver = true;
                    revealAllMines();
                    return true;
                }
            }
        }
        return false;
    }

    public void revealAllMines() {
        for (int x = 0; x < cols; x++) {
            for (int y = 0; y < rows; y++) {
                Cell cell = gameBoard[y][x];
                if (!cell.isRevealed() && cell.isMine()) {
                    cell.reveal();
                }
            }
        }
    }

    public int findFlagNumber() {
        int flagCount = 0;
        for (int x = 0; x < cols; x++) {
            for (int y = 0; y < rows; y++) {
                Cell cell = gameBoard[y][x];
                if (cell.isFlagged()) {
                    flagCount++;
                }
            }
        }
        return flagCount;
    }

    private void writeStateToFile(
            List<String> stringsToWrite, String filePath,
            boolean append
    ) {
        File file = Paths.get(filePath).toFile();
        BufferedWriter bw;
        try {
            bw = new BufferedWriter(new FileWriter(file, append));
            for (String s : stringsToWrite) {
                bw.write(s);
                bw.newLine();
            }
            bw.flush();
            bw.close();
        } catch (IOException e) {
            System.out.println("Error!");
        }

    }
    public void saveGame(String filePath) {
        List<String> gameData = new LinkedList<>();
        gameData.add(rows + "," + cols + "," + numMines + "," + gameOver);
        for (int y = 0; y < rows; y++) {
            StringBuilder row = new StringBuilder();
            for (int x = 0; x < cols; x++) {
                Cell cell = gameBoard[y][x];

                // Choose  to use shorthand form of if-else statement using ? operator
                char cellType = cell.isMine() ? 'M' : 'C';
                char revealFlag = cell.isRevealed() ? 'R' : 'H';
                char flag = cell.isFlagged() ? 'F' : 'U';
                char numValue = (char) (cell.getNumValue() + '0');

                row.append(cellType).append(revealFlag).append(flag).append(numValue).append(' ');
            }
            gameData.add(row.toString().trim());
        }
        writeStateToFile(gameData, filePath, false);
    }

    public void loadGame(String filePath) throws IOException {
        File file = Paths.get(filePath).toFile();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String[] settings = br.readLine().split(",");
            this.rows = Integer.parseInt(settings[0]);
            this.cols = Integer.parseInt(settings[1]);
            this.numMines = Integer.parseInt(settings[2]);
            this.gameOver = Boolean.parseBoolean(settings[3]);
            this.gameBoard = new Cell[rows][cols];
            this.mineCells.clear();

            for (int y = 0; y < rows; y++) {
                String[] cellData = br.readLine().split(" ");
                for (int x = 0; x < cols; x++) {
                    char[] cellInfo = cellData[x].toCharArray();
                    Cell cell = new Cell(y, x);
                    gameBoard[y][x] = cell;

                    if (cellInfo[0] == 'M') {
                        cell.setMine();
                        mineCells.add(cell);
                    }
                    if (cellInfo[1] == 'R') {
                        cell.reveal();
                    }
                    if (cellInfo[2] == 'F') {
                        cell.setFlagged();
                    }
                    if (cellInfo[3] != ' ') {
                        cell.setNumValue(cellInfo[3] - '0');
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Save file not found.");
        }
    }
}
