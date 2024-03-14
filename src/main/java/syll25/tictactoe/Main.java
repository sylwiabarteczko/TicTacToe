package syll25.tictactoe;

import java.util.Optional;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        CharacterPoolRandomizer symbolChoice = new CharacterPoolRandomizer('X', 'Y', 'Z', 'O', 'S');

        Board board = new Board(symbolChoice);
        System.out.println("That is your game board: ");
        board.printBoard();

        if (symbolChoice.availableSymbols.size() >= 2) {
            Player player1 = new Player(symbolChoice.drawSymbol());
            Player player2 = new Player(symbolChoice.drawSymbol());

            System.out.println("Player 1 that is your symbol: " + player1.getSymbol());
            System.out.println("Player 2 that is your symbol: " + player2.getSymbol());

            Scanner scanner = new Scanner(System.in);
            boolean gameOver = false;

            while (!gameOver) {
                gameOver = playerMove(board, scanner, player1.getSymbol());
                if (gameOver) break;
                gameOver = playerMove(board, scanner, player2.getSymbol());
            }
        } else {
            System.out.println("Not available symbol");

        }
    }

    public static boolean playerMove(Board board, Scanner scanner, char symbol) {
        int row, col;
        boolean validMove;

        System.out.println("Player, enter row and column (Your choice can be 0, 1 or 2). ");
        do {
            System.out.print("Row: ");
            row = scanner.nextInt();
            System.out.print("Column: ");
            col = scanner.nextInt();
            try {
                board.placeSymbol(symbol, row, col);
            } catch (InvalidMoveException e) {
                System.out.println(e.getMessage());
                continue;
            }
            break;
        } while (true);

        board.printBoard();

        Optional<Player> winner = board.getWinner(symbol);
        if (winner.isPresent()) {
            System.out.println("Player " + symbol + " wins!");
            return true;
        } else if (board.isFull()) {
            System.out.println("We have a draw!");
            return true;
        }

        return false;
    }
}


