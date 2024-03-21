package syll25.tictactoe.ui;

import syll25.tictactoe.logic.Player;

public class BoardRenderer {
    public static void renderBoard(Player[][] cells) {
        renderColumnLabels(cells[0].length);
        renderBoardContent(cells);
    }

        private static void renderColumnLabels(int amountColumns) {
            System.out.print("  ");
            for (int i = 0; i < amountColumns; i++) {
                System.out.print((char) ('A' + i) + " ");
            }
            System.out.println();
        }

        private static void renderBoardContent (Player[][] cells){
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
