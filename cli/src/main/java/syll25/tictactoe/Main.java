package syll25.tictactoe;

import syll25.tictactoe.logic.*;
import syll25.tictactoe.logic.exception.*;
import syll25.tictactoe.logic.state.State;
import syll25.tictactoe.logic.state.StateDTO;
import syll25.tictactoe.logic.state.StateFactory;
import syll25.tictactoe.ui.BoardRenderer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.Scanner;


public class Main {

    private static final String saveDirectory = System.getProperty("user.home") + "/tictactoe/";
    private static int boardSize = 3;
    private static Player player1;
    private static Player player2;


    public static void main(String[] args) {
        ensureSaveDirectory();

        State state;

        if (args.length == 0) {
            System.out.println("Using default file: New game begins. ");
            state = StateFactory.getState("sqlite");
            startNewGame(state.toString());
            return;
        }
        switch (args[0]) {
            case "--list-games":
                listGames();
                return;
            case "--load-game":
                if (args.length < 2) {
                    System.out.println("Provide filename. ");
                    return;
                }
                loadGame(args[1]);
                break;
            default:
                System.out.println("Unknown command. ");
                return;
        }
    }

    private static void ensureSaveDirectory() {
        try {
            Path path = Paths.get(saveDirectory);
            if (!Files.exists(path)) {
                Files.createDirectories(path);
                System.out.println("Created directory: " + path.toString());
            }
        } catch (IOException e) {
            System.out.println("Can not create save directory. ");
            System.exit(1);
        }
    }

    private static void listGames() {
        try {
            Files.list(Paths.get(saveDirectory))
                    .filter(Files::isRegularFile)
                    .map(Path::getFileName)
                    .map(Path::toString)
                    .forEach(System.out::println);
        } catch (IOException e) {
            System.out.println("Failed to list games. ");
        }
    }

    public static void loadGame(String filename) {
        Path path = Paths.get(saveDirectory + filename);
        State state = StateFactory.getState(filename);

        if (!Files.exists(path)) {
            System.out.println("File not found. Starting a new game: " + filename);
            startNewGame(filename);
        } else {
            System.out.println("Loaded existing game: " + filename);
            try {
                StateDTO stateDTO = state.load();
                loadSavedGames(state, stateDTO);
            } catch (RuntimeException e) {
                System.out.println("No saved game state found. Starting a new game.");
                startNewGame(filename);
            }
        }
    }

    public static void loadSavedGames(State state, StateDTO stateDTO) {

        Player player1 = new Player(stateDTO.player1.name(), stateDTO.player1.sign().charAt(0));
        Player player2 = new Player(stateDTO.player2.name(), stateDTO.player2.sign().charAt(0));

        GameBoard board = new Board(stateDTO.size);
        for (int row = 0; row < stateDTO.board.length; row++) {
            for (int col = 0; col < stateDTO.board[row].length; col++) {
                if (player1.getSymbol() == stateDTO.board[row][col].charAt(0)) {
                    board.placeSymbol(player1, row, col);
                }
                if (player2.getSymbol() == stateDTO.board[row][col].charAt(0)) {
                    board.placeSymbol(player2, row, col);
                }
            }
        }

        System.out.println("Loaded game board: ");
        BoardRenderer.renderBoard(board);

        playGame(state, board, player1, player2);
    }

    public static void startNewGame(String filename) {

        CharacterPoolRandomizer symbolChoice = new CharacterPoolRandomizer('X', 'Y', 'Z', 'O', 'S');

        Scanner scanner = new Scanner(System.in);

        System.out.println("Player 1, enter your name: ");
        String player1Name = scanner.nextLine();
        System.out.println("Player 2, enter your name");
        String player2Name = scanner.nextLine();

        GameBoard board = new Board(boardSize);
        System.out.println("That is your game board: ");

        BoardRenderer.renderBoard(board);

        try {
            player1 = new Player(player1Name, symbolChoice.drawSymbol());
            player2 = new Player(player2Name, symbolChoice.drawSymbol());
            System.out.println("Player " + player1.getName() + " that is your symbol: " + player1.getSymbol());
            System.out.println("Player " + player2.getName() + " that is your symbol: " + player2.getSymbol());

            playGame(StateFactory.getState(filename), board, player1, player2);

        } catch (NoMoreSymbolsException ex) {
            System.out.println("No more symbols available. ");
        }
    }

    private static void playGame(State state, GameBoard board, Player player1, Player player2) {
        boolean gameOver = false;

        while (!gameOver) {
            System.out.println(player1.getName() + " , enter row and column (e.g. A1, B2): ");
            gameOver = playerMove(state, board, new Scanner(System.in), player1);
            if (gameOver) break;
            System.out.println(player2.getName() + " , enter row and column (e.g. A1, B2): ");
            gameOver = playerMove(state, board, new Scanner(System.in), player2);
        }

    }

    public static boolean playerMove(State state, GameBoard board, Scanner scanner, Player player) {
        int row, col;
        String input;

        do {
            input = scanner.nextLine().toUpperCase();
            Coordinates coordinates = new Coordinates(input);

            row = coordinates.getRow();
            col = coordinates.getCol();

            if (row == -1 || col == -1) {
                System.out.println("Invalid input. Please enter row and column in the format A1, B2, etc.");
                continue;
            }

            try {
                board.placeSymbol(player, row, col);
            } catch (InvalidMoveException ex) {
                System.out.println(ex.getMessage());
                continue;
            } catch (OutOfRangeException ex) {
                System.out.println("Invalid move: Out of range. ");
                continue;
            } catch (CellOccupiedException ex) {
                System.out.println("Invalid move: Cell already occupied. ");
                continue;
            } catch (InvalidCoordinatesException ex) {
                System.out.println("Invalid input. Please enter row and column in the format A1, B2 etc. ");
                continue;
            }
            break;
        } while (true);

        BoardRenderer.renderBoard(board);

        state.save(board, player1, player2);

        Optional<Player> winner = board.isWinner(player.getSymbol());
        if (winner.isPresent()) {
            System.out.println(player.getName() + " wins!");
            return true;
        } else if (board.isFull()) {
            System.out.println("We have a draw!");
            return true;
        }
        return false;
    }
}


