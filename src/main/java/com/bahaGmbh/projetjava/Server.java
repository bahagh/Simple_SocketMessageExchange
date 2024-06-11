package com.bahaGmbh.projetjava;

import java.io.*;
import java.net.*;
import java.util.logging.*;

public class Server {
    private static final int PORT = 12345;
    private static final Logger logger = Logger.getLogger(Server.class.getName());
    private String firstClientName;
    private boolean isFirstClientAssigned = false;

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

    private String getClientName(BufferedReader in) throws IOException {
        return in.readLine();
    }

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
                if (messageFromClient1 == null) {
                    logger.warning("Connection closed by " + client1Name + ".");
                    break;
                }
                logger.info("Received from " + client1Name + ": " + messageFromClient1);

                // Send to Client 2
                out2.println(messageFromClient1);
                logger.info("Sent to " + client2Name + ": " + messageFromClient1);

                // Receive from Client 2
                messageFromClient2 = in2.readLine();
                if (messageFromClient2 == null) {
                    logger.warning("Connection closed by " + client2Name + ".");
                    break;
                }
                logger.info("Received from " + client2Name + ": " + messageFromClient2);

                // Send to Client 1
                out1.println(messageFromClient2);
                logger.info("Sent to " + client1Name + ": " + messageFromClient2);

                messageCounter++;
            }

            logger.info("Game finished after relaying 20 messages between players.");
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error during client communication", e);
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }
}
