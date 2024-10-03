package syll25.tictactoe.logic;

import syll25.tictactoe.logic.exception.CellOccupiedException;
import syll25.tictactoe.logic.exception.OutOfRangeException;

import java.util.Optional;

public class Board implements GameBoard {
    private final Player[][] cells;
    private final int size;

    public Board(int size) {
        this.size = size;
        this.cells = new Player[size][size];
        initializeBoard();
    }

    public Player[][] getCells() {
        Player[][] cellMatrix = new Player[size][size];
        for (int i = 0; i < size; i++) {
            System.arraycopy(cells[i], 0, cellMatrix[i], 0, size);
        }
        return cellMatrix;
    }

    @Override
    public String getCell(int row, int col) {
        checkValidMove(row,col);
        Player player = cells[row][col];
        if (player == null) {
            return "-";
        } else {
            return String.valueOf(player.getSymbol());
        }
    }
    public boolean isCellEmpty(int row, int col) {
        return cells[row][col] == null;
    }
    private void initializeBoard() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                cells[i][j] = null;
            }
        }
    }

    public int getSize() {
        return size;
    }

    @Override
    public Optional<Player> getFieldState(int row, int col) {
        return Optional.ofNullable(cells[row][col]);
    }

    @Override
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

    @Override
    public void placeSymbol(Player player, int row, int col) {
        checkValidMove(row, col);
        if (cells[row][col] != null) {
            throw new CellOccupiedException();
        }
        cells[row][col] = player;
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