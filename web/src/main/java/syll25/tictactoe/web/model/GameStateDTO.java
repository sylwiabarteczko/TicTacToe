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
        this.winnerFound = stateDTO.isWinnerFound();
        this.draw = stateDTO.isDraw();
        this.currentPlayer = stateDTO.getCurrentPlayer();
        this.size = stateDTO.size;
        this.gameOver = stateDTO.gameOver;
    }

    public GameStateDTO() {
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
        return stateDTO.getCurrentPlayer();
    }

    public void setCurrentPlayer(String currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public String[][] getBoard() {
        return stateDTO.getBoard();
    }

    public int getSize() {
        return stateDTO.getSize();
    }

    public void setSize(int size) {
        this.size = size;
    }

    public boolean isGameOver() {
        return stateDTO != null && stateDTO.isGameOver();
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

}
