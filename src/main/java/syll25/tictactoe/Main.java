package syll25.tictactoe;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        CharacterPoolRandomizer symbolChoice = new CharacterPoolRandomizer('X', 'Y', 'Z', 'O', 'S');

        symbolChoice.addSymbol('X');
        symbolChoice.addSymbol('Y');
        symbolChoice.addSymbol('O');
        symbolChoice.addSymbol('Z');
        symbolChoice.addSymbol('S');

        Board board = new Board(symbolChoice);
        board.initializeBoard();

        if(symbolChoice.availableSymbols.size() >=2) {
            char player1symbol = symbolChoice.drawSymbol();
            char player2symbol = symbolChoice.drawSymbol();

            System.out.println("Player 1 that is your symbol: " + player1symbol);
            System.out.println("Player 2 that is your symbol: " + player2symbol);
        } else {
            System.out.println("Not available symbol");

        }

    }
}
