package syll25.tictactoe;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        CharacterPoolRandomizer symbolChoice = new CharacterPoolRandomizer('X', 'Y', 'Z', 'O', 'S');

        char player1symbol = symbolChoice.drawSymbol();
        System.out.println("Player 1 that is your symbol: " + player1symbol);

        char player2symbol = symbolChoice.drawSymbol();
        System.out.println("Player 2 that is your symbol: " + player2symbol);


//        char [][] board = new char[2][2];
//
//        for (int i = 0; i < 2; i++) {
//            for (int j = 0; j < 2; j++) {
//            board [i] [j] = '-' ;  Jeszcze nie wiem czy potrzebuje tego tutaj

    }
}
