package syll25.tictactoe.logic.state;

import syll25.tictactoe.logic.GameBoard;
import syll25.tictactoe.logic.Player;

public interface State {
    void save(GameBoard board, Player player1, Player player2);

    StateDTO load();

    // TODO a co to tu się stało?
    StateDTO load(Long gameId, Player player1, Player player2, String[][] board, int size, String currentPlayer, boolean gameOver);
}
