package syll25.tictactoe.web.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import syll25.tictactoe.logic.Board;
import syll25.tictactoe.logic.CharacterPoolRandomizer;
import syll25.tictactoe.logic.Player;
import syll25.tictactoe.logic.exception.CellOccupiedException;
import syll25.tictactoe.logic.state.StateDTO;
import syll25.tictactoe.web.model.Game;
import syll25.tictactoe.web.model.GameStateDTO;
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
    private StateDTO jsonToState(String json) {
        try {
            return objectMapper.readValue(json, StateDTO.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert JSON to StateDTO", e);
        }
    }

    public Long startNewGame(String player1Name, String player2Name, int boardSize) {

        Board board = new Board(boardSize);

        CharacterPoolRandomizer symbolChoice = new CharacterPoolRandomizer('X', 'O');
        Player player1 = new Player(player1Name, symbolChoice.drawSymbol());
        Player player2 = new Player(player2Name, symbolChoice.drawSymbol());
        Player currentPlayer = player1;
        boolean gameOver = false;

        String[][] emptyBoard = new String[boardSize][boardSize];
        StateDTO stateDTO = new StateDTO(player1, player2, emptyBoard, boardSize, currentPlayer.getName(), false);

        String boardStateJson = stateToJson(stateDTO);

        Game game = new Game(boardStateJson, player1.getName(), player1.getSymbol(), player2.getName(),
                player2.getSymbol(), currentPlayer.getName(),false );

        gameRepository.save(game); //Id przydzielane

        return game.getId();
    }

    public StateDTO makeMove(Long gameId, int row, int col) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new IllegalArgumentException("Game not found"));

        StateDTO stateDTO = jsonToState(game.getBoardState("XOX,O,O"));
        Board board = new Board(stateDTO.getSize());

        Player player1 = new Player(stateDTO.getPlayer1().name(), stateDTO.getPlayer1().sign().charAt(0));
        Player player2 = new Player(stateDTO.getPlayer2().name(), stateDTO.getPlayer2().sign().charAt(0));
        Player currentPlayer = stateDTO.getCurrentPlayer().equals(player1.getName()) ? player1 : player2;

        for (int i = 0; i < stateDTO.getBoard().length; i++) {
            for (int j = 0; j < stateDTO.getBoard()[i].length; j++) {
                if (stateDTO.getBoard()[i][j] != null) {
                    char symbol = stateDTO.getBoard()[i][j].charAt(0);
                    Player player = (symbol == player1.getSymbol()) ? player1 : player2;
                    board.placeSymbol(player, i, j);
                }
            }
        }

        try {
            board.placeSymbol(currentPlayer, row, col);

            if (board.isWinner(currentPlayer.getSymbol()).isPresent()) {
                stateDTO.setGameOver(true);
            } else if (board.isFull()) {
                stateDTO.setGameOver(true);
                stateDTO.setCurrentPlayer(null);
            } else {
                stateDTO.setCurrentPlayer (currentPlayer.getName().equals(player1.getName()) ? player2.getName() : player1.getName());
            }

            String[][] updatedBoard = new String[board.getSize()][board.getSize()];
            Player[][] cells = board.getCells();
            for (int i = 0; i < cells.length; i++) {
                for (int j = 0; j < cells[i].length; j++) {
                    if (cells[i][j] != null) {
                        updatedBoard[i][j] = String.valueOf(cells[i][j].getSymbol());
                    }
                }
            }
            stateDTO.setBoard(updatedBoard);

            game.setBoardState(stateToJson(stateDTO));
            gameRepository.save(game);

            return stateDTO;

        } catch (CellOccupiedException e) {
            throw new CellOccupiedException();
        }
    }


    public GameStateDTO loadGame(Long gameId) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new IllegalArgumentException("Game not found"));

        StateDTO stateDTO = jsonToState(game.getBoardState("XOX,O,O"));

        return new GameStateDTO(stateDTO, gameId);
        }

    public List<Game> listGames() {
        return gameRepository.findAll();
    }

    private Board loadBoardFromString(Long gameId) {

        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new IllegalArgumentException("Game not found"));

        Character player1Symbol = game.getPlayer1Symbol();
        Character player2Symbol = game.getPlayer2Symbol();
        String boardState = game.getBoardState("XOX,O,O");

        Board board = new Board(game.getBoardState("XOX,O,O").split("\n").length);

        Player player1 = new Player(game.getPlayer1Name(), game.getPlayer1Symbol());
        Player player2 = new Player(game.getPlayer2Name(), game.getPlayer2Symbol());

        String[] rows = game.getBoardState("XOX,O,O").split("\n");
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
