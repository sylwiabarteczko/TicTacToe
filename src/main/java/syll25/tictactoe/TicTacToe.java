package syll25.tictactoe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TicTacToe {
    private ArrayList<Character> availableSymbols;
    private List<Character> symbols;

    public TicTacToe(char x, char y, char z, char o, char s) {
        symbols = new ArrayList<>();
        symbols.add(x);
        symbols.add(y);
        symbols.add(z);
        symbols.add(o);
        symbols.add(s);

        if (symbols.size() != 5) {
            throw new IllegalArgumentException("You need to choose one of this characters: X, Y, Z, O, S");
        }
        availableSymbols = new ArrayList<>(symbols);
    }

    public void addSymbols(char x, char y, char z, char o, char s) {
        for (char symbol : symbols) {
            availableSymbols.add(symbol);
        }
    }

    public char drawSymbol() {
        if (availableSymbols.isEmpty()) {
            throw new IllegalStateException("No available symbols");
        }
        Collections.shuffle(availableSymbols);
        char symbol = availableSymbols.remove(0);
        return symbol;
    }

    public List<Character> drawSymbols() {
        List<Character> drawnSymbols = new ArrayList<>();
        for (char symbol : symbols) {
            drawnSymbols.add(symbol);
        }
        Collections.shuffle(drawnSymbols);
        return drawnSymbols;
    }

}
