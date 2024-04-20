package syll25.tictactoe.logic.state;

public class StateDTO {
    public String player1Name;
    public String player2Name;
    public String player1Sign;
    public String player2Sign;
    public String[][] board;
    public int size;

    public StateDTO(String player1Name, String player1Sign, String player2Name, String player2Sign, Object object, int size) {

    }

    public void StateDTD(String player1Name, String player2Name, String player1Sign, String player2Sign, String[][] board, int size) {
        this.player1Name = player1Name;
        this.player2Name = player2Name;
        this.player1Sign = player1Sign;
        this.player2Sign = player2Sign;
        this.board = board;
        this.size = size;
    }

}
