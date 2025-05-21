package syll25.tictactoe.web.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
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

    @Transactional
    public Long startNewGame(String player1Name, String player2Name, int boardSize, String player1Login) {

        Board board = new Board(boardSize);

        CharacterPoolRandomizer symbolChoice = new CharacterPoolRandomizer('X', 'O');
        Player player1 = new Player(player1Name, symbolChoice.drawSymbol());
        Player player2 = new Player(player2Name, symbolChoice.drawSymbol());
        Player currentPlayer = player1;

        String[][] emptyBoard = new String[boardSize][boardSize];
        StateDTO stateDTO = new StateDTO(player1, player2, emptyBoard, boardSize, currentPlayer.getName(), false);

        String boardStateJson = stateToJson(stateDTO);

        Game game = new Game(boardStateJson, player1.getName(), player1.getSymbol(), player2.getName(),
                player2.getSymbol(), player1Login, null, currentPlayer.getName(), false);

        game.setCurrentPlayer(currentPlayer.getName());
        gameRepository.save(game);

        return game.getId();
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public StateDTO makeMove(Long gameId, int row, int col, String currentUsername) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new IllegalArgumentException("Game not found"));

        if (!currentUsername.equals(game.getPlayer1Name()) &&
                !currentUsername.equals(game.getPlayer2Name())) {
            throw new IllegalStateException("Not a game participant");
        }

        if (!currentUsername.equals(game.getCurrentPlayer())) {
            throw new IllegalStateException("Not your turn");
        }

        if (game.getPlayer2Name().equals("Player") && !currentUsername.equals(game.getPlayer1Name())) {
            game.setPlayer2Name(currentUsername);
        }

        StateDTO stateDTO = jsonToState(game.getBoardState());
        Board board = new Board(stateDTO.getSize());

        Player player1 = new Player(stateDTO.getPlayer1().name(), stateDTO.getPlayer1().sign().charAt(0));
        Player player2 = new Player(stateDTO.getPlayer2().name(), stateDTO.getPlayer2().sign().charAt(0));
        Player currentPlayer = currentUsername.equals(player1.getName()) ? player1 : player2;

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
                stateDTO.setCurrentPlayer("Game Over");
            } else if (board.isFull()) {
                stateDTO.setGameOver(true);
                stateDTO.setCurrentPlayer("Game Over");
            } else {
                stateDTO.setCurrentPlayer(currentPlayer.getName().equals(player1.getName()) ? player2.getName() : player1.getName());
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
            game.setGameOver(stateDTO.isGameOver());
            game.setCurrentPlayer(stateDTO.getCurrentPlayer());

            gameRepository.save(game);
            return stateDTO;

        } catch (CellOccupiedException e) {
            throw new CellOccupiedException();
        }
    }


    public GameStateDTO loadGame(Long gameId) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new IllegalArgumentException("Game not found"));

        return new GameStateDTO(jsonToState(game.getBoardState()), gameId);
    }

    public List<Game> listActiveGames() {
        return gameRepository.findByActiveGame();
    }

    private Board loadBoardFromString(Long gameId) {

        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new IllegalArgumentException("Game not found"));

        Character player1Symbol = game.getPlayer1Symbol();
        Character player2Symbol = game.getPlayer2Symbol();
        String boardState = game.getBoardState();

        Board board = new Board(game.getBoardState().split("\n").length);

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

    public Game getGameById(Long gameId) {
        return gameRepository.findById(gameId)
                .orElseThrow(() -> new IllegalArgumentException("Game not found"));
    }

    public void save(Game game) {
        gameRepository.save(game);
    }

    public boolean checkWinner(StateDTO stateDTO) {
        Board board = new Board(stateDTO.getSize());

        Player player1 = new Player(
                stateDTO.getPlayer1().name(),
                stateDTO.getPlayer1().sign().charAt(0)
        );
        Player player2 = new Player(
                stateDTO.getPlayer2().name(),
                stateDTO.getPlayer2().sign().charAt(0)
        );
        String[][] currentBoard = stateDTO.getBoard();
        for (int row = 0; row < currentBoard.length; row++) {
            for (int col = 0; col < currentBoard[row].length; col++) {
                if (currentBoard[row][col] != null) {
                    char symbol = currentBoard[row][col].charAt(0);
                    Player currentPlayer = symbol == player1.getSymbol() ? player1 : player2;
                    board.placeSymbol(currentPlayer, row, col);
                }
            }

        }
        char lastPlayerSymbol = stateDTO.getCurrentPlayer().equals(player1.getName()) ? player2.getSymbol() : player1.getSymbol();
        return board.isWinner(lastPlayerSymbol).isPresent();
    }

    public Game loadGameEntity(Long gameId) {
        return gameRepository.findById(gameId)
                .orElseThrow(() -> new IllegalArgumentException("Game not found"));
    }

    public StateDTO convertToStateDTO(Game game) {
        try {
            return objectMapper.readValue(game.getBoardState(), StateDTO.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to deserialize board state", e);
        }
    }

    public GameStateDTO convertToGameStateDTO(Game game) {
        StateDTO stateDTO = convertToStateDTO(game);
        return new GameStateDTO(
                stateDTO,
                game.getCurrentPlayer(),
                game.isGameOver(),
                checkWinner(stateDTO)
        );
    }

    public boolean assignPlayer(Game game, String login) {

        boolean modified = false;

        if (game.getPlayer1Login() == null && login != null) {
            game.setPlayer1Login(login);
            modified = true;
        } else if (game.getPlayer2Login() == null && !login.equals(game.getPlayer1Login())) {
            game.setPlayer2Login(login);
            modified = true;
        } else if (!login.equals(game.getPlayer1Login()) && !login.equals(game.getPlayer2Login())) {
            return false;
        }
        if (modified) {
            gameRepository.save(game);
        }
        return true;
    }

}



