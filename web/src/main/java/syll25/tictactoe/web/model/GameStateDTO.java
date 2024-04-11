package syll25.tictactoe.web.model;

import syll25.tictactoe.logic.state.StateDTO;

public class GameStateDTO {

    private StateDTO stateDTO;
    private Long gameId;
    private boolean winnerFound;
    private boolean draw;
    private String currentPlayer;
    public int size;

    public boolean gameOver;

    public GameStateDTO(StateDTO stateDTO, Long gameId) {
        this.stateDTO = stateDTO;
        this.gameId = gameId;
        this.winnerFound = false;
        this.draw = false;
        this.currentPlayer = stateDTO.getCurrentPlayer();
        this.size = stateDTO.size;
        this.gameOver = stateDTO.gameOver;
    }

    public GameStateDTO() {
        this.stateDTO = null;
        this.gameId = null;
        this.winnerFound = false;
        this.draw = false;
        this.currentPlayer = null;
        this.size = 0;
        this.gameOver = false;
    }

    public GameStateDTO(StateDTO stateDTO, String currentPlayer, boolean gameOver, boolean winnerFound) {
        this.stateDTO = stateDTO;
        this.gameId = null;
        this.winnerFound = winnerFound;
        this.draw = false;
        this.currentPlayer = currentPlayer;
        this.size = stateDTO.getSize();
        this.gameOver = gameOver;
    }


    public StateDTO getStateDTO() {
        return stateDTO;
    }

    public void setStateDTO(StateDTO stateDTO) {
        this.stateDTO = stateDTO;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
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

    public String getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(String currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

}
