package syll25.tictactoe.logic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayerTest {
    @Test
    public void testGetSymbol() {

        Player player = new Player("Player1", 'S');

        assertEquals('S', player.getSymbol());
    }

    @Test
    public void testGetName() {

        Player player = new Player("Player1", 'O');

        assertEquals("Player1", player.getName());
    }

}
