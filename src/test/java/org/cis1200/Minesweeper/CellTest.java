package org.cis1200.Minesweeper;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CellTest {
    @Test
    public void cellInitializationTest() {
        Cell cell = new Cell(0, 0);
        assertFalse(cell.isRevealed());
        assertFalse(cell.isFlagged());
        assertFalse(cell.isMine());
        assertEquals(0, cell.getNumValue());
    }

    @Test
    public void testSetMine() {
        Cell cell = new Cell(0, 0);
        cell.setMine();
        assertEquals(-1, cell.getNumValue());
        assertTrue(cell.isMine());
    }

    @Test
    public void testFlagging() {
        Cell cell = new Cell(0, 0);
        cell.setFlagged();
        assertTrue(cell.isFlagged());

        cell.removeFlag();
        assertFalse(cell.isFlagged());
    }

    @Test
    public void testFlaggingRevealedCell() {
        Cell cell = new Cell(0, 0);
        cell.reveal();
        cell.setFlagged();
        assertFalse(cell.isFlagged());
    }

    @Test
    public void testRevealCell() {
        Cell cell = new Cell(0, 0);
        cell.reveal();
        assertTrue(cell.isRevealed());
        assertFalse(cell.isFlagged());
    }

    @Test
    public void testCalculateCellValueNoAdjacentMines() {
        Minesweeper game = new Minesweeper();
        // Clear all mines for controlled test conditions
        game.boardSetup();
        Cell[][] cells = new Cell[10][10];
        // Clear all mines for controlled test conditions
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                cells[x][y] = new Cell(x, y);
                cells[x][y].setNumValue(0);
            }
        }

        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                cells[x][y].calculateCellValue(cells);
                assertEquals(0, cells[x][y].getNumValue(), "Cell should have 0 adjacent mines.");
            }
        }
    }

    @Test
    public void testCalculateCellValueAllAdjacentMines() {
        Minesweeper game = new Minesweeper();
        // Clear all mines for controlled test conditions
        game.boardSetup();
        Cell[][] cells = new Cell[10][10];
        // Clear all mines for controlled test conditions
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                cells[x][y] = new Cell(x, y);
            }
        }

        int row = 5, col = 5;
        for (int dr = -1; dr <= 1; dr++) {
            for (int dc = -1; dc <= 1; dc++) {
                if (dr != 0 || dc != 0) { // Skip the center cell itself
                    cells[row + dr][col + dc] = new Cell(row + dr, col + dc);
                    cells[row + dr][col + dc].setMine();
                }
            }
        }


        cells[5][5].calculateCellValue(cells);
        assertEquals(8, cells[5][5].getNumValue(), "Cell should have 8 adjacent mines.");
    }

    @Test
    public void testCalculateCellValueEdgeCell() {
        Minesweeper game = new Minesweeper();
        game.boardSetup();
        Cell[][] cells = new Cell[10][10];
        // Clear all mines for controlled test conditions
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                cells[x][y] = new Cell(x, y);
            }
        }

        cells[0][1].setMine();
        cells[1][0].setMine();
        cells[1][1].setMine();

        Cell testCell = cells[0][0];
        testCell.calculateCellValue(cells);
        assertEquals(3, testCell.getNumValue(), "Edge cell should have 3 adjacent mines.");
    }


}
