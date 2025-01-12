package syll25.tictactoe.web;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
public class GameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getNewGameTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/game/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("newGame"));
    }
    @Test
    public void startNewGameTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/game/start")
                .param("player1Name", "Sylwia")
                .param("player2Name", "Sabina")
                .param("boardSize", "3"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("/game/{gameId}"));
    }
    @Test
    public void gameIdTest() throws Exception {

        Long gameId = 1L;
        mockMvc.perform(MockMvcRequestBuilders.get("/game/{gameId}", gameId))
                .andExpect(status().isOk())
                .andExpect(view().name("game"))
                .andExpect(model().attribute("stateDTO", Matchers.hasProperty("gameId", Matchers.is(gameId))))
                .andExpect(model().attribute("stateDTO", Matchers.hasProperty("gameOver", Matchers.is(false))))
                .andExpect(model().attribute("stateDTO", Matchers.hasProperty("size", Matchers.greaterThanOrEqualTo(3))))
                .andExpect(model().attributeExists("stateDTO"));
    }

    @Test
    public void makeMoveTest() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/game/move")
                        .param("gameId", "1")
                        .param("row", "0")
                        .param("col", "0"))
                .andExpect(status().isOk())
                .andExpect(view().name("game"))
                .andExpect(model().attributeExists("stateDTO"))
                .andExpect(model().attribute("stateDTO", Matchers.hasProperty("gameOver", Matchers.is(false))))
                .andExpect(model().attribute("stateDTO", Matchers.hasProperty("size", Matchers.greaterThanOrEqualTo(3))));
    }

    @Test
    public void loadGameTest() throws Exception {

        Long gameId = 1L;
        mockMvc.perform(MockMvcRequestBuilders.post("/game/load")
                .param("gameId", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("game"))
                .andExpect(model().attributeExists("board"))
                .andExpect(model().attribute("board", Matchers.hasProperty("gameId", Matchers.is(gameId))));

    }

}





