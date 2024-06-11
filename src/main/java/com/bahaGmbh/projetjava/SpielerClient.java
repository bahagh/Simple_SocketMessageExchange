package com.bahaGmbh.projetjava;

import java.io.*;
import java.net.*;
import java.util.logging.*;

public class SpielerClient {
    private static final int PORT = 12345;
    private static final Logger logger = Logger.getLogger(SpielerClient.class.getName());
    private String name;
    private boolean isFirstClient;
    private int messageCounter = 0;

    public SpielerClient(String name) {
        this.name = name;
    }

    public void start() {
        try (Socket socket = new Socket("localhost", PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in))) {

            // Send client's name to server
            out.println(name);

            // Receive the client role from the server
            isFirstClient = Boolean.parseBoolean(in.readLine());

            logger.info(name + " (Player) connected to Game Server as " + (isFirstClient ? "first" : "second") + " player.");

            if (isFirstClient) {
                // Initial message sender
                System.out.print("You get to start the game ! Enter message: ");
                String initialMessage = userInput.readLine();
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

            logger.info(name + " (Player) finished communication after sending 10 messages and receiving 10 messages !");
        } catch (IOException e) {
            logger.log(Level.SEVERE, name + " (Player) encountered an error", e);
        }
    }

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
