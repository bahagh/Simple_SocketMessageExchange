/**
 * Represents a player in the messaging game.
 * Handles sending and receiving messages within the game.
 */

package com.bahaGmbh.projetjava;


class Player {
    private String name;
    private int messageCount = -1;
    private Player otherPlayer;

    public Player(String name) {
        this.name = name;
    }

/**
 * Sets another player for this player.
 * param otherPlayer: The other player to be set.
 */    
    public void setOtherPlayer(Player otherPlayer) {
        this.otherPlayer = otherPlayer;
    }


/**
 * Sends a message to the other player.
 * Limits the number of messages sent to 10.
 * param message: The message to be sent.
 */
public void sendMessage(String message) {
    if (messageCount < 9) {
        messageCount++;
        System.out.println(name + " sent: " + message);
        otherPlayer.receiveMessage(message);
    }
}

/**
 * receives and Sends back a message to the other player.
 */
public void receiveMessage(String message) {
    System.out.println(name + " received: " + message);
    // Append the counter to the message when sending it back.
    sendMessage(message + " " + (messageCount+1));
}
}
