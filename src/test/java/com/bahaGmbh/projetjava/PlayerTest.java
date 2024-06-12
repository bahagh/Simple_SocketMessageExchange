package com.bahaGmbh.projetjava;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    void testSendMessage() {
        Player player1 = new Player("Player1");
        Player player2 = new Player("Player2");
        player1.setOtherPlayer(player2);
        player2.setOtherPlayer(player1);

        // Sending 10 messages
        for (int i = 0; i < 10; i++) {
            player1.sendMessage("Message " + i);
        }

        // Player 1 can't send more than 10 messages
        player1.sendMessage("Extra message");
        assertEquals(player1.getMessageCount(), 9);

        // Player 2 should receive 10 messages
        assertEquals(player2.getMessageCount(), 9);
    }

    @Test
    void testReceiveMessage() {
        Player player1 = new Player("Player1");
        Player player2 = new Player("Player2");
        player1.setOtherPlayer(player2);
        player2.setOtherPlayer(player1);

        player1.sendMessage("Hello");
        assertEquals(player1.getMessageCount(), 9);
        assertEquals(player2.getMessageCount(), 9);

        player2.sendMessage("Hi");
        assertEquals(player1.getMessageCount(), 9);
        assertEquals(player2.getMessageCount(), 9);
    }

    @Test
    void testSendMessageWithCount() {
        Player player1 = new Player("Player1");
        Player player2 = new Player("Player2");
        player1.setOtherPlayer(player2);
        player2.setOtherPlayer(player1);

        player1.sendMessage("Hello");
        player2.sendMessage("Hi");

        assertEquals(player1.getMessageCount(), 9);
        assertEquals(player2.getMessageCount(), 9);
    }
}
