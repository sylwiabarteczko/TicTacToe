package syll25.tictactoe;

import java.util.Arrays;

public class BoardRenderer {
    public static void renderBoard(Player[][] cells) {

        System.out.print("  ");
        for (int i = 0; i < cells[0].length; i++) {
            System.out.print((char) ('A' + i) + " ");
        }
        System.out.println();

        for (int i = 0; i < cells.length; i++) {
            System.out.print(i + 1 + " ");
            for (int j = 0; j < cells[i].length; j++) {
                Player cell = cells[i][j];
                if (cell == null) {
                    System.out.print("- ");
                } else {
                    System.out.print(cell.getSymbol() + " ");
                }
            }
            System.out.println();
        }

    }

}
