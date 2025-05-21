package syll25.tictactoe.web.model;
import syll25.tictactoe.logic.state.StateDTO;

public class MoveResponseDTO {

    private StateDTO stateDTO;
    private boolean yourTurn;
    private String currentPlayer;
    private boolean gameOver;

    public MoveResponseDTO(StateDTO stateDTO, boolean yourTurn) {
        this.stateDTO = stateDTO;
        this.yourTurn = yourTurn;
        this.currentPlayer = stateDTO.getCurrentPlayer();
        this.gameOver = stateDTO.isGameOver();
    }
    public StateDTO getStateDTO() {
        return stateDTO;
    }
    public boolean isYourTurn() {
        return yourTurn;
    }
    public String getCurrentPlayer() {
        return currentPlayer;
    }
    public boolean isGameOver() {
        return gameOver;
    }
    public void setStateDTO(StateDTO stateDTO) {
        this.stateDTO = stateDTO;
    }
    public void setYourTurn(boolean yourTurn) {
        this.yourTurn = yourTurn;
    }
    public void setCurrentPlayer(String currentPlayer) {
        this.currentPlayer = currentPlayer;
    }
    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }
}