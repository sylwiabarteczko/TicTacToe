package syll25.tictactoe.logic.state;

import com.fasterxml.jackson.databind.ObjectMapper;
import syll25.tictactoe.logic.GameBoard;
import syll25.tictactoe.logic.Player;

import java.io.File;
import java.io.IOException;

public class JsonState implements State{

    private final String filename;
    private static final ObjectMapper mapper = new ObjectMapper();

    public JsonState(String filename) {
        this.filename = filename;
    }

    @Override
    public void save(GameBoard board, Player player1, Player player2) {
        String currentPlayer = player1.getName();
        boolean gameOver = false;

        StateDTO stateDTO = new StateDTO();

        try {
            mapper.writeValue(new File(filename), stateDTO);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save game state", e);
        }
    }


    @Override
    public StateDTO load() {
        try {
            return mapper.readValue(new File(filename), StateDTO.class);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load game state", e);
        }
    }

}