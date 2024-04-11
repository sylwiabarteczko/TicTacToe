package syll25.tictactoe.logic;

public class Player {
    private final char symbol;
    private final String name;

    public Player(String name, char symbol) {
        this.name = name;
        this.symbol = symbol;
    }

    public char getSymbol() {
        return symbol;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return String.valueOf(symbol);
    }
}


