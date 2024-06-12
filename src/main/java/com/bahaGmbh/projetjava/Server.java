package com.bahaGmbh.projetjava;

import java.io.*;
import java.net.*;
import java.util.logging.*;

/**
 * The Server class represents the game server 
 * that facilitates communication between two clients.
 */
public class Server {
    private static final int PORT = 12345;
    private static final Logger logger = Logger.getLogger(Server.class.getName());
    private String firstClientName;
    private boolean isFirstClientAssigned = false;

    /**
     * Starts the server and waits for players (clients) to connect
     */
    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            logger.info("Game started. Waiting for Players to connect...");

            // Accept the first client
            Socket client1Socket = serverSocket.accept();
            BufferedReader in1 = new BufferedReader(new InputStreamReader(client1Socket.getInputStream()));
            String client1Name = getClientName(in1);
            logger.info(client1Name + " connected.");
            firstClientName = client1Name;
            isFirstClientAssigned = true;
            PrintWriter out1 = new PrintWriter(client1Socket.getOutputStream(), true);
            out1.println("true"); // Assign the first client as 'true'

            // Accept the second client
            Socket client2Socket = serverSocket.accept();
            BufferedReader in2 = new BufferedReader(new InputStreamReader(client2Socket.getInputStream()));
            String client2Name = getClientName(in2);
            logger.info(client2Name + " connected.");
            PrintWriter out2 = new PrintWriter(client2Socket.getOutputStream(), true);
            out2.println("false"); // Assign the second client as 'false'

            // Handle communication between clients
            handleClientCommunication(client1Socket, client2Socket, client1Name, client2Name);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Game Server encountered an error", e);
        }
    }

    /**
     * Reads the client's name from the input stream.
     */
    private String getClientName(BufferedReader in) throws IOException {
        String name = in.readLine();
        if (name == null || name.isEmpty()) {
            throw new IOException("Client name cannot be null or empty.");
        }
        return name;
    }

    /**
     *  message exchange between two connected clients
     */ 
    private void handleClientCommunication(Socket client1, Socket client2, String client1Name, String client2Name) {
        try (
            BufferedReader in1 = new BufferedReader(new InputStreamReader(client1.getInputStream()));
            PrintWriter out1 = new PrintWriter(client1.getOutputStream(), true);
            BufferedReader in2 = new BufferedReader(new InputStreamReader(client2.getInputStream()));
            PrintWriter out2 = new PrintWriter(client2.getOutputStream(), true);
        ) {
            String messageFromClient1;
            String messageFromClient2;
            int messageCounter = 0;

            // Initial message exchange
            while (messageCounter < 10) {
                // Receive from Client 1
                messageFromClient1 = in1.readLine();
                if (messageFromClient1 == null || messageFromClient1.equals("DISCONNECT")) {
                    logger.warning("Connection closed by " + client1Name + ".");
                    break;
                }
                logger.info("Received from " + client1Name + ": " + messageFromClient1);

                // Send to Client 2
                out2.println(messageFromClient1);
                logger.info("Sent to " + client2Name + ": " + messageFromClient1);

                // Receive from Client 2
                messageFromClient2 = in2.readLine();
                if (messageFromClient2 == null || messageFromClient2.equals("DISCONNECT")) {
                    logger.warning("Connection closed by " + client2Name + ".");
                    break;
                }
                logger.info("Received from " + client2Name + ": " + messageFromClient2);

                // Send to Client 1
                out1.println(messageFromClient2);
                logger.info("Sent to " + client1Name + ": " + messageFromClient2);

                messageCounter++;
            }

            /*  Final message handling outside loop to ensure even count             
                (After Player 1 sends its 10th message, it will wait for the 10th message from Player 2, 
                but because the loop condition checks the messageCounter before receiving this message, 
                the loop exits after sending the 10th message and receiving the 9th)
                
             */

            messageFromClient1 = in1.readLine();
            if (messageFromClient1 != null && !messageFromClient1.equals("DISCONNECT")) {
                out2.println(messageFromClient1);
                logger.info("Final exchange: Sent to " + client2Name + ": " + messageFromClient1);
            }

            messageFromClient2 = in2.readLine();
            if (messageFromClient2 != null && !messageFromClient2.equals("DISCONNECT")) {
                out1.println(messageFromClient2);
                logger.info("Final exchange: Sent to " + client1Name + ": " + messageFromClient2);
            }

            logger.info("Game finished after relaying 20 messages between players.");
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error during client communication", e);
        } finally {
            // Ensure sockets are closed properly
            try {
                client1.close();
                client2.close();
            } catch (IOException e) {
                logger.log(Level.SEVERE, "Error closing client sockets", e);
            }
        }
    }

    /**
     * The main method to start the server.
     */
    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }
}

