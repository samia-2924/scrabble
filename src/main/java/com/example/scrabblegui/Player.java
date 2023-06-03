package com.example.scrabblegui;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Player {
    private String name;
    private List<Character> tiles;
    private int score;
    private Scanner scanner;
    private static final int MAX_TILES = 7;

    public Player(String name) {
        this.name = name;
        tiles = new ArrayList<>();
        score = 0;
        scanner = new Scanner(System.in);
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public void updateScore(int points) {
        score += points;
    }

    public void displayTiles() {
        System.out.println("Your tiles: " + tiles);
    }

    public void refillTiles(Bag bag) {
        while (tiles.size() < MAX_TILES && !bag.isEmpty()) {
            char tile = bag.drawTile();
            tiles.add(tile);
        }
    }
    public void clearTiles() {
        tiles.clear();
    }

    public boolean hasNoTiles() {
        return tiles.isEmpty();
    }

    public String getWord() {
        System.out.print("Enter word (leave blank to pass): ");
        return scanner.nextLine().toUpperCase();
    }


    public boolean isHorizontal() {
        System.out.print("Is the word horizontal? (Y/N): ");
        String choice = scanner.next();
        scanner.nextLine(); // Consume the newline character
        return choice.equalsIgnoreCase("Y");
    }

    public int getRow() {
        System.out.print("Enter row: ");
        int row;
        while (true) {
            try {
                row = Integer.parseInt(scanner.nextLine());
                if (row >= 0 && row < 15) {
                    break;
                } else {
                    System.out.println("Invalid input. Please enter a valid row within the board size.");
                    System.out.print("Enter row: ");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer for the row.");
                System.out.print("Enter row: ");
            }
        }
        return row;
    }

    public int getCol() {
        System.out.print("Enter column: ");
        int col;
        while (true) {
            try {
                col = Integer.parseInt(scanner.nextLine());
                if (col >= 0 && col < 15) {
                    break;
                } else {
                    System.out.println("Invalid input. Please enter a valid column within the board size.");
                    System.out.print("Enter column: ");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer for the column.");
                System.out.print("Enter column: ");
            }
        }
        return col;
    }
}