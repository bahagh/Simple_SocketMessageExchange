package com.bahaGmbh.projetjava;

import org.junit.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import static org.junit.Assert.assertEquals;

public class MainTest {
    
    @Test
    public void testMainMethod() {
        // Arrange
        String input = "Initiator\nOther Player\nInitial Message\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        
        // Act
        Main.main(new String[]{});
        
        // Assert
        assertEquals("Initiator sent: Initial Message\nConversation finished ! both players have sent and received 10 messages .\n", outContent.toString());
    }
}
