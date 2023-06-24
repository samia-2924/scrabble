package com.example.scrabblegui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Stack;

import javax.swing.JButton;

public class Scrabble implements Serializable {
    Player player;
    Board board;
    Dictionary dict;

    public Stack<Tile> recentlyPlayedTileStack = new Stack<>();
    public Stack<Cell> recentlyPlayedCellStack = new Stack<>();
    public Stack<JButton> recentlyPlayedCellButtonStack = new Stack<>();
    public ArrayList<Cell> occupiedCells = new ArrayList<Cell>();

    Tile rackTileSelected;
    boolean initialMove = true;

    public Scrabble(Player player, Board board) {
        this.player = player;
        this.board = board;
        dict = new Dictionary();
        rackTileSelected = null;
        player.setScore(0);
    }

    /**
     * Checks the validity of the current board state and calculates the score of the move.
     *
     * @return True if the board state is valid, false otherwise.
     */
    public boolean checkBoard() {
        if (initialMove && recentlyPlayedCellStack.size() == 1)
            return false;

        if (recentlyPlayedCellStack.size() == 0)
            return false;

        if (board.cellMatrix[7][7].getTile() == null)
            return false;

        if (!isHorizontallyLinked() && !isVerticallyLinked())
            return false;

        if (!isConnected())
            return false;

        if (!isWordCorrectHorizonatally() || !isWordCorrectVertically())
            return false;

        if (isHorizontallyLinked() && !isVerticallyLinked()) {
            Cell leftMost = checkLeftCells(recentlyPlayedCellStack.peek());
            player.addScore(horizontalScore(leftMost) + scoreVerticalWordsConnectedToHorizontalMove());
        } else if (isVerticallyLinked() && !isHorizontallyLinked()) {
            Cell topMost = getTopCell(recentlyPlayedCellStack.peek());
            player.addScore(verticalScore(topMost) + scoreHorizontalWordsConnectedToVerticalMove());
        } else if (isVerticallyLinked() && isHorizontallyLinked()) {
            Cell leftMost = checkLeftCells(recentlyPlayedCellStack.peek());
            Cell topMost = getTopCell(recentlyPlayedCellStack.peek());
            player.addScore(horizontalScore(leftMost) + verticalScore(topMost));
        }

        updateCells();
        clearStacks();
        initialMove = false;
        return true;
    }

    /**
     * Calculates the score of the vertical words connected to a horizontal move.
     *
     * @return The score of the vertical words.
     */
    public int scoreVerticalWordsConnectedToHorizontalMove() {
        Cell[] cellArray = stackToArray();
        int score = 0;

        for (int i = 0; i < cellArray.length; i++) {
            Cell topMost = getTopCell(cellArray[i]);
            score += verticalScore(topMost);
        }
        return score;
    }

    /**
     * Calculates the score of the horizontal words connected to a vertical move.
     *
     * @return The score of the horizontal words.
     */
    public int scoreHorizontalWordsConnectedToVerticalMove() {
        Cell[] cellArray = stackToArray();
        int score = 0;

        for (int i = 0; i < cellArray.length; i++) {
            Cell leftMost = checkLeftCells(cellArray[i]);
            score += horizontalScore(leftMost);
        }
        return score;
    }

    /**
     * Calculates the score of a horizontal word.
     *
     * @param leftMost The leftmost cell of the word.
     * @return The score of the horizontal word.
     */
    public int horizontalScore(Cell leftMost) {
        Cell current = leftMost;
        int wordMultiply = 1;
        int wordScore = 0;
        boolean hasOneTile = true;

        while (current.getTile() != null) {
            wordMultiply += getWordBonus(current);
            wordScore += (current.getTile().getPoints() * getLetterBonus(current));

            if (current.getRight() != null) {
                if (current.getRight().getTile() != null) {
                    current = current.getRight();
                    hasOneTile = false;
                } else {
                    break;
                }
            } else {
                break;
            }
        }

        if (hasOneTile)
            return 0;

        return wordScore * wordMultiply;
    }

    /**
     * Calculates the score of a vertical word.
     *
     * @param topMost The topmost cell of the word.
     * @return The score of the vertical word.
     */
    public int verticalScore(Cell topMost) {
        Cell current = topMost;
        int wordMultiply = 1;
        int wordScore = 0;
        boolean hasOneTile = true;

        while (current.getTile() != null) {
            wordMultiply += getWordBonus(current);
            wordScore += (current.getTile().getPoints() * getLetterBonus(current));

            if (current.getBottom() != null) {
                if (current.getBottom().getTile() != null) {
                    current = current.getBottom();
                    hasOneTile = false;
                } else {
                    break;
                }
            } else {
                break;
            }
        }

        if (hasOneTile)
            return 0;

        return wordScore * wordMultiply;
    }

    /**
     * Retrieves the word bonus multiplier for a given cell.
     *
     * @param cell The cell to check.
     * @return The word bonus multiplier (1 for no bonus, 2 for double word, 3 for triple word).
     */
    public int getWordBonus(Cell cell) {
        if (occupiedCells.contains(cell))
            return 0;
        else if ("TW".equals(cell.getBonus()))
            return 3;
        else if ("DW".equals(cell.getBonus()))
            return 2;
        return 0;
    }

    /**
     * Retrieves the letter bonus multiplier for a given cell.
     *
     * @param cell The cell to check.
     * @return The letter bonus multiplier (1 for no bonus, 2 for double letter, 3 for triple letter).
     */
    public int getLetterBonus(Cell cell) {
        if (occupiedCells.contains(cell))
            return 1;
        else if ("TL".equals(cell.getBonus()))
            return 3;
        else if ("DL".equals(cell.getBonus()))
            return 2;
        return 1;
    }

    /**
     * Clears the recently played tile and cell stacks.
     */
    public void clearStacks() {
        recentlyPlayedTileStack.clear();
        recentlyPlayedCellButtonStack.clear();
    }

    /**
     * Updates the occupied cells list with the recently played cells.
     */
    public void updateCells() {
        while (!recentlyPlayedCellStack.isEmpty())
            occupiedCells.add(recentlyPlayedCellStack.pop());
    }

    /**
     * Converts the recently played cell stack to an array.
     *
     * @return An array of cells representing the recently played cells.
     */
    public Cell[] stackToArray() {
        Cell[] cellArray = new Cell[recentlyPlayedCellStack.size()];
        recentlyPlayedCellStack.toArray(cellArray);
        return cellArray;
    }

    /**
     * Checks if the board is connected.
     *
     * @return True if the board is connected, false otherwise.
     */
    public boolean isConnected() {
        if (occupiedCells.size() == 0)
            return true;

        Cell[] recentlyPlayedCellArray = cellToArray();

        for (Cell currentCell : recentlyPlayedCellArray) {
            if (traverseLeftUntil(currentCell) || traverseRightUntil(currentCell) ||
                    traverseUpUntil(currentCell) || traverseDownUntil(currentCell)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Checks the leftmost cells connected to the given cell.
     *
     * @param cell The cell to check.
     * @return The leftmost connected cell.
     */
    public Cell checkLeftCells(Cell cell) {
        Cell current = cell;
        while (current.getLeft() != null) {
            if (current.getLeft().getTile() != null)
                current = current.getLeft();
            else
                break;
        }
        return current;
    }

    /**
     * Retrieves the topmost cell connected to the given cell.
     *
     * @param cell The cell to check.
     * @return The topmost connected cell.
     */
    public Cell getTopCell(Cell cell) {
        Cell current = cell;
        while (current.getTop() != null) {
            if (current.getTop().getTile() != null)
                current = current.getTop();
            else
                break;
        }
        return current;
    }

    /**
     * Traverses left from the given cell until an occupied cell is found.
     *
     * @param cell The starting cell.
     * @return True if an occupied cell is found, false otherwise.
     */
    public boolean traverseLeftUntil(Cell cell) {
        Cell current = cell;
        while (current.getLeft() != null) {
            if (current.getLeft().getTile() != null) {
                current = current.getLeft();
                if (occupiedCells.contains(current))
                    return true;
            } else {
                break;
            }
        }
        return false;
    }

    /**
     * Traverses right from the given cell until an occupied cell is found.
     *
     * @param cell The starting cell.
     * @return True if an occupied cell is found, false otherwise.
     */
    public boolean traverseRightUntil(Cell cell) {
        Cell current = cell;
        while (current.getRight() != null) {
            if (current.getRight().getTile() != null) {
                current = current.getRight();
                if (occupiedCells.contains(current))
                    return true;
            } else {
                break;
            }
        }
        return false;
    }

    /**
     * Traverses up from the given cell until an occupied cell is found.
     *
     * @param cell The starting cell.
     * @return True if an occupied cell is found, false otherwise.
     */
    public boolean traverseUpUntil(Cell cell) {
        Cell current = cell;
        while (current.getTop() != null) {
            if (current.getTop().getTile() != null) {
                current = current.getTop();
                if (occupiedCells.contains(current))
                    return true;
            } else {
                break;
            }
        }
        return false;
    }

    /**
     * Traverses down from the given cell until an occupied cell is found.
     *
     * @param cell The starting cell.
     * @return True if an occupied cell is found, false otherwise.
     */
    public boolean traverseDownUntil(Cell cell) {
        Cell current = cell;
        while (current.getBottom() != null) {
            if (current.getBottom().getTile() != null) {
                current = current.getBottom();
                if (occupiedCells.contains(current))
                    return true;
            } else {
                break;
            }
        }
        return false;
    }

    /**
     * Checks if the board is horizontally linked.
     *
     * @return True if the board is horizontally linked, false otherwise.
     */
    public boolean isHorizontallyLinked() {
        Cell currentCell = recentlyPlayedCellStack.peek();

        while (currentCell.getLeft() != null) {
            if (currentCell.getLeft().getTile() != null)
                currentCell = currentCell.getLeft();
            else
                break;
        }

        Cell[] recentlyPlayedCellArray = cellToArray();
        int count = 0;

        if (isCellRecentlyPlayed(currentCell, recentlyPlayedCellArray))
            count++;

        while (currentCell.getRight() != null) {
            if (currentCell.getRight().getTile() != null) {
                currentCell = currentCell.getRight();
                if (isCellRecentlyPlayed(currentCell, recentlyPlayedCellArray))
                    count++;
            } else {
                break;
            }
        }

        return count == recentlyPlayedCellArray.length;
    }

    /**
     * Checks if the board is vertically linked.
     *
     * @return True if the board is vertically linked, false otherwise.
     */
    public boolean isVerticallyLinked() {
        Cell currentCell = recentlyPlayedCellStack.peek();

        while (currentCell.getTop() != null) {
            if (currentCell.getTop().getTile() != null)
                currentCell = currentCell.getTop();
            else
                break;
        }

        Cell[] recentlyPlayedCellArray = cellToArray();
        int count = 0;

        if (isCellRecentlyPlayed(currentCell, recentlyPlayedCellArray))
            count++;

        while (currentCell.getBottom() != null) {
            if (currentCell.getBottom().getTile() != null) {
                currentCell = currentCell.getBottom();
                if (isCellRecentlyPlayed(currentCell, recentlyPlayedCellArray))
                    count++;
            } else {
                break;
            }
        }

        return count == recentlyPlayedCellArray.length;
    }

    /**
     * Checks if the recently played cells form correct words horizontally.
     *
     * @return True if the words are correct, false otherwise.
     */
    public boolean isWordCorrectHorizonatally() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                Cell currentCell = board.cellMatrix[i][j];

                if (currentCell.getTile() != null)
                    sb.append(currentCell.getTile().getLetter());
                else if ((currentCell.getTile() == null && sb.length() > 1) || (j == 14 && sb.length() > 1)) {
                    String word = sb.toString();

                    if (!dict.isValidWord(word)) {
                        sb.setLength(0);
                        return false;
                    } else {
                        sb.setLength(0);
                    }
                } else if ((currentCell.getTile() == null && sb.length() == 1) || (j == 14 && sb.length() == 1)) {
                    sb.setLength(0);
                }
            }

            sb.setLength(0);
        }
        return true;
    }

    /**
     * Checks if the recently played cells form correct words vertically.
     *
     * @return True if the words are correct, false otherwise.
     */
    public boolean isWordCorrectVertically() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                Cell currentCell = board.cellMatrix[j][i];
                if (currentCell.getTile() != null)
                    sb.append(currentCell.getTile().getLetter());
                else if ((currentCell.getTile() == null && sb.length() > 1) || (j == 14 && sb.length() > 1)) {
                    String word = sb.toString();

                    if (!dict.isValidWord(word)) {
                        sb.setLength(0);
                        return false;
                    } else {
                        sb.setLength(0);
                    }
                } else if ((currentCell.getTile() == null && sb.length() == 1) || (j == 14 && sb.length() == 1)) {
                    sb.setLength(0);
                }
            }
            sb.setLength(0);
        }
        return true;
    }

    /**
     * Converts the recently played cell stack to an array.
     *
     * @return An array of cells representing the recently played cells.
     */
    public Cell[] cellToArray() {
        int size = recentlyPlayedCellStack.size();
        Cell[] cellStack = new Cell[size];
        recentlyPlayedCellStack.toArray(cellStack);
        return cellStack;
    }

    /**
     * Checks if a given cell is in the recently played cells stack.
     *
     * @param currentCell     The cell to check.
     * @param recentlyPlayedCellArray The array of recently played cells.
     * @return True if the cell is in the recently played cells, false otherwise.
     */
    public boolean isCellRecentlyPlayed(Cell currentCell, Cell[] recentlyPlayedCellArray) {
        if (currentCell == null) return false;
        for (Cell i : recentlyPlayedCellArray) {
            if (i == currentCell) return true;
        }
        return false;
    }
}
