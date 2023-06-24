/**
 * The Tile class represents a Scrabble tile with a letter and its corresponding points.
 * It implements the Serializable interface to allow the object to be serialized and deserialized.
 */
package com.example.scrabblegui;

import java.io.Serializable;

public class Tile implements Serializable {
    private String letter; // The letter on the tile
    private int points; // The points associated with the letter

    /**
     * Constructs a Tile object with the given letter and points.
     *
     * @param letter The letter on the tile.
     * @param points The points associated with the letter.
     */
    public Tile(String letter, int points) {
        this.letter = letter;
        this.points = points;
    }

    /**
     * Retrieves the letter on the tile.
     *
     * @return The letter on the tile.
     */
    public String getLetter() {
        return letter;
    }

    /**
     * Retrieves the points associated with the letter on the tile.
     *
     * @return The points associated with the letter on the tile.
     */
    public int getPoints() {
        return points;
    }
}
