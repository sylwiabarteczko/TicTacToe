package syll25.tictactoe.logic.exception;

public class OutOfRangeException extends RuntimeException {
    private static final String DEFAULT_MESSAGE1 = "Invalid move: Out of range. ";

    public OutOfRangeException() {
        super(DEFAULT_MESSAGE1);
    }

}
