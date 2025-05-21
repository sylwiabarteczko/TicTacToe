package syll25.tictactoe.ui;

import org.junit.jupiter.api.Test;
import syll25.tictactoe.logic.Board;
import syll25.tictactoe.logic.Player;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class BoardRendererTest {

    @Test
    public void renderEmptyBoard() {

        Board board = new Board(3);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        BoardRenderer.renderBoard(board);

        String expectedOutput = """
                A B C\s
                1 - - -\s
                2 - - -\s
                3 - - -\s
                """;

        assertEquals(expectedOutput.trim(), outputStream.toString().trim());
        System.setOut(System.out);

    }

    @Test
    public void renderBoardWithSymbols() {

        Board board = new Board(3);
        Player player1 = new Player("player1", 'X');
        Player player2 = new Player("player2", 'S');

        board.placeSymbol(player1, 1, 1);
        board.placeSymbol(player2, 0, 1);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        BoardRenderer.renderBoard(board);

        String expectedOutput = """
                A B C\s
                1 - S -\s
                2 - X -\s
                3 - - -\s
                """;

        assertEquals(expectedOutput.trim(), outputStream.toString().trim());
        System.setOut(System.out);

    }

    @Test
    public void render1x1Board() {

        Board board = new Board(1);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        BoardRenderer.renderBoard(board);

        String exceptedOutput = """
                A\s
                1 -\s
                """;
        assertEquals(exceptedOutput.trim(), outputStream.toString().trim());
        System.setOut(System.out);
    }

    @Test
    public void render4x4Board() {

        Board board = new Board(4);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        BoardRenderer.renderBoard(board);

        String exceptedOutput = """
                A B C D\s
                1 - - - -\s
                2 - - - -\s
                3 - - - -\s
                4 - - - -\s
                """;

        assertEquals(exceptedOutput.trim(), outputStream.toString().trim());
        System.setOut(System.out);
    }

    @Test
    public void renderBoardWithNull() {
        try {
            BoardRenderer.renderBoard(null);
        } catch (NullPointerException e) {
            return;
        }
    }
}
