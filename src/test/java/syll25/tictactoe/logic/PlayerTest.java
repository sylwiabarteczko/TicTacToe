package syll25.tictactoe.logic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayerTest {
    @Test
    public void testGetSymbol() {
        //given
        Player player = new Player("Player1", 'S');
        //when
        //then
        assertEquals('S', player.getSymbol());
    }

    @Test
    public void testGetName() {
        //given
        Player player = new Player("Player1", 'O');
        //when
        //then
        assertEquals("Player1", player.getName());
    }
//    @Test
//    public void testToString () {
//        //given
//        Player player = new Player("Player1", 'Z');
//        //when
//        //then
//        assertEquals('Z', player.toString());
//    }
}
