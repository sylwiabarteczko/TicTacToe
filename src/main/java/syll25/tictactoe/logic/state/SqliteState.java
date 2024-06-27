package syll25.tictactoe.logic.state;

import syll25.tictactoe.logic.GameBoard;
import syll25.tictactoe.logic.Player;

import java.sql.*;
import java.util.Optional;

public class SqliteState implements State {

    private static final String DataBase_URL = "jdbc:sqlite:" + System.getProperty("user.home") + "/tictactoe/game_state.db";

    public SqliteState() {
        createTable();
    }

    @Override
    public void save(GameBoard board, Player player1, Player player2) {


        try (Connection connection = DriverManager.getConnection(DataBase_URL);
             PreparedStatement stmt = connection.prepareStatement(
                     "INSERT INTO game_state (player1_name, player1_symbol, player2_name, player2_symbol, board, size) VALUES ()")) {

            StringBuilder boardString = new StringBuilder();
            for (int i = 0; i < board.getSize(); i++) {
                for (int j = 0; j < board.getSize(); j++) {
                    Optional<Player> player = board.getFieldState(i, j);
                    boardString.append(player.map(p -> p.getSymbol()).orElse('-')).append(" ");
                }
            }

            stmt.setString(1, player1.getName());
            stmt.setString(2, String.valueOf(player1.getSymbol()));
            stmt.setString(3, player2.getName());
            stmt.setString(4, String.valueOf(player2.getSymbol()));
            stmt.setString(5, boardString.toString());
            stmt.setInt(6, board.getSize());

            stmt.executeUpdate();
            System.out.println("Game state saved to database.");

        } catch (SQLException e) {
            System.out.println("Failed to save game state to database.");
        }
    }

    @Override
    public StateDTO load() {
        StateDTO stateDTO = new StateDTO();
        try (Connection connection = DriverManager.getConnection(DataBase_URL);
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM game_state ORDER BY id DESC LIMIT 1")) {

            if (rs.next()) {
                stateDTO.player1 = new PlayerDTO(rs.getString("player1_name"), rs.getString("player1_symbol"));
                stateDTO.player2 = new PlayerDTO(rs.getString("player2_name"), rs.getString("player2_symbol"));

                String[] boardString = rs.getString("board").split(" ");
                int size = rs.getInt("size");

                String[][] board = new String[size][size];
                int index = 0;
                for (int i = 0; i < size; i++) {
                    for (int j = 0; j < size; j++) {
                        board[i][j] = boardString[index++];
                    }
                }
                stateDTO.board = board;
                stateDTO.size = size;
            }

        } catch (SQLException e) {
            System.out.println("Failed to load game state from database.");
        }
        return stateDTO;
    }

    private void createTable() {
        try (Connection connection = DriverManager.getConnection(DataBase_URL);
             Statement stmt = connection.createStatement()) {

            String createTableSQL = "CREATE TABLE IF NOT EXISTS game_state (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "player1_name TEXT NOT NULL, " +
                    "player1_symbol TEXT NOT NULL, " +
                    "player2_name TEXT NOT NULL, " +
                    "player2_symbol TEXT NOT NULL, " +
                    "board TEXT NOT NULL, " +
                    "size INTEGER NOT NULL);";

            stmt.executeUpdate(createTableSQL);
            System.out.println("Table created or already exists.");

        } catch (SQLException e) {
            System.out.println("Failed to create table in database.");
        }
    }
}
