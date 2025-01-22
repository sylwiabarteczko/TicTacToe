package syll25.tictactoe.logic.state;

import syll25.tictactoe.logic.Player;

public class StateDTO {

    public PlayerDTO player1;
    public PlayerDTO player2;
    public String[][] board;
    public int size;
    public String currentPlayer;
    public boolean gameOver;
    public Long gameId;
    private boolean winnerFound;
    private boolean draw;

    public StateDTO(Long gameId, Player player1, Player player2, String[][] board, int size, String currentPlayer, boolean gameOver) {
        this.player1 = new PlayerDTO(player1.getName(), String.valueOf(player1.getSymbol()));
        this.player2 = new PlayerDTO(player2.getName(), String.valueOf(player2.getSymbol()));
        this.size = size;
        this.currentPlayer = currentPlayer;
        this.gameOver = gameOver;
        this.gameId = gameId;
        this.board = board;
        this.winnerFound = false;
        this.draw = false;

    }

    public StateDTO(Player player1, Player player2, String[][] board, int size, String currentPlayer, boolean gameOver) {
        this(null, player1, player2, board, size, currentPlayer, gameOver);
    }

    public StateDTO() {
    }

    public StateDTO(Long gameId, Player player1, Player player2, Player[][] cells, int size, String name, boolean gameOver) {
    }

    public String[][] getBoard() {
        return board;
    }

    public void setBoard(String[][] board) {
        this.board = board;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(String currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public boolean isWinnerFound() {
        return winnerFound;
    }

    public void setWinnerFound(boolean winnerFound) {
        this.winnerFound = winnerFound;
    }
    public boolean isDraw() {
        return draw;
    }

    public void setDraw(boolean draw) {
        this.draw = draw;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public PlayerDTO getPlayer1() {
        return player1;
    }

    public PlayerDTO getPlayer2() {
        return player2;
    }

}