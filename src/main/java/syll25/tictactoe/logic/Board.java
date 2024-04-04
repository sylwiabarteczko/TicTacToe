package syll25.tictactoe.logic;

import syll25.tictactoe.logic.exception.CellOccupiedException;
import syll25.tictactoe.logic.exception.OutOfRangeException;

import java.util.Arrays;
import java.util.Optional;

public class Board implements GameBoard{

    private final Player[][] cells;
    private final int size;

    public Board(int size) {
        this.size = size;
        this.cells = new Player[size][size];
        initializeBoard(cells);
    }

    public void initializeBoard(Player[][] cells) {
        for (Player[] row : cells) {
            Arrays.fill(row, null);
        }
    }

    public Player[][] getCells() {
        return cells;
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
        checkValidMove(row, col);
        checkCellOccupied(row, col);
        cells[row][col] = player;
    }

    private void checkValidMove(int row, int col) throws OutOfRangeException {
        if (row < 0 || row >= cells.length || col < 0 || col >= cells[row].length) {
            throw new OutOfRangeException();
        }
    }

    private void checkCellOccupied(int row, int col) throws CellOccupiedException {
        if (cells[row][col] != null) {
            throw new CellOccupiedException();
        }
    }

    public Optional<Player> isWinner(char symbol) {

        Optional<Player> rowWinner = checkRowWinner(symbol);
        if (rowWinner.isPresent()) {
            return rowWinner;
        }

        Optional<Player> columnWinner = checkColumnWinner(symbol);
        if (columnWinner.isPresent()) {
            return columnWinner;
        }
        Optional<Player> diagonalWinner = checkDiagonalWinner(symbol);
        if (diagonalWinner.isPresent()) {
            return diagonalWinner;
        }

        return Optional.empty();

    }

    private Optional<Player> checkRowWinner(char symbol) {
        for (int i = 0; i < size; i++) {
            boolean win = true;
            for (int j = 0; j < size; j++) {
                if (cells[i][j] == null || cells[i][j].getSymbol() != symbol) {
                    win = false;
                    break;
                }
            }
            if (win) {
                return Optional.of(cells[i][0]);
            }
        }
        return Optional.empty();
    }

    private Optional<Player> checkColumnWinner(char symbol) {
        for (int j = 0; j < size; j++) {
            boolean win = true;
            for (int i = 0; i < size; i++) {
                if (cells[i][j] == null || cells[i][j].getSymbol() != symbol) {
                    win = false;
                    break;
                }
            }
            if (win) {
                return Optional.of(cells[0][j]);
            }
        }
        return Optional.empty();
    }

    private Optional<Player> checkDiagonalWinner(char symbol) {
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