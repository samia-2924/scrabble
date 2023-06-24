import com.example.scrabblegui.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;

public class ScrabbleTest {
    private Scrabble scrabble;

    @BeforeEach
    public void setUp() {
        Player player = new Player();
        Board board = new Board();
        scrabble = new Scrabble(player, board);
    }

    @Test
    public void testCheckBoard_InitialMove_ReturnsFalse() {
        boolean result = scrabble.checkBoard();
        Assertions.assertFalse(result, "Initial move should return false");
    }

    @Test
    public void testCheckBoard_EmptyRecentlyPlayedCellStack_ReturnsFalse() {
        scrabble.recentlyPlayedCellStack.clear();

        boolean result = scrabble.checkBoard();
        Assertions.assertFalse(result, "Empty recentlyPlayedCellStack should return false");
    }

    @Test
    public void testIsConnected_HorizontalLink_ReturnsTrue() {
        Cell cell1 = new Cell();
        Cell cell2 = new Cell();
        Cell cell3 = new Cell();

        cell1.setRight(cell2);
        cell2.setRight(cell3);
        cell2.setLeft(cell1);
        cell3.setLeft(cell2);

        scrabble.recentlyPlayedCellStack.push(cell1);
        scrabble.recentlyPlayedCellStack.push(cell2);
        scrabble.recentlyPlayedCellStack.push(cell3);

        boolean result = scrabble.isConnected();
        Assertions.assertTrue(result, "Horizontal link should return true");
    }

    @Test
    public void testHorizontalScore_CalculateScore_ReturnsCorrectScore() {
        Cell cell1 = new Cell();
        Cell cell2 = new Cell();
        Cell cell3 = new Cell();

        Tile tile1 = new Tile("A", 1);
        Tile tile2 = new Tile("B", 3);
        Tile tile3 = new Tile("C", 2);

        cell1.setTile(tile1);
        cell2.setTile(tile2);
        cell3.setTile(tile3);

        cell1.setRight(cell2);
        cell2.setRight(cell3);
        cell2.setLeft(cell1);
        cell3.setLeft(cell2);

        int score = scrabble.horizontalScore(cell1);
        Assertions.assertEquals(6, score, "Horizontal score calculation is incorrect");
    }

    @Test
    public void testCheckBoard_InvalidInitialMove_ReturnsFalse() {
        scrabble.recentlyPlayedCellStack.push(new Cell());

        boolean result = scrabble.checkBoard();
        Assertions.assertFalse(result, "Invalid initial move should return false");
    }

    @Test
    public void testHorizontalScore_NoTiles_ReturnsZero() {
        Cell cell = new Cell();

        int score = scrabble.horizontalScore(cell);
        Assertions.assertEquals(0, score, "Horizontal score should be zero when no tiles are present");
    }

    @Test
    public void testVerticalScore_SingleTile_ReturnsZero() {
        Cell cell = new Cell();
        Tile tile = new Tile("A", 1);
        cell.setTile(tile);

        int score = scrabble.verticalScore(cell);
        Assertions.assertEquals(0, score, "Vertical score should be zero when only one tile is present");
    }

    @Test
    public void testGetWordBonus_OccupiedCell_ReturnsZero() {
        Cell cell = new Cell();
        scrabble.occupiedCells.add(cell);

        int bonus = scrabble.getWordBonus(cell);
        Assertions.assertEquals(0, bonus, "Word bonus should be zero for an occupied cell");
    }

    @Test
    public void testGetLetterBonus_UnoccupiedCell_ReturnsOne() {
        Cell cell = new Cell();

        int bonus = scrabble.getLetterBonus(cell);
        Assertions.assertEquals(1, bonus, "Letter bonus should be one for an unoccupied cell");
    }

    @Test
    public void testClearStacks_StacksAreCleared() {
        scrabble.recentlyPlayedTileStack.push(new Tile("A", 1));
        scrabble.recentlyPlayedCellButtonStack.push(new JButton());
        scrabble.clearStacks();

        Assertions.assertTrue(scrabble.recentlyPlayedTileStack.isEmpty(), "recentlyPlayedTileStack should be empty");
        Assertions.assertTrue(scrabble.recentlyPlayedCellButtonStack.isEmpty(), "recentlyPlayedCellButtonStack should be empty");
    }

    @Test
    public void testUpdateCells_CellsAreUpdated() {
        Cell cell1 = new Cell();
        Cell cell2 = new Cell();
        scrabble.recentlyPlayedCellStack.push(cell1);
        scrabble.recentlyPlayedCellStack.push(cell2);
        scrabble.updateCells();

        Assertions.assertTrue(scrabble.occupiedCells.contains(cell1), "occupiedCells should contain cell1");
        Assertions.assertTrue(scrabble.occupiedCells.contains(cell2), "occupiedCells should contain cell2");
    }

    @Test
    public void testIsConnected_VerticalLink_ReturnsTrue() {
        Cell cell1 = new Cell();
        Cell cell2 = new Cell();
        Cell cell3 = new Cell();

        cell1.setBottom(cell2);
        cell2.setBottom(cell3);
        cell2.setTop(cell1);
        cell3.setTop(cell2);

        scrabble.recentlyPlayedCellStack.push(cell1);
        scrabble.recentlyPlayedCellStack.push(cell2);
        scrabble.recentlyPlayedCellStack.push(cell3);

        boolean result = scrabble.isConnected();
        Assertions.assertTrue(result, "Vertical link should return true");
    }


    @Test
    public void testIsCellRecentlyPlayed_CellInArray_ReturnsTrue() {
        Cell cell = new Cell();
        Cell[] cellArray = {cell};

        boolean result = scrabble.isCellRecentlyPlayed(cell, cellArray);
        Assertions.assertTrue(result, "isCellRecentlyPlayed should return true");
    }

    @Test
    public void testIsCellRecentlyPlayed_CellNotInArray_ReturnsFalse() {
        Cell cell = new Cell();
        Cell[] cellArray = {new Cell()};

        boolean result = scrabble.isCellRecentlyPlayed(cell, cellArray);
        Assertions.assertFalse(result, "isCellRecentlyPlayed should return false");
    }

    @Test
    public void testIsHorizontallyLinked_HorizontalLink() {
        Cell cell1 = new Cell();
        Cell cell2 = new Cell();
        Cell cell3 = new Cell();

        cell1.setRight(cell2);
        cell2.setRight(cell3);
        cell2.setLeft(cell1);
        cell3.setLeft(cell2);

        scrabble.recentlyPlayedCellStack.push(cell1);
        scrabble.recentlyPlayedCellStack.push(cell2);
        scrabble.recentlyPlayedCellStack.push(cell3);

        boolean result = scrabble.isHorizontallyLinked();
        Assertions.assertFalse(result, "Horizontal link should return false");
    }

    @Test
    public void testTraverseLeftUntil_CellNotRecentlyPlayed_ReturnsFalse() {
        Cell cell = new Cell();

        boolean result = scrabble.traverseLeftUntil(cell);
        Assertions.assertFalse(result, "traverseLeftUntil should return false");
    }

    @Test
    public void testTraverseRightUntil_CellNotRecentlyPlayed_ReturnsFalse() {
        Cell cell = new Cell();

        boolean result = scrabble.traverseRightUntil(cell);
        Assertions.assertFalse(result, "traverseRightUntil should return false");
    }

    @Test
    public void testTraverseUpUntil_CellNotRecentlyPlayed_ReturnsFalse() {
        Cell cell = new Cell();

        boolean result = scrabble.traverseUpUntil(cell);
        Assertions.assertFalse(result, "traverseUpUntil should return false");
    }

    @Test
    public void testTraverseDownUntil_CellNotRecentlyPlayed_ReturnsFalse() {
        Cell cell = new Cell();

        boolean result = scrabble.traverseDownUntil(cell);
        Assertions.assertFalse(result, "traverseDownUntil should return false");
    }
}
