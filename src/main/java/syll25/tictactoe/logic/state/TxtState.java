package syll25.tictactoe.logic.state;

import syll25.tictactoe.logic.GameBoard;
import syll25.tictactoe.logic.Player;

import java.io.*;

public class TxtState implements State {
    @Override
    public void save(GameBoard board, Player player1, Player player2) {

        PrintWriter out = new PrintWriter(new FileWriter("gameState.txt"));

    }

    @Override
    public StateDTO load(String filename) {  //DTO Data Transfer Object

        StateDTO stateDTO = new StateDTO();
        String fileContent = ;


//        stateDTO.player1Name = filename.split(";")
//        stateDTO.player2Name =

        return stateDTO;
    }

}
