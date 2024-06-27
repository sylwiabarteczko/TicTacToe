package syll25.tictactoe.logic.state;

import syll25.tictactoe.logic.GameBoard;
import syll25.tictactoe.logic.Player;

import java.io.*;
import java.util.Optional;

public class TxtState implements State {

    private static final String PLAYER_DATA_SEPARATOR = ":";
    private static final String PLAYERS_SEPARATOR = ";";
    private final String filename;

    public TxtState(String filename) {
        this.filename = filename;
    }

    @Override
    public void save(GameBoard board, Player player1, Player player2) {

        try {

            PrintWriter out = new PrintWriter(new FileWriter(filename)); // to miejsce !!
            savePlayerData(player1, out);
            out.print(PLAYERS_SEPARATOR);
            savePlayerData(player2, out);
            out.println();

            StringBuilder boardState = new StringBuilder();
            for (int i = 0; i < board.getSize(); i++) {
                for (int j = 0; j < board.getSize(); j++) {
                    Optional<Player> player = board.getFieldState(i, j);
                    if (player.isPresent()) {
                        boardState.append(player.get().getSymbol());
                    } else {
                        boardState.append("-");
                    }
                }
            }

            out.print(boardState.toString());
            out.close();

        } catch (IOException e) {
            System.out.println("Error occurred. ");
        }
    }

    private static void savePlayerData(Player player, PrintWriter out) {
        out.print(player.getName());
        out.print(PLAYER_DATA_SEPARATOR);
        out.print(player.getSymbol());
    }

    @Override
    public StateDTO load() {  //DTO Data Transfer Object

        StateDTO stateDTO = new StateDTO();


        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File(filename)));
            String line1 = reader.readLine();
            String[] players = line1.split(PLAYERS_SEPARATOR);
            String[] player1Data = players[0].split(PLAYER_DATA_SEPARATOR);
            String[] player2Data = players[1].split(PLAYER_DATA_SEPARATOR);

            PlayerDTO player1DTO = new PlayerDTO(player1Data[0], player1Data[1]);
            PlayerDTO player2DTO = new PlayerDTO(player2Data[0], player2Data[1]);

            stateDTO.player1 = player1DTO;
            stateDTO.player2 = player2DTO;

            String line = reader.readLine();
            int size = (int) Math.sqrt(line.length());
            stateDTO.size = size;

            String[][] board = new String[size][size];
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    board[i][j] = String.valueOf(line.charAt(i * size + j));
                }
            }
            stateDTO.board = board;
            reader.close();

        } catch (IOException e) {
            throw new RuntimeException("Failed to load game state", e);
        }
        return stateDTO;
    }

}