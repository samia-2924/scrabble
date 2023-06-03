package com.example.scrabblegui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Bag {
    private static final String TILE_SET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private List<Character> tiles;

    private static final int[] TILE_FREQUENCIES = {
            9, 2, 2, 4, 12, 2, 3, 2, 9, 1, 1, 4, 2, 6, 8, 2, 1, 6, 4, 6, 4, 2, 2, 1, 2, 1
    };

    public Bag() {
        tiles = new ArrayList<>();
        fillBag();
    }

    private void fillBag() {
        for (int i = 0; i < TILE_SET.length(); i++) {
            char tile = TILE_SET.charAt(i);
            int frequency = TILE_FREQUENCIES[i];
            for (int j = 0; j < frequency; j++) {
                tiles.add(tile);
            }
        }
        Collections.shuffle(tiles);
    }


    public boolean isEmpty() {
        return tiles.isEmpty();
    }

    public char drawTile() {
        int index = (int) (Math.random() * tiles.size());
        return tiles.remove(index);
    }
}