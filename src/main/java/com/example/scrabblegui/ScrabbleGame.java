package com.example.scrabblegui;

import java.util.*;

public class ScrabbleGame {
    private static final int BOARD_SIZE = 15;
    private static final int MAX_TILES = 7;

    private Board board;
    private Dictionary dictionary;
    private List<Player> players;
    private Bag bag;
    private int currentPlayerIndex;

    public ScrabbleGame() {
        board = new Board(BOARD_SIZE);
        dictionary = new Dictionary();
        players = new ArrayList<>();
        bag = new Bag();
        currentPlayerIndex = 0;
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void startGame() {
        while (!isGameOver()) {
            refillTilesForAllPlayers();
            Player currentPlayer = players.get(currentPlayerIndex);
            System.out.println("\nIt's " + currentPlayer.getName() + "'s turn.");
            System.out.println("Current score: " + currentPlayer.getScore());

            displayBoard();
            currentPlayer.displayTiles();

            boolean validMove = false;
            while (!validMove) {
                String word = currentPlayer.getWord();

                if (word.isEmpty()) {
                    currentPlayer.refillTiles(bag);
                    validMove = true;
                    break;
                }

                int row = currentPlayer.getRow();
                int col = currentPlayer.getCol();
                boolean isHorizontal = currentPlayer.isHorizontal();

                if (board.isValidMove(word, row, col, isHorizontal) && dictionary.isValidWord(word)) {
                    int score = board.placeWord(word, row, col, isHorizontal);
                    currentPlayer.updateScore(score);
                    currentPlayer.refillTiles(bag);
                    validMove = true;
                } else {
                    System.out.println("Invalid move. Please try again.");
                }
            }

            currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        }

        System.out.println("\nGame over!");
        displayScores();
    }

    private void refillTilesForAllPlayers() {
        for (Player player : players) {
            player.clearTiles();
            player.refillTiles(bag);
        }
    }

    private boolean isGameOver() {
        return bag.isEmpty() && players.stream().allMatch(Player::hasNoTiles);
    }

    private void displayBoard() {
        System.out.println("\nCurrent board state:");
        System.out.println(board.toString());
    }

    private void displayScores() {
        System.out.println("\nFinal scores:");
        for (Player player : players) {
            System.out.println(player.getName() + ": " + player.getScore());
        }
    }

    public static void main(String[] args) {
        ScrabbleGame game = new ScrabbleGame();
        Player player1 = new Player("Ali");
        Player player2 = new Player("Umar");
        game.addPlayer(player1);
        game.addPlayer(player2);
        game.startGame();
    }
}