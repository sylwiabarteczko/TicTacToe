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

import java.util.Optional;

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
        mockGame = new Game();
        mockGame.setId(1L);
        mockGame.setPlayer1Name("Player1");
        mockGame.setPlayer1Symbol('X');
        mockGame.setPlayer2Name("Player2");
        mockGame.setPlayer2Symbol('O');
        mockGame.setCurrentPlayer("Player1");
        mockGame.setBoardState("{\"player1\":{\"name\":\"Player1\",\"sign\":\"X\"}," +
                "\"player2\":{\"name\":\"Player2\",\"sign\":\"O\"}," +
                "\"board\":[[null,null,null],[null,null,null],[null,null,null]]," +
                "\"size\":3,\"currentPlayer\":\"Player1\",\"gameOver\":false}");
    }
    @Test
    void startNewGameTest() {
        when(gameRepository.save(any(Game.class))).thenAnswer(invocation -> {
           Game saveGame = invocation.getArgument(0);
           saveGame.setId(50L);
           return saveGame;
        });
        Long gameId = gameService.startNewGame("Player1",3);
        assertEquals(50L, gameId);

        verify(gameRepository).save(any(Game.class));
    }

    @Test
    void makeMoveTest_ForValid_Move() {
        when(gameRepository.findById(1L)).thenReturn(Optional.of(mockGame));
        when(gameRepository.save(any(Game.class))).thenReturn(mockGame);

        StateDTO stateDTO = gameService.makeMove(1L, 0, 0, "Player1");

        assertNotNull(stateDTO);
        assertEquals(3, stateDTO.getSize());
        assertNotNull(stateDTO.getBoard()[0][0]);
    }
    @Test
    void makeMoveTest_NotPlayerTurn() {
        when(gameRepository.findById(1L)).thenReturn(Optional.of(mockGame));

        assertThrows(IllegalStateException.class, () -> {
            gameService.makeMove(1L, 0, 0, "WrongPlayer");
        });
    }
    @Test
    void makeMove_OccupiedCell() {
        when(gameRepository.findById(1L)).thenReturn(Optional.of(mockGame));
        when(gameRepository.save(any(Game.class))).thenReturn(mockGame);

        gameService.makeMove(1L, 0, 0, "Player1");

        Game updatedMock = gameService.getGameById(1L);
        String secondPlayer = updatedMock.getCurrentPlayer();

        assertThrows(Exception.class, () -> {
            gameService.makeMove(1L, 0, 0, secondPlayer);
        });
    }

}
