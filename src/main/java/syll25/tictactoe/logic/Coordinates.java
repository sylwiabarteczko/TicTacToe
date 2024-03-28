package syll25.tictactoe.logic;

import syll25.tictactoe.logic.exception.InvalidCoordinatesException;

public class Coordinates {
    private int col;
    private int row;

    public Coordinates(String input) {

        if (input.length() != 2 || !isValidCoordinate(input)) {
            throw new InvalidCoordinatesException();

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