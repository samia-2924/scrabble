package com.example.scrabblegui;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ScrabbleServer {
    private static final int PORT = 12345;
    private SharedModel sharedModel;

    public static void main(String[] args) {
        ScrabbleServer server = new ScrabbleServer();
        server.start();
    }

    /**
     * Starts the Scrabble server.
     * The server listens for client connections, receives actions from clients, and updates the shared model accordingly.
     */
    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started");

            while (true) {
                try (
                        Socket clientSocket = serverSocket.accept();
                        ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
                        ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream())
                ) {
                    System.out.println("Client connected");
                    sharedModel = new SharedModel();

                    while (true) {
                        String action = (String) in.readObject();
                        sharedModel = (SharedModel) in.readObject();
                        System.out.println("Received action: " + action);

                        switch (action) {
                            case "SHUFFLE":
                                sharedModel.shuffleButtonClicked();
                                break;
                            case "UNDO":
                                sharedModel.undoButtonClicked();
                                break;
                            case "SUBMIT":
                                sharedModel.submitButtonClicked();
                                break;
                            case "EXIT":
                                sharedModel.exitButtonClicked();
                                break;
                            default:
                                System.out.println("Unknown action: " + action);
                        }
                        out.flush();
                    }
                } catch (IOException ex) {
                    System.out.println("Client disconnected");
                } catch (ClassNotFoundException ex) {
                    System.out.println("Error in deserializing object");
                }
            }
        } catch (IOException e) {
            System.err.println("Error in server socket: " + e.getMessage());
        }
    }
}
