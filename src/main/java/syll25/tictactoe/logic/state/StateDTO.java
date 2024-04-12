package syll25.tictactoe.logic.state;

public class StateDTO {
    public String player1Name;
    public String player2Name;
    public String player1Sign;
    public String player2Sign;
    public String[][] board;
    public int size;
    public void StateDTD(String player1Name, String player2Name, String player1Sign, String player2Sign, String[][] board, int size) {
        this.player1Name = player1Name;
        this.player2Name = player2Name;
        this.player1Sign = player1Sign;
        this.player2Sign = player2Sign;
        this.board = board;
        this.size = size;
    }

    public String getPlayer1Name() {
        return player1Name;
    }

    public void setPlayer1Name(String player1Name) {
        this.player1Name = player1Name;
    }

    public String getPlayer2Name() {
        return player2Name;
    }

    public void setPlayer2Name(String player2Name) {
        this.player2Name = player2Name;
    }

    public String getPlayer1Sign() {
        return player1Sign;
    }

    public void setPlayer1Sign(String player1Sign) {
        this.player1Sign = player1Sign;
    }

    public String getPlayer2Sign() {
        return player2Sign;
    }

    public void setPlayer2Sign(String player2Sign) {
        this.player2Sign = player2Sign;
    }

    public String[][] getBoard() {
        return board;
    }

    public void setBoard(String[][] board) {
        this.board = board;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }


}
