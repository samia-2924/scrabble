package com.example.scrabblegui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Hand implements Serializable {
    private ArrayList<Tile> tiles;

    private static Map<Character, Integer> letterPoints = createLetterPointsMap();

    /**
     * Constructor for the Hand class.
     * Initializes the hand, populates it with tiles, and shuffles the tiles.
     */
    public Hand() {
        tiles = new ArrayList<>();
        populateHand();
        shuffleHand();
    }

    /**
     * Get the next tile from the hand and remove it from the hand.
     *
     * @return The next tile from the hand.
     */
    public Tile getNextTile() {
        return tiles.remove(0);
    }

    /**
     * Check if the bag is empty (hand is empty).
     *
     * @return True if the hand is empty, false otherwise.
     */
    public boolean bagIsEmpty() {
        return tiles.isEmpty();
    }

    /**
     * Populate the hand with tiles based on their frequency and points.
     */
    private void populateHand() {
        createTile("EAIORNLTUS", 1, 9);
        createTile("DG", 2, 4);
        createTile("BCMP", 3, 3);
        createTile("FHVWY", 4, 3);
        createTile("K", 5, 1);
        createTile("JX", 8, 1);
        createTile("QZ", 10, 1);
    }

    /**
     * Create tiles with the given letters, points, and count and add them to the hand.
     *
     * @param letters The letters of the tiles.
     * @param points  The points of the tiles.
     * @param count   The number of tiles to create.
     */
    private void createTile(String letters, int points, int count) {
        for (int i = 0; i < count; i++) {
            for (char letter : letters.toCharArray()) {
                tiles.add(new Tile(Character.toString(letter), points));
            }
        }
    }

    /**
     * Shuffle the tiles in the hand.
     */
    private void shuffleHand() {
        Collections.shuffle(tiles);
    }

    /**
     * Create and initialize the letter points map.
     *
     * @return The created letter points map.
     */
    private static Map<Character, Integer> createLetterPointsMap() {
        letterPoints = new HashMap<>();

        char[] letters = {'E', 'A', 'I', 'O', 'N', 'R', 'T', 'L', 'S', 'U', 'D', 'G', 'B', 'C', 'M', 'P', 'F', 'H', 'V', 'W', 'Y', 'K', 'J', 'X', 'Q', 'Z'};
        int[] points = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 3, 3, 3, 3, 4, 4, 4, 4, 4, 5, 8, 8, 10, 10};

        for (int i = 0; i < letters.length; i++) {
            letterPoints.put(letters[i], points[i]);
        }

        return letterPoints;
    }
}
