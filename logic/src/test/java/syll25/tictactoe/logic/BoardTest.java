package syll25.tictactoe.logic;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import syll25.tictactoe.logic.exception.CellOccupiedException;
import syll25.tictactoe.logic.exception.OutOfRangeException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

public class BoardTest {

    private Board board;

    @Test
    void testInitialBoardIsEmpty() {
        Board board = new Board(4);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                assertTrue(board.isCellEmpty(i, j));
            }
        }
    }

    @Test
    public void testGetCell() {
        Board board = new Board(3);
        Player player = new Player("Player1", 'X');
        board.placeSymbol(player, 1, 1);
        assertEquals("X", board.getCell(1, 1));
        assertEquals("-", board.getCell(0,0));
    }

    @ParameterizedTest
    @ValueSource(ints = {3, 4, 5, 10})
    public void testGetSize(int size) {
        Board board = new Board(size);
        assertEquals(size, board.getSize());
    }

    @Test
    public void testIsFullPartiallyFilledBoard() {

        GameBoard board = new Board(3);
        Player player1 = new Player("Player 1", 'X');
        Player player2 = new Player("Player 2", 'O');

        board.placeSymbol(player1, 0, 0);
        board.placeSymbol(player2, 1, 1);

        assertFalse(board.isFull());
    }

    @Test
    public void testIsFullEmptyBoard() {

        GameBoard board = new Board(3);
        Player player1 = new Player("Player1", 'X');
        Player player2 = new Player("Player2", 'Y');

        assertFalse(board.isFull());
    }

    @Test
    public void testRowWinner() {

        int size = 3;
        GameBoard board = new Board(size);
        Player player = new Player("Player", 'Z');

        for (int i = 0; i < size; i++) {
            board.placeSymbol(player, 0, i);
        }

        Optional<Player> winner = board.isWinner(player.getSymbol());
        assertTrue(winner.isPresent());
        assertEquals(player, winner.get());
    }

    @Test
    public void testColumnWinner() {

        int size = 3;
        GameBoard board = new Board(size);
        Player player = new Player("Player", 'X');

        for (int i = 0; i < size; i++) {
            board.placeSymbol(player, i, 0);
        }

        Optional<Player> winner = board.isWinner(player.getSymbol());
        assertEquals(player, winner.get());
    }

    @Test
    public void testDiagonal1Winner() {

        int size = 3;
        GameBoard board = new Board(size);
        Player player = new Player("Player", 'Y');

        for (int i = 0; i < size; i++) {
            board.placeSymbol(player, i, i);
        }

        Optional<Player> winner = board.isWinner(player.getSymbol());
        assertTrue(winner.isPresent());
    }

    @Test
    public void testDiagonal2Winner() {

        int size = 3;
        GameBoard board = new Board(size);
        Player player = new Player("Player", 'X');

        for (int i = 0; i < size; i++) {
            board.placeSymbol(player, i, size - 1 - i);
        }

        Optional<Player> winner = board.isWinner(player.getSymbol());
        assertTrue(winner.isPresent());
    }

    @Test
    public void testNoWinner() {

        int size = 3;
        GameBoard board = new Board(size);
        Player player = new Player("Player", 'X');

        Optional<Player> winner = board.isWinner(player.getSymbol());
        assertFalse(winner.isPresent());
    }

    @Test
    public void testOutOfRangeException() {

        int size = 3;
        GameBoard board = new Board(size);
        Player player = new Player("Player", 'Y');

        assertThrows(OutOfRangeException.class, () -> board.placeSymbol(player, -1, 0));
        assertThrows(OutOfRangeException.class, () -> board.placeSymbol(player, -1, -1));
        assertThrows(OutOfRangeException.class, () -> board.placeSymbol(player, 0, -1));
        assertThrows(OutOfRangeException.class, () -> board.placeSymbol(player, 1, 3));
        assertThrows(OutOfRangeException.class, () -> board.placeSymbol(player, 3, 0));
        assertThrows(OutOfRangeException.class, () -> board.placeSymbol(player, size, 0));
        assertThrows(OutOfRangeException.class, () -> board.placeSymbol(player, 0, size));
    }

    @Test
    public void testCellOccupiedException() {

        int size = 3;
        GameBoard board = new Board(size);
        Player player1 = new Player("Player1", 'Z');
        Player player2 = new Player("Player2", 'Y');

        board.placeSymbol(player1, 0, 0);

        assertThrows(CellOccupiedException.class, () -> board.placeSymbol(player2, 0, 0));
    }

    @Test
    public void testBoardIsFullReturnsTrue() {

        int size = 3;
        Board board = new Board(size);
        Player player1 = new Player("Player1", 'X');
        Player player2 = new Player("Player2", 'O');

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if ((i + j) % 2 == 0) {
                    board.placeSymbol(player1, i, j);
                } else {
                    board.placeSymbol(player2, i, j);
                }
            }
        }
        boolean isFull = board.isFull();
        assertTrue(isFull);
    }

    @Test
    public void testBoardIsFullReturnsFalse() {

        int size = 3;
        Board board = new Board(size);
        Player player1 = new Player("Player1", 'X');
        Player player2 = new Player("Player2", 'O');

        board.placeSymbol(player1, 0, 0);
        board.placeSymbol(player1, 1, 2);
        board.placeSymbol(player2, 2, 0);
        board.placeSymbol(player2, 0, 1);

        boolean isFull = board.isFull();
        assertFalse(isFull);
    }
}
