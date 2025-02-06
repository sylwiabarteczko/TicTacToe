package syll25.tictactoe.web;

import org.junit.jupiter.api.Test;
import syll25.tictactoe.web.model.Game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class GameTest {

    @Test
    void testGameCreation() {
        Game game = new Game("---\n---\n---", "Player1", 'X', "Player2", 'O', "Player1", false);
        assertEquals("Player1", game.getCurrentPlayer());
        assertFalse(game.isGameOver());
    }

}
