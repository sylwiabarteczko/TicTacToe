package syll25.tictactoe.logic.exception;

public class NoMoreSymbolsException extends RuntimeException {
    public NoMoreSymbolsException() {
        super("No more symbols available. ");
    }
}
