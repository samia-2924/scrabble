package com.example.scrabblegui;

import java.io.Serializable;

public class Board implements Serializable {
    protected Cell[][] cellMatrix;

    public Board() {
        createBoard();
        connectCells();
        createBonus();
    }

    /**
     * Get the matrix of cells representing the board.
     *
     * @return The matrix of cells.
     */
    public Cell[][] getCellMatrix() {
        return cellMatrix;
    }

    /**
     * Create the initial board by initializing each cell in the cell matrix.
     */
    public void createBoard() {
        cellMatrix = new Cell[15][15];

        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                cellMatrix[i][j] = new Cell();
            }
        }
    }

    /**
     * Connect each cell in the cell matrix to its neighboring cells.
     */
    public void connectCells() {
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                connectCell(i, j, i - 1, j, Direction.TOP);
                connectCell(i, j, i + 1, j, Direction.BOTTOM);
                connectCell(i, j, i, j - 1, Direction.LEFT);
                connectCell(i, j, i, j + 1, Direction.RIGHT);
            }
        }
    }

    /**
     * Create the bonus positions on the board and assign bonuses to those positions.
     */
    public void createBonus() {
        int[][] bonusPositions = {
                {7, 7}, {0, 0}, {7, 0}, {14, 0}, {0, 7}, {0, 14}, {7, 14}, {14, 7}, {14, 14}, {5, 1}, {9, 1}, {1, 5}, {5, 5},
                {9, 5}, {13, 5}, {1, 9}, {5, 9}, {9, 9}, {13, 9}, {5, 13}, {9, 13}, {1, 1}, {2, 2}, {3, 3}, {4, 4}, {4, 10}, {3, 11},
                {2, 12}, {1, 13}, {13, 1}, {12, 2}, {11, 3}, {10, 4}, {10, 10}, {11, 11}, {12, 12}, {13, 13}, {3, 0}, {11, 0},
                {0, 3}, {6, 2}, {7, 3}, {8, 2}, {14, 3}, {2, 6}, {6, 6}, {8, 6}, {12, 6}, {3, 7}, {11, 7}, {2, 8}, {6, 8}, {8, 8},
                {12, 8}, {0, 11}, {7, 11}, {14, 11}, {6, 12}, {8, 12}, {3, 14}, {6, 12}, {11, 14}
        }; // 38

        String[] bonuses = {
                "X", "TW", "TW", "TW", "TW", "TW", "TW", "TW", "TW", "TL", "TL", "TL", "TL", "TL", "TL", "TL", "TL", "TL", "TL", "TL",
                "TL", "TL", "DW", "DW", "DW", "DW", "DW", "DW", "DW", "DW", "DW", "DW", "DW", "DW", "DW", "DW", "DW", "DW", "DL", "DL", "DL",
                "DL", "DL", "DL", "DL", "DL", "DL", "DL", "DL", "DL", "DL", "DL", "DL", "DL", "DL", "DL", "DL", "DL", "DL", "DL", "DL", "DL",
                "DL", "DL", "DL", "DL"
        };

        for (int i = 0; i < bonusPositions.length; i++) {
            int[] position = bonusPositions[i];
            String bonus = bonuses[i];
            setBonus(position[0], position[1], bonus);
        }
    }

    /**
     * Set a bonus on a specific cell at the given row and column.
     *
     * @param row   The row index of the cell.
     * @param col   The column index of the cell.
     * @param bonus The bonus to be set on the cell.
     */
    private void setBonus(int row, int col, String bonus) {
        cellMatrix[row][col].setBonus(bonus);
    }

    /**
     * Connect a cell to its neighboring cell in the given direction.
     *
     * @param row        The row index of the current cell.
     * @param col        The column index of the current cell.
     * @param newRow     The row index of the neighboring cell.
     * @param newCol     The column index of the neighboring cell.
     * @param direction  The direction in which the cells are being connected.
     */
    private void connectCell(int row, int col, int newRow, int newCol, Direction direction) {
        if (newRow >= 0 && newRow < 15 && newCol >= 0 && newCol < 15) {
            Cell currentCell = cellMatrix[row][col];
            Cell connectedCell = cellMatrix[newRow][newCol];

            switch (direction) {
                case TOP -> currentCell.setTop(connectedCell);
                case BOTTOM -> currentCell.setBottom(connectedCell);
                case LEFT -> currentCell.setLeft(connectedCell);
                case RIGHT -> currentCell.setRight(connectedCell);
            }
        }
    }

    /**
     * Enumeration representing the directions for connecting cells.
     */
    enum Direction {
        TOP,
        BOTTOM,
        LEFT,
        RIGHT
    }
}
