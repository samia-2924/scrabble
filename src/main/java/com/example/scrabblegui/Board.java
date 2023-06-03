package com.example.scrabblegui;

public class Board {
    private char[][] grid;

    public Board(int size) {
        grid = new char[size][size];
    }

    public boolean isValidMove(String word, int row, int col, boolean isHorizontal) {
        int wordLength = word.length();
        int lastRow = row + (isHorizontal ? 0 : wordLength - 1);
        int lastCol = col + (isHorizontal ? wordLength - 1 : 0);

        if (lastRow >= 15 || lastCol >= 15) {
            return false;
        }

        for (int i = 0; i < wordLength; i++) {
            char tile = grid[row + (isHorizontal ? 0 : i)][col + (isHorizontal ? i : 0)];
            if (tile != 0 && tile != word.charAt(i)) {
                return false;
            }
        }

        return true;
    }


    public int placeWord(String word, int row, int col, boolean isHorizontal) {
        int score = 0;
        int wordMultiplier = 1;

        for (int i = 0; i < word.length(); i++) {
            char tile = word.charAt(i);
            int tileScore = getTileScore(tile);
            int letterMultiplier = getLetterMultiplier(row, col, isHorizontal, i);

            score += tileScore * letterMultiplier;
            grid[row + (isHorizontal ? 0 : i)][col + (isHorizontal ? i : 0)] = tile;

            wordMultiplier *= getWordMultiplier(row, col, isHorizontal, i);
        }


        return score * wordMultiplier;
    }

    private int getLetterMultiplier(int row, int col, boolean isHorizontal, int i) {
        int multiplier = 1;
        if (isHorizontal) {
            multiplier = getHorizontalLetterMultiplier(row, col + i);
        } else {
            multiplier = getVerticalLetterMultiplier(row + i, col);
        }
        return multiplier;
    }

    private int getWordMultiplier(int row, int col, boolean isHorizontal, int i) {
        int multiplier = 1;
        if (isHorizontal) {
            multiplier = getHorizontalWordMultiplier(row, col + i);
        } else {
            multiplier = getVerticalWordMultiplier(row + i, col);
        }
        return multiplier;
    }

    private int getTileScore(char tile) {
        int score = 0;
        switch (Character.toUpperCase(tile)) {
            case 'A':
            case 'E':
            case 'I':
            case 'O':
            case 'U':
            case 'L':
            case 'N':
            case 'S':
            case 'T':
            case 'R':
                score = 1;
                break;
            case 'D':
            case 'G':
                score = 2;
                break;
            case 'B':
            case 'C':
            case 'M':
            case 'P':
                score = 3;
                break;
            case 'F':
            case 'H':
            case 'V':
            case 'W':
            case 'Y':
                score = 4;
                break;
            case 'K':
                score = 5;
                break;
            case 'J':
            case 'X':
                score = 8;
                break;
            case 'Q':
            case 'Z':
                score = 10;
                break;
        }
        return score;
    }

    private int getHorizontalLetterMultiplier(int row, int col) {
        int multiplier = 1;
        if (row >= 0 && row < 15 && col >= 0 && col < 15) {
            char tile = grid[row][col];
            if (Character.isLetter(tile)) {
                if (Character.isUpperCase(tile)) {
                    multiplier = 2;
                } else {
                    multiplier = 3;
                }
            }
        }
        return multiplier;
    }

    private int getVerticalLetterMultiplier(int row, int col) {
        int multiplier = 1;
        if (row >= 0 && row < 15 && col >= 0 && col < 15) {
            char tile = grid[row][col];
            if (Character.isLetter(tile)) {
                if (Character.isUpperCase(tile)) {
                    multiplier = 2;
                } else {
                    multiplier = 3;
                }
            }
        }
        return multiplier;
    }

    private int getHorizontalWordMultiplier(int row, int col) {
        int multiplier = 1;
        if (row >= 0 && row < 15 && col >= 0 && col < 15) {
            char tile = grid[row][col];
            if (Character.isLetter(tile)) {
                if (Character.isUpperCase(tile)) {
                    multiplier = 2;
                } else {
                    multiplier = 3;
                }
            }
        }
        return multiplier;
    }

    private int getVerticalWordMultiplier(int row, int col) {
        int multiplier = 1;
        if (row >= 0 && row < 15 && col >= 0 && col < 15) {
            char tile = grid[row][col];
            if (Character.isLetter(tile)) {
                if (Character.isUpperCase(tile)) {
                    multiplier = 2;
                } else {
                    multiplier = 3;
                }
            }
        }
        return multiplier;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (char[] row : grid) {
            for (char tile : row) {
                sb.append(tile == 0 ? "." : tile);
                sb.append(" ");
            }
            sb.append("\n");
        }

        return sb.toString();
    }
}