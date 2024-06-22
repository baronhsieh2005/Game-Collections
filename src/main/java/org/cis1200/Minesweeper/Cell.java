package org.cis1200.Minesweeper;

public class Cell {
    private final int row;
    private final int col;
    private int numValue;

    private boolean isRevealed;
    private boolean isFlagged;
    private boolean isMine;
    public static final int CELL_SIZE = 40;

    Cell(int row, int col) {
        this.row = row;
        this.col = col;
        this.isRevealed = false;
        this.isFlagged = false;
        this.isMine = false;
    }

    public void setMine() {
        isMine = true;
        this.numValue = -1;
    }

    public boolean isMine() {
        return isMine;
    }

    public int getNumValue() {
        return numValue;
    }

    public void setNumValue(int numValue) {
        this.numValue = numValue;
    }

    public boolean isRevealed() {
        return isRevealed;
    }

    public void reveal() {
        isRevealed = true;
        isFlagged = false;
    }

    public void setFlagged() {
        if (!isRevealed()) {
            isFlagged = true;
        }
    }
    public void removeFlag() {
        if (!isRevealed()) {
            isFlagged = false;
        }
    }

    public boolean isFlagged() {
        return isFlagged;
    }

    public void calculateCellValue(Cell[][] cells) {
        if (!isMine) {
            int count = 0;
            for (int dRow = -1; dRow <= 1; dRow++) {
                for (int dCol = -1; dCol <= 1; dCol++) {
                    int currentRow = this.row + dRow;
                    int currentCol = this.col + dCol;
                    if (currentRow >= 0 && currentRow < cells.length
                            && currentCol >= 0 && currentCol < cells[0].length) {
                        if (cells[currentRow][currentCol].isMine()) {
                            count++;
                        }
                    }
                }
            }
            numValue = count;
        }
    }
}
