package syll25.tictactoe.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import syll25.tictactoe.logic.Board;
import syll25.tictactoe.logic.CharacterPoolRandomizer;
import syll25.tictactoe.logic.Player;
import syll25.tictactoe.logic.exception.CellOccupiedException;
import syll25.tictactoe.logic.exception.InvalidMoveException;
import syll25.tictactoe.web.model.Game;
import syll25.tictactoe.web.repository.GameRepository;

import java.util.List;

@Service
public class GameService {

    @Autowired
    private GameRepository gameRepository;

    public Board startNewGame(String player1Name, String player2Name, int boardSize) {
        Board board = new Board(boardSize);

        CharacterPoolRandomizer symbolChoice = new CharacterPoolRandomizer('X', 'O');
        Player player1 = new Player(player1Name, symbolChoice.drawSymbol());
        Player player2 = new Player(player2Name, symbolChoice.drawSymbol());
        Player currentPlayer = player1;
        boolean gameOver = false;
//TODO nie chcemy toString
        Game game = new Game(board.toString(), player1.getName(), player1.getSymbol(), player2.getName(), player2.getSymbol(), currentPlayer.getName(), gameOver);
        gameRepository.save(game);
        return board;
    }

    public Board makeMove(Long gameId, int row, int col) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new InvalidMoveException("Game not found")); //todo obsluga bledow

        Board board = loadBoardFromString(game.getBoardState(), game.getPlayer1Symbol(), game.getPlayer2Symbol());
        Player player1 = new Player(game.getPlayer1Name(), game.getPlayer1Symbol());
        Player player2 = new Player(game.getPlayer2Name(), game.getPlayer2Symbol());
        Player currentPlayer = game.getCurrentPlayer().equals(player1.getName()) ? player1 : player2;

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
            gameRepository.save(game);

            return board;

        } catch (CellOccupiedException ex) {
            throw new CellOccupiedException();
        }
    }
    public Game loadGame(Long gameId) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new IllegalArgumentException("Game not found"));

        Board board = loadBoardFromString(game.getBoardState(), game.getPlayer1Symbol(), game.getPlayer2Symbol());
        Player player1 = new Player("Player 1", game.getPlayer1Symbol());
        Player player2 = new Player("Player 2", game.getPlayer2Symbol());
        Player currentPlayer = game.getCurrentPlayer().equals(player1.getName()) ? player1 : player2;

        return game;
    }

    public List<Game> listGames() {
        return gameRepository.findAll();
    }

    private Board loadBoardFromString(String boardState, char player1Symbol, char player2Symbol) {
        Board board = new Board(3);
        Player player1 = new Player("Player 1", player1Symbol);
        Player player2 = new Player("Player 2", player2Symbol);
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
