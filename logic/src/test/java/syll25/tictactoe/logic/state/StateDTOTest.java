package syll25.tictactoe.logic.state;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StateDTOTest {

    @Test
    public void testWinnerAndDraw() {

        StateDTO stateDTO = new StateDTO();

        stateDTO.setGameOver(true);
        stateDTO.setCurrentPlayer("Player X");

        assertTrue(stateDTO.isWinnerFound());
        assertFalse(stateDTO.isDraw());

        stateDTO.setCurrentPlayer(null);
        assertFalse(stateDTO.isWinnerFound());
        assertTrue(stateDTO.isDraw());
    }

}
