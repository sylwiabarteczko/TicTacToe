package syll25.tictactoe;

import java.util.Arrays;
import java.util.Optional;

public class Board {

    private final Player[][] cells = new Player[3][3];

    public static void initializeBoard (Player[][] cells) {
        for (Player[] row : cells) {
            Arrays.fill(row, null);
        }
    }

    public void printBoard() {
        BoardRenderer.renderBoard(cells);
    }

    public boolean isFull() {
        for (Player[] row : cells) {
            for (Player col : row) {
                if (col == null) {
                    return false;
                }
            }
        }
        return true;
    }

    public void placeSymbol(Player player, int row, int col) {
        if (row < 0 || row >= cells.length || col < 0 || col >= cells[0].length) {
            throw new InvalidMoveException("Invalid move: Out of range. ");
        }
        if (cells[row][col] != null) {
            throw new InvalidMoveException("Invalid move: Cell already occupied. ");
        }

        cells[row][col] = player;
    }

    public Optional<Player> getWinner(char symbol) {
        int size = cells.length;
        for (int i = 0; i < size; i++) {
            if (cells[i][0] != null && cells[i][0].getSymbol() == symbol && cells[i][1] != null &&
                    cells[i][1].getSymbol() == symbol && cells[i][2] != null && cells[i][2].getSymbol() == symbol) {
                return Optional.of(cells[i][0]);
            }
            if (cells[0][i] != null && cells[0][i].getSymbol() == symbol && cells[1][i] != null &&
                    cells[1][i].getSymbol() == symbol && cells[2][i] != null && cells[2][i].getSymbol() == symbol) {
                return Optional.of(cells[0][i]);
            }
        }
        boolean diagonal1Win = true;
        boolean diagonal2Win = true;

        for (int i = 0; i < size; i++) {
            if (cells[i][i] == null || cells[i][i].getSymbol() != symbol) {
                diagonal1Win = false;
            }
            if (cells[i][size - 1 - i] == null || cells[i][size - 1 - i].getSymbol() != symbol) {
                diagonal2Win = false;
            }
        }
        if (diagonal1Win) {
            return Optional.of(cells[0][0]);
        }
        if (diagonal2Win) {
            return Optional.of(cells[0][size - 1]);
        }
        return Optional.empty();
    }
}