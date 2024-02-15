package syll25.tictactoe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Wymagania:
 * - konstruktor przyjmujący zestaw znaków, z których będziemy losować
 * - metoda losująca znak z puli, bez powtórzeń
 * - metoda losująca w przypadku braku dostępnych w puli znaków rzuca wyjątek
 * - testy jednostkowe
 */
public class CharacterPoolRandomizer {
    private ArrayList<Character> availableSymbols;
    private List<Character> symbols; // Bez tego podkresla na czerwono kazde slowo "symbols" z komentarzem "cannot resolve symbol"

    public CharacterPoolRandomizer(char... characters) {
        symbols = new ArrayList<>();
        for (char c : characters) {
            symbols.add(c);
        }

        if (symbols.size() != 5) {
            throw new IllegalArgumentException("You need to choose one of this characters: X, Y, Z, O, S"); //nazwe wyjatku podpowiada mi program, gdy wymysle wlasna stwierdza "cannot resolve symbol"
        }
        availableSymbols = new ArrayList<>(symbols);
        Collections.shuffle(availableSymbols);
    }

    public void addSymbols(char... characters) {
        for (char symbol : symbols) {
            availableSymbols.add(symbol);
        }
    }

    public char drawSymbol() {
        if (availableSymbols.isEmpty()) {
            throw new IllegalArgumentException("No available symbols");
        }
        int randomIndex = new Random().nextInt(availableSymbols.size());
        char symbol = availableSymbols.remove(0);
        return symbol;
    }

    public List<Character> drawSymbols() {
        List<Character> drawnSymbols = new ArrayList<>();
        for (char symbol : symbols) {
            drawnSymbols.add(symbol);
        }
        return drawnSymbols;
    }
}
