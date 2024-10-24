package syll25.tictactoe.logic.state;

import syll25.tictactoe.logic.Player;

public class StateDTO {

    private Long gameId;
    public PlayerDTO player1;
    public PlayerDTO player2;
    public String[][] board;
    public int size;
    public String currentPlayer;
    public boolean gameOver;

    public StateDTO(Long gameId, Player player1, Player player2, String[][] board, int size, String currentPlayer, boolean gameOver) {
        this.gameId = gameId;
        this.player1 = new PlayerDTO(player1.getName(), player1.getSymbol());
        this.player2 = new PlayerDTO(player2.getName(), player2.getSymbol());
        this.board = board;
        this.size = size;
        this.currentPlayer = currentPlayer;
        this.gameOver = gameOver;
    }

    public StateDTO(Player player1, Player player2, String[][] board, int size, String currentPlayer, boolean gameOver) {
        this(null, player1, player2, board, size, currentPlayer, gameOver);
    }

    public Long getGameId() {
        return gameId;
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

    public boolean isGameOver() {
        return gameOver;
    }
    public StateDTO() {

    }
}