package syll25.tictactoe.web;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import syll25.tictactoe.logic.state.StateDTO;
import syll25.tictactoe.web.controllers.GameController;
import syll25.tictactoe.web.model.GameStateDTO;
import syll25.tictactoe.web.service.GameService;
import syll25.tictactoe.web.service.GameViewService;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GameController.class)
public class GameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GameService gameService;

    @MockBean
    private GameViewService gameViewService;

    @Test
    public void getNewGameTest() throws Exception {
        mockMvc.perform(get("/game/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("newGame"));
    }
    @Test
    public void startNewGameTest() throws Exception {
        mockMvc.perform(post("/game/start")
                .param("player1Name", "Sylwia")
                .param("player2Name", "Sabina")
                .param("boardSize", "3"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("/game/{gameId}"));
    }
    @Test
    public void gameIdTest() throws Exception {

        Long gameId = 1L;
        GameStateDTO mockGameState = new GameStateDTO();
        mockGameState.setGameId(gameId);
        mockGameState.setWinnerFound(false);
        mockGameState.setSize(3);
        mockGameState.setGameOver(false);
        mockGameState.setCurrentPlayer("X");

        when(gameService.loadGame(any(Long.class))).thenReturn(mockGameState);

        mockMvc.perform(get("/game/{gameId}", gameId))
                .andExpect(status().isOk())
                .andExpect(view().name("game"))
                .andExpect(model().attribute("gameStateDTO", Matchers.hasProperty("gameId", Matchers.is(gameId))))
                .andExpect(model().attribute("gameStateDTO", Matchers.hasProperty("winnerFound", Matchers.is(false))))
                .andExpect(model().attribute("gameStateDTO", Matchers.hasProperty("size", Matchers.greaterThanOrEqualTo(3))))
                .andExpect(model().attribute("gameStateDTO", Matchers.hasProperty("currentPlayer", Matchers.is("X"))));
    }

    @Test
    void makeMoveTest() throws Exception {
        GameStateDTO mockGameState = new GameStateDTO();
        mockGameState.setWinnerFound(false);
        when(gameService.makeMove(eq(1L), anyInt(), anyInt())).thenReturn(mockGameState.getStateDTO());
        when(gameViewService.redirectToResult(any(GameStateDTO.class).getStateDTO())).thenReturn("game");

        mockMvc.perform(post("/game/move")
                        .param("gameId", "1")
                        .param("row", "0")
                        .param("col", "0"))
                .andExpect(status().isOk())
                .andExpect(view().name("game"));
    }

    @Test
    public void loadGameTest() throws Exception {

        Long gameId = 1L;
        mockMvc.perform(post("/game/load")
                .param("gameId", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("game"))
                .andExpect(model().attributeExists("board"))
                .andExpect(model().attribute("board", Matchers.hasProperty("gameId", Matchers.is(gameId))));

    }
    @Test
    void viewGameTest() throws Exception {
        GameStateDTO mockGameState = new GameStateDTO();
        mockGameState.setGameId(1L);
        when(gameService.loadGame(1L)).thenReturn(mockGameState);

        mockMvc.perform(get("/game/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("game"))
                .andExpect(model().attributeExists("gameStateDTO"));
    }
    @Test
    void gameResultTest() throws Exception {
        GameStateDTO mockGameState = new GameStateDTO();
        mockGameState.setWinnerFound(true);
        when(gameService.loadGame(1L)).thenReturn(mockGameState);

        mockMvc.perform(get("/game/gameResult"))
                .andExpect(status().isOk())
                .andExpect(view().name("gameResult"))
                .andExpect(model().attributeExists("gameStateDTO"));
    }

}





