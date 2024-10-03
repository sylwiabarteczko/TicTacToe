package syll25.tictactoe.logic.state;

import syll25.tictactoe.logic.Player;

public class StateDTO {

    public PlayerDTO player1;
    public PlayerDTO player2;
    public String[][] board;
    public int size;
    public String currentPlayer;

    public StateDTO(Player player1, Player player2, Player[][] cells, int size) {
    }

    public StateDTO() {

    }
}