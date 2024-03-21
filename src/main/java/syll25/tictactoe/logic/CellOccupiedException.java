package syll25.tictactoe.logic;

public class CellOccupiedException extends RuntimeException {
    private static final String DEFAULT_MESSAGE2 = "Invalid move: Cell already occupied. ";

    public CellOccupiedException() {
        super(DEFAULT_MESSAGE2);
    }
}
