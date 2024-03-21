package syll25.tictactoe.logic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {
    @Test  //sprawdza czy jest pelna w polowie gry
    public void testIsFullPartiallyFilledBoard() {
        //given
        Board board = new Board(3);
        Player player1 = new Player('X');
        Player player2 = new Player('O');
        //when
        board.placeSymbol(player1, 0, 0);
        board.placeSymbol(player2, 1, 1);
        //then
        assertFalse(board.isFull());
    }

    @Test
    public void testIsFullEmptyBoard() { //sprawdza czy nie jest pelna na poczatku
        //given
        Board board = new Board(3);
        //then
        assertFalse(board.isFull());
    }

}
