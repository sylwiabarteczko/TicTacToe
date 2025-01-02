package syll25.tictactoe.logic.state;

import syll25.tictactoe.logic.Player;

public class StateDTO {

    public PlayerDTO player1;
    public PlayerDTO player2;
    public String[][] board;
    public int size;
    public String currentPlayer;

    public StateDTO(Long gameId, Player player1, Player player2, String[][] board, int size, String currentPlayer, boolean gameOver) {
        this.player1 = new PlayerDTO(player1.getName(), String.valueOf(player1.getSymbol()));
        this.player2 = new PlayerDTO(player2.getName(), String.valueOf(player2.getSymbol()));
        this.size = size;
        this.currentPlayer = currentPlayer;

    }

    public StateDTO(Player player1, Player player2, String[][] board, int size, String currentPlayer, boolean gameOver) {
        this(null, player1, player2, board, size, currentPlayer, gameOver);
    }

    public StateDTO() {

    }

    public String[][] getBoard() {
        return board;
    }

    public int getSize() {
        return size;
    }

    public String getCurrentPlayer() {
        return currentPlayer;
    }

    public StateDTO(Long gameId, Player player1, Player player2, Player[][] cells, int size, String name, boolean gameOver) {

    }
}