package com.example.scrabblegui;

import javax.swing.*;
import java.awt.*;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 * The ScrabbleGUI class represents the graphical user interface for the Scrabble game.
 */
public class ScrabbleGUI extends JFrame {
    private JButton[] playerTiles;
    private JButton exitButton;
    private JPanel boardPanel, scrabbleGrid, playerPanel;

    private Board board;
    private Hand hand;
    private Player player;
    private Scrabble scrabble;
    private JLabel scoreLabel;
    private SharedModel sharedModel;
    private ScrabbleClient scrabbleClient;

    /**
     * The main method creates an instance of the ScrabbleGUI and starts the application.
     *
     * @param args Command line arguments (unused).
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                ScrabbleGUI scrabbleGUI = new ScrabbleGUI();
                scrabbleGUI.setUndecorated(true);
                scrabbleGUI.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Constructs a ScrabbleGUI object.
     */
    public ScrabbleGUI() {
        sharedModel = new SharedModel();
        scrabbleClient = new ScrabbleClient();
        scrabbleClient.start();
        initializeGUI();
    }

    /**
     * Initializes the graphical user interface.
     */
    private void initializeGUI() {
        setBounds(100, 100, 870, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);
        getContentPane().setBackground(new Color(1, 1, 0));

        gridPanel();
        titlePanel();
        playersTilePanel();

        board = sharedModel.getBoard();
        player = sharedModel.getPlayer();
        scrabble = sharedModel.getScrabble();
        hand = sharedModel.getHand();

        createCellButtons(board.cellMatrix);
        givePlayerStartingTiles();
        playersTiles();
        updatePlayerRackGUI();
    }

    /**
     * Creates the panel for the game board.
     */
    private void gridPanel() {
        boardPanel = new JPanel();
        boardPanel.setBounds(10, 63, 831, 541);
        boardPanel.setBackground(new Color(1, 1, 0));
        getContentPane().add(boardPanel);
        boardPanel.setLayout(new GridLayout(15, 15, 0, 0));
    }

    /**
     * Creates the title panel containing the game logo.
     */
    private void titlePanel() {
        playerPanel = new JPanel();
        playerPanel.setBounds(10, 615, 831, 143);
        playerPanel.setBackground(new Color(1, 1, 0));
        getContentPane().add(playerPanel);
        playerPanel.setLayout(null);
        {
            JPanel logoPanel = new JPanel();
            logoPanel.setBounds(10, 11, 831, 47);
            logoPanel.setBackground(new Color(1, 1, 0));
            getContentPane().add(logoPanel);
            logoPanel.setLayout(new BorderLayout(0, 0));
            {
                JLabel logoLabel = new JLabel("S  C  R  A  B  B  L  E");
                logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
                logoLabel.setFont(new Font("Calibri Light", Font.BOLD, 22));
                logoLabel.setForeground(Color.WHITE);
                logoPanel.add(logoLabel);
            }
        }
    }

    /**
     * Creates the panel for the player's tiles and action buttons.
     */
    private void playersTilePanel() {
        scrabbleGrid = new JPanel();
        scrabbleGrid.setBounds(165, 60, 500, 61);
        scrabbleGrid.setBackground(new Color(1, 1, 0));
        playerPanel.add(scrabbleGrid);
        scrabbleGrid.setLayout(new GridLayout(0, 7, 0, 0));

        JPanel actionPanel = new JPanel();
        actionPanel.setBounds(165, 12, 500, 37);
        actionPanel.setBackground(new Color(1, 1, 0));
        playerPanel.add(actionPanel);
        actionPanel.setLayout(new GridLayout(1, 4, 0, 0));

        JButton shuffleButton = new JButton("Shuffle");
        actionPanel.add(shuffleButton);
        shuffleButton.setBackground(new Color(62, 138, 204));
        shuffleButton.setForeground(Color.WHITE);

        JButton undoButton = new JButton("Undo");
        actionPanel.add(undoButton);
        undoButton.setBackground(new Color(219, 82, 75));
        undoButton.setForeground(Color.WHITE);

        JButton submitButton = new JButton("Submit");
        actionPanel.add(submitButton);
        submitButton.setBackground(new Color(123, 213, 0));
        submitButton.setForeground(Color.WHITE);

        exitButton = new JButton("Exit");
        actionPanel.add(exitButton);
        exitButton.setBackground(Color.DARK_GRAY);
        exitButton.setForeground(Color.WHITE);

        scoreLabel = new JLabel("0");
        scoreLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        scoreLabel.setFont(new Font("Tahoma", Font.PLAIN, 25));
        scoreLabel.setBounds(10, 39, 65, 53);
        scoreLabel.setForeground(Color.WHITE);
        playerPanel.add(scoreLabel);

        JLabel lblNewLabel = new JLabel("pts.");
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblNewLabel.setBounds(85, 48, 43, 42);
        lblNewLabel.setForeground(Color.WHITE);
        playerPanel.add(lblNewLabel);

        shuffleButton.addActionListener(e -> {
            updateSharedModel();
            sharedModel.shuffleButtonClicked();
            scrabbleClient.sendObject("SHUFFLE");
            scrabbleClient.sendObject(sharedModel);
            updatePlayerRackGUI();
        });

        undoButton.addActionListener(e -> {
            updateSharedModel();
            sharedModel.undoButtonClicked();

            scrabbleClient.sendObject("UNDO");
            scrabbleClient.sendObject(sharedModel);

            updatePlayerRackGUI();
        });

        submitButton.addActionListener(e -> {
            updateSharedModel();
            sharedModel.submitButtonClicked();
            scrabbleClient.sendObject("SUBMIT");
            scrabbleClient.sendObject(sharedModel);

            updatePlayerRackGUI();
        });

        exitButton.addActionListener(e -> {
            updateSharedModel();
            sharedModel.exitButtonClicked();
            scrabbleClient.sendObject("EXIT");
            scrabbleClient.sendObject(sharedModel);
        });
    }

    /**
     * Updates the shared model with the current state of the game.
     */
    private void updateSharedModel() {
        sharedModel.setPlayerTiles(playerTiles);
        sharedModel.setScrabble(scrabble);
        sharedModel.setBoard(board);
        sharedModel.setHand(hand);
        sharedModel.setScoreLabel(scoreLabel);
        sharedModel.setPlayer(player);
    }

    /**
     * Updates the GUI representation of the player's rack.
     */
    private void updatePlayerRackGUI() {
        for (int i = 0; i < 7; i++) {
            if (player.getTiles()[i] != null) {
                playerTiles[i].setText(player.getTiles()[i].getLetter());
                playerTiles[i].setBackground(new Color(206, 175, 67));
                playerTiles[i].setForeground(Color.WHITE);
            } else if (player.getTiles()[i] == null) {
                playerTiles[i].setText("");
                playerTiles[i].setBackground(Color.LIGHT_GRAY);
            }
        }
    }

    /**
     * Creates the buttons representing the cells on the game board.
     *
     * @param cellMatrix The matrix of cells representing the game board.
     */
    private void createCellButtons(Cell[][] cellMatrix) {
        JButton[][] cellButtons = new JButton[15][15];

        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                JButton cellButton = new JButton();
                cellButton.setBackground(new Color(57, 57, 57));
                Cell currentCell = cellMatrix[i][j];
                cellButton.addActionListener(e -> cellButtonClicked(cellButton, currentCell));
                cellButtons[i][j] = cellButton;

                if (cellMatrix[i][j].getBonus() != null) {
                    String bonus = cellMatrix[i][j].getBonus();
                    cellButtons[i][j].setText(bonus);
                    if (bonus.equals("X") || bonus.equals("...")) {
                        cellButtons[i][j].setBackground(new Color(186, 69, 51));
                    } else if (bonus.equals("DL")) {
                        cellButtons[i][j].setBackground(new Color(1, 104, 145));
                    } else if (bonus.equals("TL")) {
                        cellButtons[i][j].setBackground(new Color(4, 65, 94));
                    } else {
                        cellButtons[i][j].setBackground(new Color(186, 69, 51));
                    }
                }
            }
        }

        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                boardPanel.add(cellButtons[i][j]);
            }
        }
    }

    /**
     * Creates the buttons representing the player's tiles.
     */
    private void playersTiles() {
        playerTiles = new JButton[7];

        for (int i = 0; i < 7; i++) {
            JButton playerTile = createPlayerTileButton(i);
            playerTiles[i] = playerTile;
            scrabbleGrid.add(playerTile);
        }
    }

    /**
     * Generates a new tile for the player.
     *
     * @return true if a tile was generated, false otherwise.
     */
    private boolean generateTile() {
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
     * Gives the player the starting set of tiles.
     */
    private void givePlayerStartingTiles() {
        for (int i = 0; i < 7; i++) {
            generateTile();
        }
    }

    /**
     * Creates a button representing a player tile.
     *
     * @param index The index of the player tile.
     * @return The created JButton.
     */
    private JButton createPlayerTileButton(int index) {
        JButton playerTile = new JButton();
        playerTile.setBackground(new Color(206, 175, 67));
        playerTile.setText(player.getTiles()[index].getLetter());
        playerTile.setForeground(Color.WHITE);

        playerTile.addActionListener(e -> playerTileClicked(playerTile, index));

        return playerTile;
    }

    /**
     * Handles the event when a cell button is clicked.
     *
     * @param cellButton  The JButton representing the clicked cell.
     * @param currentCell The corresponding Cell object for the clicked cell.
     */
    private void cellButtonClicked(JButton cellButton, Cell currentCell) {
        if (scrabble.rackTileSelected != null && currentCell.getTile() == null) {
            currentCell.setTile(scrabble.rackTileSelected);
            cellButton.setText(scrabble.rackTileSelected.getLetter());
            cellButton.setBackground(Color.ORANGE);
            scrabble.rackTileSelected = null;
            scrabble.recentlyPlayedCellStack.push(currentCell);
            scrabble.recentlyPlayedCellButtonStack.push(cellButton);
        }
    }

    /**
     * Handles the event when a player tile button is clicked.
     *
     * @param playerTile The JButton representing the clicked player tile.
     * @param index      The index of the clicked player tile.
     */
    private void playerTileClicked(JButton playerTile, int index) {
        if (scrabble.rackTileSelected == null && !playerTile.getText().equals("")) {
            scrabble.rackTileSelected = player.removeTile(index);
            scrabble.recentlyPlayedTileStack.push(scrabble.rackTileSelected);

            playerTile.setBackground(Color.LIGHT_GRAY);
            playerTile.setText("");
        }
    }
}
