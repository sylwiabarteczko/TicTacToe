package syll25.tictactoe.ui;

import syll25.tictactoe.logic.GameBoard;
import syll25.tictactoe.logic.Player;

public class BoardRenderer {
    public static void renderBoard(GameBoard board) {
        Player[][] cells = board.getCells();
        renderColumnLabels(board.getSize());
        renderBoardContent(cells, board.getSize());
    }

    private static void renderColumnLabels(int amountColumns) {
        System.out.print("  ");
        for (int i = 0; i < amountColumns; i++) {
            System.out.print((char) ('A' + i) + " ");
        }
        System.out.println();
    }

    private static void renderBoardContent(Player[][] cells, int size) {
        for (int i = 0; i < size; i++) {
            System.out.print(i + 1 + " ");
            for (int j = 0; j < size; j++) {
                if (cells[i][j] == null) {
                    System.out.print("- ");
                } else {
                    System.out.print(cells[i][j].getSymbol() + " ");
                }
            }
            System.out.println();
        }
    }

}
