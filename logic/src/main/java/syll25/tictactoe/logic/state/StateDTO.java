package syll25.tictactoe.logic.state;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import syll25.tictactoe.logic.Player;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StateDTO {

    public PlayerDTO player1;
    public PlayerDTO player2;
    public String[][] board;
    public int size;
    public String currentPlayer;
    public boolean gameOver;


    public StateDTO(Player player1, Player player2, String[][] board, int size, String currentPlayer, boolean gameOver) {
        this.player1 = new PlayerDTO(player1.getName(), String.valueOf(player1.getSymbol()));
        this.player2 = new PlayerDTO(player2.getName(), String.valueOf(player2.getSymbol()));
        this.board = board;
        this.size = size;
        this.currentPlayer = currentPlayer;
        this.gameOver = gameOver;
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


    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public PlayerDTO getPlayer1() {
        return player1;
    }


    public PlayerDTO getPlayer2() {
        return player2;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public boolean isWinnerFound() {
        return gameOver && currentPlayer != null;
    }

    public boolean isDraw() {
        return gameOver && currentPlayer == null;
    }
    public void setPlayer1(PlayerDTO player1) {
        this.player1 = player1;
    }

    public void setPlayer2(PlayerDTO player2) {
        this.player2 = player2;
    }
}
