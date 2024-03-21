package syll25.tictactoe;

import syll25.tictactoe.logic.*;
import syll25.tictactoe.ui.BoardRenderer;

import java.util.Optional;
import java.util.Scanner;

public class Main {

    private static int boardSize = 3; //podpowiada final, ale jesli chcemy miec tablice 4x4 w przyszlosci, powinna byc final?

    public static void main(String[] args) {

        CharacterPoolRandomizer symbolChoice = new CharacterPoolRandomizer('X', 'Y', 'Z', 'O', 'S');

        Board board = new Board(boardSize);
        System.out.println("That is your game board: ");

        BoardRenderer.renderBoard(board.getCells()); //mieszaja sie, ale w klasie Main chyba powinny prawda?

        try {
            Player player1 = new Player(symbolChoice.drawSymbol());
            Player player2 = new Player(symbolChoice.drawSymbol());

            System.out.println("Player 1 that is your symbol: " + player1.getSymbol());
            System.out.println("Player 2 that is your symbol: " + player2.getSymbol());

            Scanner scanner = new Scanner(System.in);
            boolean gameOver = false;

            while (!gameOver) {
                gameOver = playerMove(board, scanner, player1);
                if (gameOver) break;
                gameOver = playerMove(board, scanner, player2);
            }
        } catch (NoMoreSymbolsException ex) { //"ex" jako exception powinnam dodawac za kazdym razem?
            System.out.println("Not available symbol");
        }
    }

    public static boolean playerMove(Board board, Scanner scanner, Player player) {
        int row, col;  // zmienilam tu tez kolejnosc ale nie wiem czy ma to znaczenie

        System.out.println("Player, enter row and column (e.g. A1, B2): ");
        do {
            String input = scanner.nextLine().toUpperCase();
            if (input.length() != 2) {
                System.out.println("Invalid input. Please enter row and column in the format A1, B2 etc.");
                continue;
            }
            char rowChar = input.charAt(0);
            char colChar = input.charAt(1);

            if (!(rowChar >= 'A' && rowChar <= 'C') || !(colChar >= '1' && colChar <= '3')) {
                System.out.println("Invalid input. Please enter row (A-C) and column (1-3)");
                continue;
            }
            row = convertColumnInput(colChar); // kolejnosc zmieniona tutaj, ale nie wyglada to dobrze?
            col = convertRowInput(rowChar);

            try {
                board.placeSymbol(player, row, col);
            } catch (InvalidMoveException ex) {
                System.out.println(ex.getMessage());
                continue;
            } catch (OutOfRangeException ex) {
                System.out.println("Invalid move: Out of range. "); //ten wyjatek zajmuje sie dokladnie tym samym co linijka 49, powinnam to zamienic?
                continue;
            } catch (CellOccupiedException ex) {
                System.out.println("Invalid move: Cell already occupied. ");
                continue;
            }
            break;
        } while (true);

        BoardRenderer.renderBoard(board.getCells());

        Optional<Player> winner = board.isWinner(player.getSymbol());
        if (winner.isPresent()) {
            System.out.println("Player " + player.getSymbol() + " wins!");
            return true;
        } else if (board.isFull()) {
            System.out.println("We have a draw!");
            return true;
        }

        return false;
    }

    private static int convertRowInput(char input) {
        return input - 'A';
    }

    private static int convertColumnInput(char input) {
        return input - '1';
    }


}


