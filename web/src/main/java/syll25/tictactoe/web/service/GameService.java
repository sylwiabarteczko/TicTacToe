package syll25.tictactoe.web.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import syll25.tictactoe.logic.Board;
import syll25.tictactoe.logic.CharacterPoolRandomizer;
import syll25.tictactoe.logic.Player;
import syll25.tictactoe.logic.exception.CellOccupiedException;
import syll25.tictactoe.logic.exception.InvalidMoveException;
import syll25.tictactoe.logic.state.StateDTO;
import syll25.tictactoe.web.model.Game;
import syll25.tictactoe.web.repository.GameRepository;

import java.util.List;

@Service
public class GameService {

    @Autowired
    private GameRepository gameRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private String stateToJson(StateDTO stateDTO) {
        try {
            return objectMapper.writeValueAsString(stateDTO);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert StateDTO to JSON", e);
        }
    }

    public StateDTO startNewGame(String player1Name, String player2Name, int boardSize) {
        Board board = new Board(boardSize);

        CharacterPoolRandomizer symbolChoice = new CharacterPoolRandomizer('X', 'O');
        Player player1 = new Player(player1Name, symbolChoice.drawSymbol());
        Player player2 = new Player(player2Name, symbolChoice.drawSymbol());
        Player currentPlayer = player1;
        boolean gameOver = false;

        StateDTO stateDTO = new StateDTO(null, player1, player2, board.getCells(), board.getSize(), currentPlayer.getName(), gameOver);


        String boardStateJson = stateToJson(stateDTO);

        Game game = new Game(boardStateJson, player1.getName(), player1.getSymbol(), player2.getName(), player2.getSymbol(), currentPlayer.getName(), gameOver);
        gameRepository.save(game);
        return stateDTO;
    }

    public StateDTO makeMove(Long gameId, int row, int col) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new InvalidMoveException("Game not found")); //todo obsluga bledow

        Board board = loadBoardFromString(gameId);
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

            return new StateDTO(game.getId(), player1, player2, board.getCells(), board.getSize(), currentPlayer.getName(), game.isGameOver());

        } catch (CellOccupiedException ex) {
            throw new CellOccupiedException();
        }
    }

    public Board loadGame(Long gameId) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new IllegalArgumentException("Game not found"));

        Board board = loadBoardFromString(gameId);

        Player player1 = new Player(game.getPlayer1Name(), game.getPlayer1Symbol());
        Player player2 = new Player(game.getPlayer2Name(), game.getPlayer2Symbol());

        Player currentPlayer = game.getCurrentPlayer().equals(player1.getName()) ? player1 : player2;

        return board;
    }

    public List<Game> listGames() {
        return gameRepository.findAll();
    }

    private Board loadBoardFromString(Long gameId) {

        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new IllegalArgumentException("Game not found"));

        char player1Symbol = game.getPlayer1Symbol();
        char player2Symbol = game.getPlayer2Symbol();
        String boardState = game.getBoardState();

        Board board = new Board(boardState.split("\n").length);

        Player player1 = new Player(game.getPlayer1Name(), game.getPlayer1Symbol());
        Player player2 = new Player(game.getPlayer2Name(), game.getPlayer2Symbol());

        String[] rows = game.getBoardState().split("\n");
        for (int row = 0; row < rows.length; row++) {
            String[] cells = rows[row].split(" ");
            for (int col = 0; col < cells.length; col++) {
                if (cells[col].equals(String.valueOf(player1.getSymbol()))) {
                    board.placeSymbol(player1, row, col);
                } else if (cells[col].equals(String.valueOf(player2.getSymbol()))) {
                    board.placeSymbol(player2, row, col);
                }
            }
        }
        return board;
    }

}
