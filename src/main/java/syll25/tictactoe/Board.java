package syll25.tictactoe;

import java.util.Arrays;

public class Board {

    private final char[][] cells = new char[3][3];
    private final CharacterPoolRandomizer symbolChoice;

    public Board(CharacterPoolRandomizer symbolChoice) {
        this.symbolChoice = symbolChoice;
    }

    public void initializeBoard() {
        for (char[] row : cells) {
            java.util.Arrays.fill(row, '-');  //nowe cudowne odkrycie z codecademy.com ale nie wiem czy dobrze użyte
        }
    }

    //        for (int i = 0; i < cells.length; i++) {
//            for (int j = 0; j < cells[i].length; j++) {
//                {
//                    cells[i][j] = '-';
//                }
//            }
//        }
//    }
    public void printBoard() {
        for (char[] row : cells) {
            for (char cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
    }

    //        for (int i = 0; i < cells.length; i++) {
//            for(int j = 0; j < cells[i].length; j++) {
//                System.out.print(cells[i][j] + " ");
//            }
//            System.out.println();
//        }
//    }
    public boolean isFull() {
        for (char[] row : cells) {
            for (char cell : row) {
                if (cell == '-') {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean placeSymbol(char symbol, int row, int col) {
        if (row < 0 || row >= cells.length || col < 0 || col >= cells[0].length || cells[row][col] != '-') {
            return false;
        }
        cells[row][col] = symbol;
        return true;
    }

    public boolean checkWhoWin(char symbol) {
        for (int i = 0; i < 3; i++) {
            if ((cells[i][0] == symbol && cells[i][1] == symbol && cells[i][2] == symbol) ||
                    (cells[0][i] == symbol && cells[1][i] == symbol && cells[2][i] == symbol)) {
                return true;
            }
        }
        return false;
    }
}