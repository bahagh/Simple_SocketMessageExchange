/**
 * Represents the main class of the game (5.for the part where instances with the same java process) .
 * Responsible for initializing players and facilitating message exchange.
 */

package com.bahaGmbh.projetjava;

import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the name of the initiator:");
        String initiatorName = scanner.nextLine();

        System.out.println("Enter the name of the other player:");
        String otherPlayerName = scanner.nextLine();

        Player initiator = new Player(initiatorName);
        Player otherPlayer = new Player(otherPlayerName);

        initiator.setOtherPlayer(otherPlayer);
        otherPlayer.setOtherPlayer(initiator);

        System.out.println("Enter the initial message:");
        String initialMessage = scanner.nextLine();

        initiator.sendMessage(initialMessage);
        System.out.println("Conversation finished ! both players have sent and received 10 messages .");
        scanner.close();    
    }
}
