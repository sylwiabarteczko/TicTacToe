package syll25.tictactoe.logic.state;

import syll25.tictactoe.logic.GameBoard;
import syll25.tictactoe.logic.Player;

import java.io.*;

public class TxtState implements State {
    @Override
    public void save(GameBoard board, Player player1, Player player2) {

        try {
            PrintStream consoleWriter = System.out;

            consoleWriter.println("Saving game state to the file");

            PrintWriter out = new PrintWriter(new FileWriter("gameState.txt"));
            out.write(player1.getName());
            out.write(":");
            out.write(player1.getSymbol());
            out.write(":");
            out.write(player2.getName());
            out.write(":");
            out.write(player2.getSymbol());
            out.println(board.getSize());
            out.close();

        } catch (IOException e) {
            System.out.println("Error occurred. ");
        }
    }

    @Override
    public StateDTO load(String filename) {  //DTO Data Transfer Object

        StateDTO stateDTO = new StateDTO("John","X","Adam", "O", null, 3 );

        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File(filename)));
            String line1 = reader.readLine();
            String[] players = line1.split(";");
            String[] player1Data = players[0].split(":");
            String[] player2Data = players[1].split(":");

            stateDTO.player1Name = player1Data[0];
            stateDTO.player1Sign = player1Data[1];
            stateDTO.player2Name = player2Data[0];
            stateDTO.player2Sign = player2Data[1];

            String line2 = reader.readLine();
            int size = (int) Math.sqrt(line2.length());
            stateDTO.size = Integer.parseInt(line2);

            String[][] board = new String[size][size];
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    char character = line2.charAt(i * size + j);
                    if (character != '-')
                        board[i][j] = String.valueOf(character);
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