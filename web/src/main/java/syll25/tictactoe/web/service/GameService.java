package syll25.tictactoe.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import syll25.tictactoe.logic.Board;
import syll25.tictactoe.logic.Player;
import syll25.tictactoe.logic.exception.CellOccupiedException;
import syll25.tictactoe.logic.exception.InvalidMoveException;
import syll25.tictactoe.logic.state.State;
import syll25.tictactoe.logic.state.StateDTO;
import syll25.tictactoe.logic.state.StateFactory;
import syll25.tictactoe.web.model.Game;
import syll25.tictactoe.web.repository.GameRepository;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    private static final String saveDirectory = System.getProperty("user.home") + "/tictactoe/";

    public Board startNewGame() {
        this.board = new Board(3);
        this.player1 = new Player("Player 1", 'X');
        this.player2 = new Player("Player 2", 'O');
        this.currentPlayer = player1;
        this.gameOver = false;
        return board;
    }

    public Board makeMove(int row, int col) {
        if (!board.isCellEmpty(row, col)) {
            throw new IllegalArgumentException("Cell is already occupied");
        }
        try {
            board.placeSymbol(currentPlayer, row, col);

            if (board.isWinner(currentPlayer.getSymbol()).isPresent()) {
                System.out.println(currentPlayer.getName() + " wins!");
                gameOver = true;
            } else if (board.isFull()) {
                System.out.println("It's a draw!");
                gameOver = true;
            }
            currentPlayer = (currentPlayer == player1) ? player2 : player1;
        } catch (InvalidMoveException ex) {
            throw new CellOccupiedException();
        }
        return board;
    }
    public Board loadGame(String filename) {
        Path path = Paths.get(saveDirectory + filename);
        State state = StateFactory.getState(filename);

        if (!Files.exists(path)) {
            System.out.println("File not found. Starting a new game: " + filename);
            startNewGame();
        } else {
            System.out.println("Loaded existing game: " + filename);
            try {
                StateDTO stateDTO = state.load();
                loadSavedGames(stateDTO);
            } catch (RuntimeException e) {
                System.out.println("No saved game state found. Starting a new game.");
                startNewGame();
            }
        }
        return null;
    }

    public void loadSavedGames(StateDTO stateDTO) {
        this.player1 = new Player(stateDTO.player1.name(), stateDTO.player1.sign().charAt(0));
        this.player2 = new Player(stateDTO.player2.name(), stateDTO.player2.sign().charAt(0));
        this.board = new Board(stateDTO.size);

        for (int row = 0; row < stateDTO.board.length; row++) {
            for (int col = 0; col < stateDTO.board[row].length; col++) {
                if (stateDTO.board[row][col].equals(String.valueOf(player1.getSymbol()))) {
                    board.placeSymbol(player1, row, col);
                } else if (stateDTO.board[row][col].equals(String.valueOf(player2.getSymbol()))) {
                    board.placeSymbol(player2, row, col);
                }
            }
        }

        this.currentPlayer = stateDTO.currentPlayer.equals(player1.getName()) ? player1 : player2;
        System.out.println("Game state restored.");
    }
    public List<Game> listGames() {
        return gameRepository.findAll();
    }
}
