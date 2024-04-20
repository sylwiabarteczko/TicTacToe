package syll25.tictactoe.logic.state;

import org.junit.jupiter.api.Test;
import syll25.tictactoe.logic.Board;
import syll25.tictactoe.logic.Player;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class TxtStateTest {

    @Test
    public void testSaveAndLoad()  throws IOException {

        Player player1 = new Player("John", 'X');
        Player player2 = new Player("Adam", 'O');
        int size = 3;
        Board board = new Board(size);

        StateDTO stateDTD = new StateDTO("John", "X", "Adam", "O", null, size);

        TxtState state = new TxtState("gameState.txt");

        state.save(board,player1, player2);

        StateDTO loaded = state.load();

        assertEquals(stateDTD.player1Name, loaded.player1Name);
        assertEquals(stateDTD.player2Name, loaded.player2Name);
        assertEquals(stateDTD.player1Sign, loaded.player1Sign);
        assertEquals(stateDTD.player2Sign, loaded.player2Sign);
        assertEquals(stateDTD.size, loaded.size);

    }

}
