package syll25.tictactoe.logic;

import syll25.tictactoe.logic.exception.CellOccupiedException;
import syll25.tictactoe.logic.exception.OutOfRangeException;

import java.util.Arrays;
import java.util.Optional;

public class Board implements GameBoard {
    private final Player[] cells;
    private final int size;

    public Board(int size) {
        this.size = size;
        this.cells = new Player[size * size];
        initializeBoard();
    }

    public Player[][] getCells() {
        Player[][] cellMatrix = new Player[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                cellMatrix[i][j] = cells[i * size + j];
            }
        }
        return cellMatrix;
    }

    private void initializeBoard() {
        Arrays.fill(cells, null);
    }

    public int getSize() {
        return size;
    }

    @Override
    public Optional<Player> getFieldState(int row, int col) {
        int index = row * size + col;
        return Optional.ofNullable(cells[index]);
    }

    @Override
    public boolean isFull() {
        for (Player cell : cells) {
            if (cell == null) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void placeSymbol(Player player, int row, int col) {
        int index = row * size + col;
        checkValidMove(row, col);
        if (cells[index] != null) {
            throw new CellOccupiedException();
        }
        cells[index] = player;
    }

    private void checkValidMove(int row, int col) {
        if (row < 0 || row >= size || col < 0 || col >= size) {
            throw new OutOfRangeException();
        }
    }

    @Override
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
                int index = i * size + j;
                if (cells[index] == null || cells[index].getSymbol() != symbol) {
                    win = false;
                    break;
                }
            }
            if (win) {
                return Optional.of(cells[i * size]);
            }
        }
        return Optional.empty();
    }

    private Optional<Player> checkColumnWinner(char symbol) {
        for (int j = 0; j < size; j++) {
            boolean win = true;
            for (int i = 0; i < size; i++) {
                int index = i * size + j;
                if (cells[index] == null || cells[index].getSymbol() != symbol) {
                    win = false;
                    break;
                }
            }
            if (win) {
                return Optional.of(cells[j]);
            }
        }
        return Optional.empty();
    }

    private Optional<Player> checkDiagonalWinner(char symbol) {
        boolean diagonal1Win = true;
        boolean diagonal2Win = true;

        for (int i = 0; i < size; i++) {
            int index1 = i * size + i;
            if (cells[index1] == null || cells[index1].getSymbol() != symbol) {
                diagonal1Win = false;
            }
            int index2 = i * size + (size - 1 - i);
            if (cells[index2] == null || cells[index2].getSymbol() != symbol) {
                diagonal2Win = false;
            }
        }
        if (diagonal1Win) {
            return Optional.of(cells[0]);
        }
        if (diagonal2Win) {
            return Optional.of(cells[size - 1]);
        }
        return Optional.empty();
    }
}