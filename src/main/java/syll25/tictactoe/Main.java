package syll25.tictactoe;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        CharacterPoolRandomizer symbolChoice = new CharacterPoolRandomizer('X', 'Y', 'Z', 'O', 'S');

        Board board = new Board(symbolChoice);
        board.initializeBoard();
        System.out.println("That is your game board: ");
        board.printBoard();

        if (symbolChoice.availableSymbols.size() >= 2) {
            char player1symbol = symbolChoice.drawSymbol();
            char player2symbol = symbolChoice.drawSymbol();

            System.out.println("Player 1 that is your symbol: " + player1symbol);
            System.out.println("Player 2 that is your symbol: " + player2symbol);

            Scanner scanner = new Scanner(System.in);
            int row, col;
            boolean validMove;
            boolean gameOver = false;

            while (!gameOver) {
                System.out.println("Player 1, enter row and column (Your choice can be 0, 1 or 2). ");
                do {
                    System.out.print("Row: ");
                    row = scanner.nextInt();
                    System.out.print("Column: ");
                    col = scanner.nextInt();
                    validMove = board.placeSymbol(player1symbol, col, row);
                    if (!validMove) {
                        System.out.println("Invalid move, please try again. ");
                    }
                } while (!validMove);

                board.printBoard();

                if (board.checkWhoWin(player1symbol)) {
                    System.out.println("Player 1 win!");
                    gameOver = true;
                    break;
                } else if (board.isFull()) {
                    System.out.println("We have a draw!");
                    gameOver = true;
                    break;
                }

                System.out.println("Player 2, enter row and column (Your choice can be 0, 1 or 2). ");
                do {
                    System.out.print("Row: ");
                    row = scanner.nextInt();
                    System.out.print("Column: ");
                    col = scanner.nextInt();
                    validMove = board.placeSymbol(player2symbol, col, row);
                    if (!validMove) {
                        System.out.println("Invalid move, please try again. ");
                    }
                } while (!validMove);

                board.printBoard();
                if (board.checkWhoWin(player2symbol)) {
                    System.out.println("Player 2 win!");
                    gameOver = true;
                    break;
                } else if (board.isFull()) {
                    System.out.println("We have a draw!");
                    gameOver = true;
                    break;
                }
            }
        } else {
            System.out.println("Not available symbol");

        }

    }
}
