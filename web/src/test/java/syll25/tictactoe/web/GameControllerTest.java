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

        // TODO czy oprócz właściwego widoku, chcemy sprawdzić także co się w nim zawiera? np. model?
    }
    @Test
    public void startNewGameTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/game/start")
                .param("player1Name", "Sylwia")
                .param("player2Name", "Sabina")
                .param("boardSize", "3"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("/game/{gameId}")); // bardzo dobrze, że sprawdzamy przekierowanie
    }
    @Test
    public void gameIdTest() throws Exception {

        Long gameId = 1L;
        mockMvc.perform(MockMvcRequestBuilders.get("/game/{gameId}"))
                .andExpect(status().isOk())
                .andExpect(view().name("game"))
                .andExpect(model().attributeExists("stateDTO"));

        // TODO czy oprócz właściwego widoku i modelu, chcemy sprawdzić także co się w nim zawiera? w makeMoveTest sprawdzamy wartości...
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
                .andExpect(model().attribute("stateDTO", Matchers.hasProperty("gameOver", Matchers.is(false))));
    }

    @Test
    public void loadGameTest() throws Exception {

        Long gameId = 1L;
        mockMvc.perform(MockMvcRequestBuilders.post("/game/load")
                .param("gameId", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("game"));
        // TODO czy oprócz właściwego widoku, chcemy sprawdzić także co się w nim zawiera? j/w
    }

}





