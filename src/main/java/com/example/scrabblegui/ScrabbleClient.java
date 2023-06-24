package com.example.scrabblegui;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class ScrabbleClient {
    private static final String HOST = "localhost";
    private static final int PORT = 12345;

    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public static void main(String[] args) {
        ScrabbleClient client = new ScrabbleClient();
        client.start();
    }

    /**
     * Starts the Scrabble client.
     * Connects to the server and initializes the input/output streams.
     */
    public void start() {
        try {
            socket = new Socket(HOST, PORT);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Sends an object to the server.
     *
     * @param obj The object to be sent.
     */
    public void sendObject(Object obj) {
        try {
            out.writeObject(obj);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
