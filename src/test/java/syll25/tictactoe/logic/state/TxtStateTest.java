package syll25.tictactoe.logic.state;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TxtStateTest {

   @Test
   public void testSaveAndLoad() {
       StateDTO stateDTO = new StateDTO("Sylwia", "Ania", 'X', 'S',null,3);
       TxtState state = new TxtState();
       String filename = "GameState";

       state.save(filename, stateDTO);

       StateDTO loaded = state.load(filename);

       assertEquals(stateDTO.player1Name, loaded.player1Name);
       assertEquals(stateDTO.player2Name, loaded.player2Name);
       assertEquals(stateDTO.player1Sign, loaded.player1Sign);
       assertEquals(stateDTO.player2Sign, loaded.player2Sign);
       assertEquals(stateDTO.size, loaded.size);

   }

}