package syll25.tictactoe.logic.exception;

public class InvalidCoordinatesException extends RuntimeException{
    private static final String INVALID_INPUT_MESSAGE = "Invalid input. Please enter row and column in the format A1, B2 etc. ";
    public InvalidCoordinatesException() {
        super(INVALID_INPUT_MESSAGE);
    }
}
