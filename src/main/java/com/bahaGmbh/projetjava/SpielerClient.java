package com.bahaGmbh.projetjava;

import java.io.*;
import java.net.*;
import java.util.logging.*;

/**
 * The SpielerClient class represents a client in a multiplayer game.
 * Each client connects to the server and exchanges messages with another client.
 */
public class SpielerClient {
    private static final int PORT = 12345;
    private static final Logger logger = Logger.getLogger(SpielerClient.class.getName());
    private String name;
    private boolean isFirstClient;
    private int messageCounter = 0;

    /**
     * Constructs a SpielerClient with the specified name.
     */
    public SpielerClient(String name) {
        this.name = name;
    }

    /**
     * Starts the client, connects to the server, and handles message exchange.
     */
    public void start() {
        try (Socket socket = new Socket("localhost", PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in))) {

            // Send client's name to server
            out.println(name);

            // Receive the client role from the server
            String role = in.readLine();
            if (role == null) {
                throw new IOException("Failed to receive role from server.");
            }
            isFirstClient = Boolean.parseBoolean(role);

            logger.info(name + " (Player) connected to Game Server as " + (isFirstClient ? "first" : "second") + " player.");

            if (isFirstClient) {
                // Initial message sender
                System.out.print("You get to start the game! Enter a message: ");
                String initialMessage = userInput.readLine();
                if (initialMessage == null || initialMessage.isEmpty()) {
                    throw new IOException("Initial message cannot be empty.");
                }
                out.println(initialMessage);
                logger.info(name + " (Player) sent: " + initialMessage);
                messageCounter++;  // Increment counter for the initial message
            }

            String receivedMessage;
            while (messageCounter < 10) {
                receivedMessage = in.readLine();
                if (receivedMessage == null) {
                    logger.warning(name + " (Player) connection closed.");
                    break;
                }
                logger.info(name + " (Player) received: " + receivedMessage);

                // Concatenate received message with counter and send back
                String messageToSend = receivedMessage + " " + messageCounter;
                out.println(messageToSend);
                logger.info(name + " (Player) sent: " + messageToSend);
                messageCounter++;  // Increment message counter after sending
            }

            // Final receive to ensure the client receives its 10th message
            if (messageCounter == 10) {
                receivedMessage = in.readLine();
                if (receivedMessage != null) {
                    logger.info(name + " (Player) received: " + receivedMessage);
                }
            }

            // Inform the server that the client is disconnecting
            out.println("DISCONNECT");

            logger.info(name + " (Player) finished communication after sending and receiving 10 messages!");
        } catch (UnknownHostException e) {
            logger.log(Level.SEVERE, name + " (Player) could not connect to the server (Unknown host).", e);
        } catch (IOException e) {
            logger.log(Level.SEVERE, name + " (Player) encountered an I/O error", e);
        } catch (Exception e) {
            logger.log(Level.SEVERE, name + " (Player) encountered an unexpected error", e);
        }
    }

    /**
     * The main method to start the client. Prompts the user to enter their name.
     */
    public static void main(String[] args) {
        BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.print("Enter your name: ");
            String name = userInput.readLine();

            SpielerClient client = new SpielerClient(name);
            client.start();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error reading input from console", e);
        }
    }
}
