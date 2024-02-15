package syll25.tictactoe;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

//        Player player1 = new Player();
//        Player player2 = new Player();

        TicTacToe symbolChoice = new TicTacToe('X', 'Y', 'Z', 'O', 'S');

        List<Character> drawSymbols = symbolChoice.drawSymbols();

        char player1symbol = drawSymbols.get(0);
        System.out.println("Player 1 that is your symbol: " + player1symbol);

        char player2symbol = drawSymbols.get(1);
        System.out.println("Player 2 that is your symbol: " + player2symbol);

//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Hello Player 1, what is your name? ");
//        String play1 = scanner.nextLine();
//        System.out.println("Hello Player 2, what is your name? ");
//        String play2 = scanner.nextLine();


//        char [][] board = new char[2][2];
//
//        for (int i = 0; i < 2; i++) {
//            for (int j = 0; j < 2; j++) {
//            board [i] [j] = '-' ;  Jeszcze nie wiem czy potrzebuje tego tutaj

    }
}
