package syll25.tictactoe.logic;

import java.util.Scanner;

public class Coordinates {
    private int col;
    private int row;

    public Coordinates(Scanner scanner) {

        System.out.println("Enter row and column (e.g. A1, B2): ");
        String input = scanner.nextLine().toUpperCase();

        if (input.length() != 2 || !isValidCoordinate(input)) {
            System.out.println("Invalid input. Please enter row and column in the format A1, B2 etc. ");

            this.col = -1;
            this.row = -1;

        } else {

            char firstChar = input.charAt(0);
            char secondChar = input.charAt(1);

            if (Character.isLetter(firstChar)) {
                this.col = convertColumnInput(firstChar);
                this.row = convertRowInput(secondChar);

            } else {

                this.col = convertColumnInput(secondChar);
                this.row = convertRowInput(firstChar);
            }
        }
    }

    private boolean isValidCoordinate(String input) {
        char firstChar = input.charAt(0);
        char secondChar = input.charAt(1);
        return (Character.isLetter(firstChar) && Character.isDigit(secondChar)) ||
                (Character.isDigit(firstChar) && Character.isLetter(secondChar));
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    public int convertColumnInput(char input) {
        return input - 'A';
    }

    public int convertRowInput(char input) {
        return Character.getNumericValue(input) - 1;
    }
}
