package com.example.scrabblegui;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

/**
 * The SharedModel class represents the shared data model for the Scrabble game.
 * It holds the current state of the game, including the board, hand, player, scrabble instance, and score label.
 */
public class SharedModel implements Serializable {
    private Board board;
    private Hand hand;
    private Player player;
    private Scrabble scrabble;
    private JLabel scoreLabel = new JLabel("0");

    private JButton[] playerTiles;
    /**
     * Initializes a new instance of the SharedModel class.
     * It creates the board, player, hand, scrabble instance, and sets the initial score label.
     */
    public SharedModel() {
        board = new Board();
        player = new Player();
        scrabble = new Scrabble(player, board);
        hand = new Hand();
    }

    /**
     * Retrieves the current board.
     *
     * @return The Board object representing the game board.
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Retrieves the current hand.
     *
     * @return The Hand object representing the player's hand.
     */
    public Hand getHand() {
        return hand;
    }

    public void setPlayerTiles(JButton[] playerTiles) {
        this.playerTiles = playerTiles;
    }

    /**
     * Retrieves the current player.
     *
     * @return The Player object representing the player.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Retrieves the current Scrabble game instance.
     *
     * @return The Scrabble object representing the game.
     */
    public Scrabble getScrabble() {
        return scrabble;
    }

    /**
     * Generates a new tile for the player.
     *
     * @return True if a tile was generated, false otherwise.
     */
    public boolean generateTile() {
        boolean result = true;
        if (hand.bagIsEmpty() || (player.getSize() == 7))
            result = false;
        else {
            Tile newTile = hand.getNextTile();
            player.addTile(newTile);
        }
        return result;
    }

    /**
     * Handles the event when the shuffle button is clicked.
     * Randomizes the player's tiles and organizes the hand.
     */
    public void shuffleButtonClicked() {
        player.randomizeTiles();
        player.organizeHand();
    }

    /**
     * Handles the event when the undo button is clicked.
     * Undoes the last move by removing the recently played tile from the board and adding it back to the player's hand.
     */
    public void undoButtonClicked() {
        if (scrabble.recentlyPlayedTileStack.size() > 0 && scrabble.recentlyPlayedCellStack.size() > 0 && scrabble.recentlyPlayedCellButtonStack.size() > 0 && scrabble.rackTileSelected == null) {
            Tile recentTilePlayed = scrabble.recentlyPlayedTileStack.pop();
            player.addTile(recentTilePlayed);
            Cell recentCell = scrabble.recentlyPlayedCellStack.pop();
            recentCell.setTile(null);
            JButton recentCellButton = scrabble.recentlyPlayedCellButtonStack.pop();

            if (recentCell.getBonus() != null) {
                recentCellButton.setText(recentCell.getBonus());

                if (recentCell.getBonus().equals("DL")) {
                    recentCellButton.setBackground(new Color(1, 104, 145));
                } else if (recentCell.getBonus().equals("TL")) {
                    recentCellButton.setBackground(new Color(4, 65, 94));
                } else {
                    recentCellButton.setBackground(new Color(186, 69, 51));
                }
            } else {
                recentCellButton.setText("");
                recentCellButton.setBackground(new Color(57, 57, 57));


            }

            player.organizeHand();
        }
    }

    /**
     * Handles the event when the submit button is clicked.
     * Checks if the current board configuration is valid and updates the score label.
     * Generates new tiles for the player if possible.
     */
    public void submitButtonClicked() {
        if (scrabble.checkBoard()) {
            scoreLabel.setText(player.getScore());

            while (true) {
                if (!generateTile()) {
                    break;
                }
            }
        } else {
            System.out.println("Move denied");
        }
    }

    /**
     * Handles the event when the exit button is clicked.
     * Exits the application.
     */
    public void exitButtonClicked() {
        System.exit(0);
    }

    /**
     * Sets the board in the shared model.
     *
     * @param board The Board object representing the game board.
     */
    public void setBoard(Board board) {
        this.board = board;
    }

    /**
     * Sets the hand in the shared model.
     *
     * @param hand The Hand object representing the player's hand.
     */
    public void setHand(Hand hand) {
        this.hand = hand;
    }

    /**
     * Sets the player in the shared model.
     *
     * @param player The Player object representing the player.
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Sets the Scrabble game instance in the shared model.
     *
     * @param scrabble The Scrabble object representing the game.
     */
    public void setScrabble(Scrabble scrabble) {
        this.scrabble = scrabble;
    }

    /**
     * Sets the score label in the shared model.
     *
     * @param scoreLabel The JLabel object representing the score label.
     */
    public void setScoreLabel(JLabel scoreLabel) {
        this.scoreLabel = scoreLabel;
    }
}
