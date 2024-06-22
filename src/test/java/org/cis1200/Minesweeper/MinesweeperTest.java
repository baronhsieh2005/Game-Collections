package org.cis1200.Minesweeper;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class MinesweeperTest {
    @Test
    public void testMinePlacement() {
        Minesweeper game = new Minesweeper();
        game.boardSetup();
        int mineCount = 0;
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                if (game.getCell(x, y).isMine()) {
                    mineCount++;
                }
            }
        }
        assertEquals(15, mineCount);
    }

    @Test
    public void testAdjacentMines() {
        Minesweeper game = new Minesweeper();
        game.boardSetup();
        int adjacentMines = 0;
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                if (game.getCell(x, y).getNumValue() == -1) {
                    continue;
                }
                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        if (x + i >= 0 && x + i < 10 && y + j >= 0 && y + j < 10) {
                            if (game.getCell(x + i, y + j).isMine()) {
                                adjacentMines++;
                            }
                        }
                    }
                }
                assertEquals(game.getCell(x, y).getNumValue(), adjacentMines);
                adjacentMines = 0;
            }
        }
    }

    @Test
    public void testFlagging() {
        Minesweeper game = new Minesweeper();
        game.boardSetup();
        Cell cell = game.getCell(0, 0);
        cell.setFlagged();
        assertEquals(14, game.getMaxFlags() - game.findFlagNumber());
        cell.removeFlag();
        assertEquals(15, game.getMaxFlags() - game.findFlagNumber());
    }


    @Test
    public void testFlaggingSameCell() {
        Minesweeper game = new Minesweeper();
        game.boardSetup();
        Cell cell = game.getCell(0, 0);
        cell.setFlagged();
        assertEquals(14, game.getMaxFlags() - game.findFlagNumber());
        cell.setFlagged();
        assertEquals(14, game.getMaxFlags() - game.findFlagNumber());
    }


    @Test
    public void noLongerFlaggedAfterReveal() {
        Minesweeper game = new Minesweeper();
        game.boardSetup();
        Cell cell = game.getCell(0, 0);
        cell.setFlagged();
        cell.reveal();
        assertFalse(cell.isFlagged());
    }

    @Test void flagLimitationTest() {
        Minesweeper game = new Minesweeper();
        game.boardSetup();
        game.setFlagging();
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                if (!game.getCell(x, y).isMine()) {
                    game.playTurn(x, y);
                }
            }
        }
        int flagNumber = game.findFlagNumber();
        assertTrue(flagNumber <= 15);
        assertTrue(flagNumber >= 0);
    }

    @Test
    public void clickOnFlaggedCell() {
        Minesweeper game = new Minesweeper();
        game.boardSetup();
        game.setFlagging();
        game.playTurn(0, 0);
        game.setFlagging();
        game.playTurn(0, 0);
        assertFalse(game.getCell(0, 0).isRevealed());
    }

    @Test
    public void doubleRightClickUnflag() {
        Minesweeper game = new Minesweeper();
        game.boardSetup();
        game.setFlagging();
        game.playTurn(0, 0);
        game.playTurn(0, 0);
        assertFalse(game.getCell(0, 0).isFlagged());
    }

    @Test
    public void testFlagRevealedCell() {
        Minesweeper game = new Minesweeper();
        game.boardSetup();
        game.playTurn(0, 0);
        game.setFlagging();
        game.playTurn(0, 0);
        assertFalse(game.getCell(0, 0).isFlagged());
    }

    @Test
    public void testRevealAllMines() {
        Minesweeper game = new Minesweeper();
        game.boardSetup();
        game.revealAllMines();
        boolean allRevealed = true;
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                if (game.getCell(x, y).isMine() && !game.getCell(x, y).isRevealed()) {
                    allRevealed = false;
                }
            }
        }
        assertTrue(allRevealed);
    }

    @Test
    public void testWinCondition() {
        Minesweeper game = new Minesweeper();
        game.boardSetup();
        game.revealArea(0, 0);
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                if (!game.getCell(x, y).isRevealed() && !game.getCell(x, y).isMine()) {
                    game.getCell(x, y).reveal();
                }
            }
        }
        assertTrue(game.checkWinCondition());
    }

    @Test
    public void testLoseCondition() {
        Minesweeper game = new Minesweeper();
        game.boardSetup();
        game.revealArea(0, 0);
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                if (game.getCell(x, y).isMine()) {
                    game.getCell(x, y).reveal();
                    assertTrue(game.checkLoseCondition());
                }
            }
        }
    }

    @Test
    public void testWinByFlaggingAllMines() {
        Minesweeper game = new Minesweeper();
        game.boardSetup();
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                if (game.getCell(x, y).isMine()) {
                    game.getCell(x, y).setFlagged();
                }
            }
        }
        // Reveal all non-mine cells
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                game.playTurn(x, y);
            }
        }

        // Assert game is won
        assertTrue(game.checkWinCondition());
        assertFalse(game.checkLoseCondition());
    }

    @Test
    public void testGameContinuationAfterWin() {
        Minesweeper game = new Minesweeper();
        game.boardSetup();
        // Simulate winning the game
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                Cell cell = game.getCell(x, y);
                if (!cell.isMine()) {
                    game.playTurn(x, y);
                }
            }
        }
        assertTrue(game.checkWinCondition());
        game.setFlagging();
        game.playTurn(0, 0);
        assertFalse(game.getCell(0, 0).isFlagged());
    }

    @Test
    public void testGameContinuationLoss() {
        Minesweeper game = new Minesweeper();
        game.boardSetup();
        // Simulate winning the game
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                Cell cell = game.getCell(x, y);
                if (cell.isMine()) {
                    game.playTurn(x, y);
                    assertTrue(game.checkLoseCondition());
                    game.setFlagging();
                    game.playTurn(0, 0);
                    assertFalse(game.getCell(0, 0).isFlagged());
                }
            }
        }
    }


    @Test
    public void testSaveLoadGame() {
        Minesweeper game = new Minesweeper();
        game.boardSetup();
        game.saveGame("save.txt");
        Minesweeper newGame = new Minesweeper();
        try {
            newGame.loadGame("save.txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                assertEquals(game.getCell(x, y).isRevealed(), newGame.getCell(x, y).isRevealed());
                assertEquals(game.getCell(x, y).isFlagged(), newGame.getCell(x, y).isFlagged());
                assertEquals(game.getCell(x, y).isMine(), newGame.getCell(x, y).isMine());
                assertEquals(game.getCell(x, y).getNumValue(), newGame.getCell(x, y).getNumValue());
            }
        }
    }


}
