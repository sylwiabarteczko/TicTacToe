package syll25.tictactoe.ui;

import syll25.tictactoe.logic.GameBoard;
import syll25.tictactoe.logic.Player;

import java.util.Optional;

public class BoardRenderer {
    public static void renderBoard(GameBoard board) {
        renderColumnLabels(board.getSize());
        renderBoardContent(board);
    }

    private static void renderColumnLabels(int amountColumns) {
        System.out.print("  ");
        for (int i = 0; i < amountColumns; i++) {
            System.out.print((char) ('A' + i) + " ");
        }
        System.out.println();
    }

    private static void renderBoardContent(GameBoard board) {
        for (int i = 0; i < board.getSize(); i++) {
            System.out.print(i + 1 + " ");
            for (int j = 0; j < board.getSize(); j++) {
                Optional<Player> cell = board.getFieldState(i, j);
                if (cell.isEmpty()) {
                    System.out.print("- ");
                } else {
                    System.out.print(cell.get().getSymbol() + " ");
                }
            }
            System.out.println();
        }
    }

}
