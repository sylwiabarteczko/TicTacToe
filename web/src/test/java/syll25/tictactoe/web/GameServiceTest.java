package syll25.tictactoe.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import syll25.tictactoe.logic.state.StateDTO;
import syll25.tictactoe.web.model.Game;
import syll25.tictactoe.web.repository.GameRepository;
import syll25.tictactoe.web.service.GameService;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GameServiceTest {

    @InjectMocks
    private GameService gameService;

    @Mock
    private GameRepository gameRepository;
    private Game mockGame;

    @BeforeEach
    void setUp() {
        mockGame = new Game(50L, "Player1", "Player2", 3);
    }
    @Test
    void startNewGameTest() {
        when(gameRepository.save(any(Game.class))).thenAnswer(invocation -> {
           Game saveGame = invocation.getArgument(0);
           saveGame.setId(50L);
           return saveGame;
        });
        Long gameId = gameService.startNewGame("Player1", "Player2", 3);
        assertEquals(50L, gameId);

        verify(gameRepository).save(argThat(game ->
                game.getPlayer1Name().equals("Player1") &&
                game.getPlayer2Name().equals("Player2") &&
                game.getBoardState("XOX,O,O") != null));
    }

    @Test
    void makeMoveTest() {
        Long gameId = gameService.startNewGame("Player1", "Player2", 3);
        StateDTO gameStateDTO = gameService.makeMove(gameId, 0, 0);
        assertNotNull(gameStateDTO);
        assertFalse(gameStateDTO.isWinnerFound());
    }


}
