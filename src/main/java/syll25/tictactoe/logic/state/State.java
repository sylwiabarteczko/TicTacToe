package syll25.tictactoe.logic.state;
import syll25.tictactoe.logic.GameBoard;
import syll25.tictactoe.logic.Player;

public interface State {
    void save(GameBoard board, Player player1, Player player2);

    StateDTO load(String filename);
}
