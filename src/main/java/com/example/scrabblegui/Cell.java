package com.example.scrabblegui;

import java.io.Serializable;

public class Cell implements Serializable {
    private Tile tile;
    private Cell top, left, right, bottom;
    private String bonus;

    /**
     * Constructor for the Cell class.
     * Initializes the cell with default values.
     */
    public Cell(){
        tile = null;
        top = left = right = bottom = null;
    }

    /**
     * Set the tile on the cell.
     *
     * @param tile The tile to be set on the cell.
     */
    public void setTile(Tile tile)
    {
        this.tile = tile;
    }

    /**
     * Get the tile on the cell.
     *
     * @return The tile on the cell.
     */
    public Tile getTile()
    {
        return this.tile;
    }

    /**
     * Set the bonus on the cell.
     *
     * @param bonus The bonus to be set on the cell.
     */
    public void setBonus(String bonus)
    {
        this.bonus = bonus;
    }

    /**
     * Get the bonus on the cell.
     *
     * @return The bonus on the cell.
     */
    public String getBonus()
    {
        return this.bonus;
    }

    /**
     * Set the cell above the current cell.
     *
     * @param cell The cell to be set as the top cell.
     */
    public void setTop(Cell cell)
    {
        this.top = cell;
    }

    /**
     * Set the cell to the left of the current cell.
     *
     * @param cell The cell to be set as the left cell.
     */
    public void setLeft(Cell cell)
    {
        this.left = cell;
    }

    /**
     * Set the cell to the right of the current cell.
     *
     * @param cell The cell to be set as the right cell.
     */
    public void setRight(Cell cell)
    {
        this.right = cell;
    }

    /**
     * Set the cell below the current cell.
     *
     * @param cell The cell to be set as the bottom cell.
     */
    public void setBottom(Cell cell)
    {
        this.bottom = cell;
    }

    /**
     * Get the cell above the current cell.
     *
     * @return The top cell.
     */
    public Cell getTop()
    {
        return this.top;
    }

    /**
     * Get the cell to the left of the current cell.
     *
     * @return The left cell.
     */
    public Cell getLeft()
    {
        return this.left;
    }

    /**
     * Get the cell to the right of the current cell.
     *
     * @return The right cell.
     */
    public Cell getRight()
    {
        return this.right;
    }

    /**
     * Get the cell below the current cell.
     *
     * @return The bottom cell.
     */
    public Cell getBottom()
    {
        return this.bottom;
    }
}
