package syll25.tictactoe.ui;

import org.junit.jupiter.api.Test;
import syll25.tictactoe.logic.Board;
import syll25.tictactoe.logic.Player;


public class BoardRendererTest {

    @Test
    public void renderEmptyBoard() {

        Board board = new Board(3);

        BoardRenderer.renderBoard(board);

    }

    @Test
    public void renderBoardWithSymbols() {

        Board board = new Board(3);
        Player player1 = new Player("player1", 'X');
        Player player2 = new Player("player2", 'S');

        board.placeSymbol(player1, 1, 1);
        board.placeSymbol(player2, 0, 1);

        BoardRenderer.renderBoard(board);
    }

}

