package syll25.tictactoe.logic;

import syll25.tictactoe.logic.exception.InvalidMoveException;

import java.util.Optional;

public interface GameBoard {
    void initializeBoard(Player[][] cells);
    Player[][] getCells();
    boolean isFull();
    void placeSymbol(Player player,int row, int col) throws InvalidMoveException;
    Optional<Player>isWinner(char symbol);
}
