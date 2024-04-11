package syll25.tictactoe.web;

import org.junit.jupiter.api.Test;
import syll25.tictactoe.logic.state.StateDTO;
import syll25.tictactoe.web.service.GameViewService;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameViewServiceTest {
    private final GameViewService gameViewService = new GameViewService();

    @Test
    void testRedirectToResult() {
        StateDTO stateDTO = new StateDTO();
        stateDTO.setGameOver(true);
        stateDTO.setCurrentPlayer(null);

        String result = gameViewService.redirectToResult(stateDTO,1L);
        assertEquals("gameResult", result);
    }
    @Test
    void testColumnIndexConversion() {
        assertEquals('A', gameViewService.getColumnIndex(0));
        assertEquals('B', gameViewService.getColumnIndex(1));
        assertEquals('C', gameViewService.getColumnIndex(2));
    }

}
