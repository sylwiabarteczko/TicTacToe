package syll25.tictactoe.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import syll25.tictactoe.logic.state.StateDTO;
import syll25.tictactoe.web.model.GameStateDTO;
import syll25.tictactoe.web.service.GameService;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class GameServiceTest {

    @Autowired
    private GameService gameService;

    @Test
    void startNewGameTest() {
        Long gameId = gameService.startNewGame("Player1", "Player2", 3);
        assertNotNull(gameId);
        GameStateDTO gameState = gameService.loadGame(gameId);
        assertEquals(3, gameState.getSize());
    }

    @Test
    void makeMoveTest() {
        Long gameId = gameService.startNewGame("Player1", "Player2", 3);
        StateDTO gameStateDTO = gameService.makeMove(gameId, 0, 0);
        assertNotNull(gameStateDTO);
        assertFalse(gameStateDTO.isWinnerFound());
    }


}
