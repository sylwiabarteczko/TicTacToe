package syll25.tictactoe;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class BoardTest {
    @Test  //sprawdza czy jest pelna w polowie gry
    public void testIsFullPartiallyFilledBoard() {
        //given
        Board board = new Board(new CharacterPoolRandomizer('X', 'Y', 'Z', 'O', 'S'));
        //when
        board.placeSymbol('X', 0, 0);
        board.placeSymbol('O', 1, 1);
        //then
        assertFalse(board.isFull());
    }

    @Test
    public void testIsFullEmptyBoard() { //sprawdza czy nie jest pelna na poczatku
        //given
        Board board = new Board(new CharacterPoolRandomizer('X', 'Y', 'Z', 'O', 'S'));
        //then
        assertFalse(board.isFull());
    }

    @Test
    public void testIsFullFullBoard() { //sprawdza czy jest pelna gdy komorki zostana wypelnione
        //given
        Board board = new Board(new CharacterPoolRandomizer('X', 'Y', 'Z', 'O', 'S'));

        //when
        board.placeSymbol('X', 0, 0);
        board.placeSymbol('O', 0, 1);
        board.placeSymbol('X', 0, 2);
        board.placeSymbol('O', 1, 0);
        board.placeSymbol('X', 1, 1);
        board.placeSymbol('O', 1, 2);
        board.placeSymbol('X', 2, 0);
        board.placeSymbol('O', 2, 1);
        board.placeSymbol('X', 2, 2);
        //then
        Assertions.assertTrue(board.isFull());
    }
}
