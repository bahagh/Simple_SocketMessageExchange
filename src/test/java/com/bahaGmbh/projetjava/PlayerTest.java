package com.bahaGmbh.projetjava;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class PlayerTest {

    @Test
    public void testSendMessage() {
        // Arrange
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");

        // Set player2 as the other player for player1
        player1.setOtherPlayer(player2);

        // Act
        player1.sendMessage("Hello from Player 1");

        // Assert
        // We expect the message to be received by player2
        assertEquals("Player 2 received: Hello from Player 1\nPlayer 2 sent: Hello from Player 1 0\n", systemOut());
    }

    @Test
    public void testReceiveMessage() {
        // Arrange
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");

        // Set player1 as the other player for player2
        player2.setOtherPlayer(player1);

        // Act
        player2.receiveMessage("Hello from Player 1");

        // Assert
        // We expect the message to be sent back by player2 with the counter appended
        assertEquals("Player 1 received: Hello from Player 1\nPlayer 1 sent: Hello from Player 1 0\n", systemOut());
    }

    // Helper method to capture System.out.println output
    private String systemOut() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        return outContent.toString();
    }
}
