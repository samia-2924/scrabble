package com.example.scrabblegui;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Player implements Serializable {
    private Tile[] tiles;
    private int score;

    /**
     * Constructor for the Player class.
     * Initializes the player's tiles and score.
     */
    public Player() {
        tiles = new Tile[7];
        score = 0;
    }

    /**
     * Get the score of the player.
     *
     * @return The score of the player as a string.
     */
    public String getScore() {
        return String.valueOf(score);
    }

    /**
     * Set the score of the player.
     *
     * @param num The score to be set.
     */
    public void setScore(int num) {
        score = num;
    }

    /**
     * Add points to the player's score.
     *
     * @param num The points to be added.
     */
    public void addScore(int num) {
        score = score + num;
    }

    /**
     * Randomize the order of the tiles in the player's hand.
     */
    public void randomizeTiles() {
        List<Tile> list = Arrays.asList(tiles);
        Collections.shuffle(list);
        list.toArray(tiles);
    }

    /**
     * Organize the player's hand by moving null tiles to the end of the hand.
     */
    public void organizeHand() {
        int i = 0;
        while (i < 6) {
            if(tiles[i] == null)	{
                for(int j = i + 1; j < 7; j++){
                    if(tiles[j] != null){
                        tiles[i] = tiles[j];
                        tiles[j] = null;
                        break;
                    }
                }
            }
            i++;
        }
    }

    /**
     * Remove a tile from the player's hand at the specified index.
     *
     * @param index The index of the tile to be removed.
     * @return The removed tile.
     */
    public Tile removeTile(int index) {
        Tile temp = tiles[index];
        tiles[index] = null;
        return temp;
    }

    /**
     * Add a tile to the player's hand.
     *
     * @param tile The tile to be added.
     */
    public void addTile(Tile tile) {
        for(int i = 0; i < tiles.length; i++)
            if (tiles[i] == null) {
                tiles[i] = tile;
                return;
            }
    }

    /**
     * Get the number of non-null tiles in the player's hand.
     *
     * @return The size of the player's hand.
     */
    public int getSize() {
        return (int) Arrays.stream(tiles).filter(Objects::nonNull).count();
    }

    /**
     * Get the tiles in the player's hand.
     *
     * @return The tiles in the player's hand.
     */
    public Tile[] getTiles() {
        return this.tiles;
    }
}
