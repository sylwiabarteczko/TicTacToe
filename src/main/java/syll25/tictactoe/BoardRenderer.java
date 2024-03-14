package syll25.tictactoe;

import java.util.Arrays;

public class BoardRenderer {
    public static void renderBoard(Player[][] cells) {
        for (Player[] row : cells) {
            for (Player cell : row) {
                if (cell == null) {
                    System.out.print("- ");
                } else {
                    System.out.print(cell.getSymbol() + " ");
                }
            }
            System.out.println();
        }
    }

    public static void initializeBoard(Player[][] cells) {
        for (Player[] row : cells) {
            Arrays.fill(row, null);
        }
    }
}
