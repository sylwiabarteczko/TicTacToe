package syll25.tictactoe.web.model;

import syll25.tictactoe.logic.state.StateDTO;

public class GameStateDTO {

    private StateDTO stateDTO;
    private Long gameId;
    private boolean winnerFound;
    private boolean draw;
    private String currentPlayer;

    public GameStateDTO(StateDTO stateDTO, Long gameId) {
        this.stateDTO = stateDTO;
        this.gameId = gameId;
        this.winnerFound = stateDTO.isWinnerFound();
        this.draw = stateDTO.isDraw();
        this.currentPlayer = stateDTO.getCurrentPlayer();
    }

    public StateDTO getStateDTO() {
        return stateDTO;
    }

    public Long getGameId() {
        return gameId;
    }

    public boolean isWinnerFound() {
        return winnerFound;
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

    public boolean isGameOver() {
        return stateDTO.isGameOver();
    }

}
