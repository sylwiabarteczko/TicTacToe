package syll25.tictactoe.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import syll25.tictactoe.logic.Board;
import syll25.tictactoe.logic.Player;
import syll25.tictactoe.logic.exception.CellOccupiedException;
import syll25.tictactoe.web.model.Game;
import syll25.tictactoe.web.repository.GameRepository;

import java.util.List;

@Service
public class GameService {

    @Autowired
    private GameRepository gameRepository;
    private Board board;
    private Player player1;
    private Player player2;
    private Player currentPlayer;
    private boolean gameOver = false;

    public Game startNewGame() {
        this.board = new Board(3);
        this.player1 = new Player("Player 1", 'X');
        this.player2 = new Player("Player 2", 'O');
        this.currentPlayer = player1;
        this.gameOver = false;

        Game game = new Game(board.toString(), player1.getName(), player2.getName(), currentPlayer.getName(), gameOver);
        return gameRepository.save(game);
    }

    public Game makeMove(Long gameId, int row, int col) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new IllegalArgumentException("Game not found"));

        board = loadBoardFromString(game.getBoardState());
        currentPlayer = game.getCurrentPlayer().equals(player1.getName()) ? player1 : player2;

        if (!board.isCellEmpty(row, col)) {
            throw new IllegalArgumentException("Cell is already occupied");
        }

        try {
            board.placeSymbol(currentPlayer, row, col);

            if (board.isWinner(currentPlayer.getSymbol()).isPresent()) {
                game.setGameOver(true);
            } else if (board.isFull()) {
                game.setGameOver(true);
            }

            currentPlayer = (currentPlayer == player1) ? player2 : player1;

            game.setBoardState(board.toString());
            game.setCurrentPlayer(currentPlayer.getName());

            return gameRepository.save(game);

        } catch (CellOccupiedException ex) {
            throw new CellOccupiedException();
        }
    }
    public Game loadGame(Long gameId) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new IllegalArgumentException("Game not found"));

        board = loadBoardFromString(game.getBoardState());
        currentPlayer = game.getCurrentPlayer().equals(player1.getName()) ? player1 : player2;

        return game;
    }

    public List<Game> listGames() {
        return gameRepository.findAll();
    }

    private Board loadBoardFromString(String boardState) {
        Board board = new Board(3);
        String[] rows = boardState.split("\n");
        for (int row = 0; row < 3; row++) {
            String[] cells = rows[row].split(" ");
            for (int col = 0; col < 3; col++) {
                if (cells[col].equals("X")) {
                    board.placeSymbol(player1, row, col);
                } else if (cells[col].equals("O")) {
                    board.placeSymbol(player2, row, col);
                }
            }
        }
        return board;
    }

}
