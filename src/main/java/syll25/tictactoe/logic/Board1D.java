package syll25.tictactoe.logic;

import syll25.tictactoe.logic.exception.CellOccupiedException;
import syll25.tictactoe.logic.exception.InvalidMoveException;
import syll25.tictactoe.logic.exception.OutOfRangeException;

import java.util.Arrays;
import java.util.Optional;

public class Board1D implements GameBoard {
    private final Player[] cells;
    private final int size;

    public Board1D(int size) {
        this.size = size;
        this.cells = new Player[size * size];
        initializeBoard();
    }

    public void initializeBoard() {
        Arrays.fill(cells, null);
    }

    @Override
    public String getCell(int row, int col) {
        return null;
    }

    @Override
    public void initializeFromState(String[][] boardState, Player player1, Player player2) {
        
    }

    @Override
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
    public void placeSymbol(Player player, int row, int col) throws InvalidMoveException {
        int index = row * size + col;
        checkValidMove(row, col);
        if (cells[index] != null) {
            throw new CellOccupiedException();
        }
        cells[index] = player;
    }

    public void checkValidMove(int row, int col) throws OutOfRangeException {
        if (row < 0 || row >= size || col < 0 || col >= size) {
            throw new OutOfRangeException();
        }
    }

    @Override
    public Optional<Player> isWinner(char symbol) {
        return Optional.empty();
    }

    @Override
    public Player[][] getCells() {
        return new Player[0][];
    }
}
