package syll25.tictactoe.web.model;

import jakarta.persistence.*;

@Table(name = "game_states")
@Entity
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "board_state", columnDefinition = "TEXT")
    private String boardState;

    @Column(name = "player1_name")
    private String player1Name;

    @Column(name = "player2_name")
    private String player2Name;

    @Column(name = "current_player")
    private String currentPlayer;

    @Column(name = "game_over")
    private boolean gameOver;

    @Column
    private Character player1Symbol;

    @Column
    private Character player2Symbol;

    public Game() {

    }

    public Game(String boardState, String player1Name, Character player1Symbol, String player2Name, Character player2Symbol, String currentPlayer, boolean gameOver) {
        this.boardState = boardState;
        this.player1Name = player1Name;
        this.player2Name = player2Name;
        this.currentPlayer = currentPlayer;
        this.gameOver = gameOver;
        this.player1Symbol = player1Symbol;
        this.player2Symbol = player2Symbol;
    }
    public Long getId() {
        return id;
    }

    public String getBoardState() {
        return boardState;
    }

    public void setBoardState(String boardState) {
        this.boardState = boardState;
    }

    public String getPlayer1Name() {
        return player1Name;
    }

    public String getPlayer2Name() {
        return player2Name;
    }

    public String getCurrentPlayer() {
        return currentPlayer;
    }
    public void setCurrentPlayer(String currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", boardState='" + boardState + '\'' +
                ", player1Name='" + player1Name + '\'' +
                ", player2Name='" + player2Name + '\'' +
                ", currentPlayer='" + currentPlayer + '\'' +
                ", gameOver=" + gameOver +
                '}';

    }


    public Character getPlayer1Symbol() {
        return player1Symbol;
    }


    public Character getPlayer2Symbol() {
        return player2Symbol;
    }
}
